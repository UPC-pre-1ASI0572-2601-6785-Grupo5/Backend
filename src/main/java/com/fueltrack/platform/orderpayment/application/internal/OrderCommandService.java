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

    public OrderCommandService(OrderRepository orderRepository,
                               PaymentRepository paymentRepository,
                               PaymentGatewayClient paymentGatewayClient,
                               InventoryDischargePort inventoryDischargePort) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.paymentGatewayClient = paymentGatewayClient;
        this.inventoryDischargePort = inventoryDischargePort;
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request, Long requesterId) {
        Order order = Order.builder()
                .fuelType(request.fuelType())
                .gallons(request.gallons())
                .documentRef(request.documentRef())
                .status(OrderStatus.PENDING_APPROVAL)
                .createdAt(OffsetDateTime.now())
                .requesterId(requesterId)
                .build();

        return toResponse(orderRepository.save(order));
    }

    public List<OrderResponse> listOrders(Long requesterId, boolean isProvider) {
        List<Order> orders = isProvider
                ? orderRepository.findAll()
                : orderRepository.findByRequesterId(requesterId);
        return orders.stream().map(this::toResponse).toList();
    }

    public OrderResponse getOrder(Long id) {
        return toResponse(orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found")));
    }

    @Transactional
    public OrderResponse approveOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (order.getStatus() != OrderStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Only orders in PENDING_APPROVAL can be approved");
        }
        order.setStatus(OrderStatus.APPROVED);
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
    public OrderResponse dispatchOrder(Long id, String truckId) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        if (order.getStatus() != OrderStatus.APPROVED) {
            throw new IllegalStateException("Only approved orders can be dispatched");
        }
        order.setTruckId(truckId);
        order.setStatus(OrderStatus.IN_TRANSIT);
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
                order.getGallons(),
                order.getDocumentRef(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getRequesterId(),
                order.getTruckId());
    }
}
