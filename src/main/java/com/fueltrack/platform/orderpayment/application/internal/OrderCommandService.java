package com.fueltrack.platform.orderpayment.application.internal;

import com.fueltrack.platform.orderpayment.domain.model.Order;
import com.fueltrack.platform.orderpayment.domain.model.OrderStatus;
import com.fueltrack.platform.orderpayment.domain.model.Payment;
import com.fueltrack.platform.orderpayment.domain.services.InventoryDischargePort;
import com.fueltrack.platform.orderpayment.domain.services.OrderRepository;
import com.fueltrack.platform.orderpayment.domain.services.PaymentGatewayClient;
import com.fueltrack.platform.orderpayment.domain.services.PaymentRepository;
import com.fueltrack.platform.orderpayment.interfaces.rest.requests.CreateOrderRequest;
import com.fueltrack.platform.orderpayment.interfaces.rest.responses.OrderResponse;
import com.fueltrack.platform.orderpayment.interfaces.rest.responses.PaymentValidationResponse;
import com.fueltrack.platform.fleet.domain.model.aggregates.Driver;
import com.fueltrack.platform.fleet.domain.model.aggregates.Tank;
import com.fueltrack.platform.fleet.infrastructure.persistence.jpa.repositories.DriverRepository;
import com.fueltrack.platform.fleet.infrastructure.persistence.jpa.repositories.TankRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Application service for order and payment commands.
 */
@Service
public class OrderCommandService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayClient paymentGatewayClient;
    private final InventoryDischargePort inventoryDischargePort;
    private final DriverRepository driverRepository;
    private final TankRepository tankRepository;

    public OrderCommandService(OrderRepository orderRepository,
                               PaymentRepository paymentRepository,
                               PaymentGatewayClient paymentGatewayClient,
                               InventoryDischargePort inventoryDischargePort,
                               DriverRepository driverRepository,
                               TankRepository tankRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.paymentGatewayClient = paymentGatewayClient;
        this.inventoryDischargePort = inventoryDischargePort;
        this.driverRepository = driverRepository;
        this.tankRepository = tankRepository;
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request, Long requesterId) {
        Order order = Order.builder()
                .fuelType(request.fuelType())
                .name(request.name())
                .gallons(request.gallons())
                .documentRef(request.documentRef())
                .status(OrderStatus.PENDING_APPROVAL)
                .createdAt(OffsetDateTime.now())
                .requesterId(requesterId)
                .etaMinutes(request.etaMinutes())
                .build();

        return toResponse(orderRepository.save(order));
    }

    public List<OrderResponse> listOrders(Long requesterId, boolean isProvider) {
        List<Order> orders = isProvider
                ? orderRepository.findByProviderIdIsNullOrProviderId(requesterId)
                : orderRepository.findByRequesterId(requesterId);
        return orders.stream().map(this::toResponse).toList();
    }

    public OrderResponse getOrder(Long id) {
        return toResponse(orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found")));
    }

    @Transactional
    public OrderResponse approveOrder(Long id, Long providerId) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (order.getStatus() != OrderStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Only orders in PENDING_APPROVAL can be approved");
        }
        if (order.getProviderId() != null && !order.getProviderId().equals(providerId)) {
            throw new IllegalStateException("This order is already assigned to another provider");
        }
        order.setStatus(OrderStatus.APPROVED);
        order.setProviderId(providerId);
        return toResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse updateGallons(Long id, Double newGallons) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setGallons(newGallons);
        return toResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse dispatchOrder(Long id, String truckId, Long driverId, Long tankId) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (order.getStatus() != OrderStatus.APPROVED) {
            throw new IllegalStateException("Only approved orders can be dispatched");
        }
        order.setTruckId(truckId);
        order.setDriverId(driverId);
        order.setTankId(tankId);
        order.setStatus(OrderStatus.IN_TRANSIT);
        order.setDispatchedAt(OffsetDateTime.now());
        
        // Update Driver status
        if (driverId != null) {
            driverRepository.findById(driverId).ifPresent(driver -> {
                driver.setStatus("ON_ROUTE");
                driverRepository.save(driver);
            });
        }
        
        // Update Tank status and fuel
        if (tankId != null) {
            tankRepository.findById(tankId).ifPresent(tank -> {
                tank.setStatus("ON_ROUTE");
                tank.setCurrentFuelGallons(order.getGallons());
                tankRepository.save(tank);
            });
        }

        orderRepository.save(order);
        inventoryDischargePort.discharge(order.getFuelType(), order.getGallons());
        return toResponse(order);
    }

    @Transactional
    public PaymentValidationResponse validatePayment(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        BigDecimal amount = BigDecimal.valueOf(order.getGallons()).multiply(BigDecimal.valueOf(1.00d));
        boolean validated = paymentGatewayClient.validatePayment(order.getId(), amount);
        if (!validated) {
            throw new IllegalArgumentException("Payment validation failed");
        }

        order.setStatus(OrderStatus.APPROVED);
        orderRepository.save(order);

        Payment payment = Payment.builder()
                .order(order)
                .amount(amount)
                .paymentStatus("APPROVED")
                .createdAt(OffsetDateTime.now())
                .build();
        paymentRepository.save(payment);

        return new PaymentValidationResponse(order.getId(), true, order.getStatus());
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getFuelType(),
                order.getName(),
                order.getGallons(),
                order.getDocumentRef(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getRequesterId(),
                order.getTruckId(),
                order.getDriverId(),
                order.getTankId(),
                order.getProviderId(),
                order.getEtaMinutes(),
                order.getDispatchedAt(),
                order.getCompletedAt(),
                order.getSecurityHash());
    }

    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found");
        }
        orderRepository.deleteById(id);
    }

    @Transactional
    public OrderResponse markAsDelivered(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(OrderStatus.DELIVERED);
        return toResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse markAsCompleted(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(OrderStatus.COMPLETED);
        order.setCompletedAt(OffsetDateTime.now());
        // Generate a simple security hash
        String hashStr = order.getId() + "-" + order.getCompletedAt().toEpochSecond();
        order.setSecurityHash("#FT-HASH-" + java.util.UUID.nameUUIDFromBytes(hashStr.getBytes()).toString().substring(0, 13).toUpperCase());
        
        // Update Driver trips and status
        if (order.getDriverId() != null) {
            driverRepository.findById(order.getDriverId()).ifPresent(driver -> {
                driver.setCompletedTripsSinceRest(driver.getCompletedTripsSinceRest() + 1);
                if (driver.getCompletedTripsSinceRest() >= 2) {
                    driver.setStatus("FATIGUE");
                } else {
                    driver.setStatus("AVAILABLE");
                }
                driverRepository.save(driver);
            });
        }
        
        // Update Tank trips and status
        if (order.getTankId() != null) {
            tankRepository.findById(order.getTankId()).ifPresent(tank -> {
                tank.setCompletedTripsSinceMaintenance(tank.getCompletedTripsSinceMaintenance() + 1);
                if (tank.getCompletedTripsSinceMaintenance() >= 5) {
                    tank.setStatus("UNSTABLE_VALVES");
                } else {
                    tank.setStatus("AVAILABLE");
                }
                tankRepository.save(tank);
            });
        }

        return toResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse accelerateOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setEtaMinutes(0);
        return toResponse(orderRepository.save(order));
    }
}
