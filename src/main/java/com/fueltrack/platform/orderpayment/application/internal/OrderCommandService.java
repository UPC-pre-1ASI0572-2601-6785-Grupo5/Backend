package com.fueltrack.platform.orderpayment.application.internal;

import com.fueltrack.platform.orderpayment.domain.model.Order;
import com.fueltrack.platform.orderpayment.domain.model.OrderStatus;
import com.fueltrack.platform.orderpayment.domain.model.Payment;
import com.fueltrack.platform.orderpayment.domain.services.OrderRepository;
import com.fueltrack.platform.orderpayment.domain.services.PaymentGatewayClient;
import com.fueltrack.platform.orderpayment.domain.services.PaymentRepository;
import com.fueltrack.platform.orderpayment.interfaces.rest.requests.CreateOrderRequest;
import com.fueltrack.platform.orderpayment.interfaces.rest.responses.OrderResponse;
import com.fueltrack.platform.orderpayment.interfaces.rest.responses.PaymentValidationResponse;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Service;

/**
 * Application service for order and payment commands.
 */
@Service
public class OrderCommandService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayClient paymentGatewayClient;

    /**
     * Creates a new order command service.
     *
     * @param orderRepository the order repository port
     * @param paymentRepository the payment repository port
     * @param paymentGatewayClient the payment gateway client port
     */
    public OrderCommandService(OrderRepository orderRepository,
                               PaymentRepository paymentRepository,
                               PaymentGatewayClient paymentGatewayClient) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.paymentGatewayClient = paymentGatewayClient;
    }

    /**
     * Creates a new order.
     *
     * @param request the order request
     * @return the order response
     */
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = Order.builder()
                .clientId(request.clientId())
                .fuelType(request.fuelType())
                .quantityLiters(request.quantityLiters())
                .status(OrderStatus.PENDING)
                .createdAt(OffsetDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);
        return toResponse(savedOrder);
    }

    /**
     * Returns an order by identifier.
     *
     * @param id the order identifier
     * @return the order response
     */
    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return toResponse(order);
    }

    /**
     * Validates payment for an order and updates the order status when approved.
     *
     * @param id the order identifier
     * @return the validation response
     */
    @Transactional
    public PaymentValidationResponse validatePayment(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        BigDecimal amount = BigDecimal.valueOf(order.getQuantityLiters()).multiply(BigDecimal.valueOf(1.00d));
        boolean validated = paymentGatewayClient.validatePayment(order.getId(), amount);
        if (!validated) {
            throw new IllegalArgumentException("Payment validation failed");
        }

        order.setStatus(OrderStatus.VALIDATED);
        orderRepository.save(order);

        Payment payment = Payment.builder()
                .order(order)
                .amount(amount)
                .paymentStatus("VALIDATED")
                .createdAt(OffsetDateTime.now())
                .build();
        paymentRepository.save(payment);

        return new PaymentValidationResponse(order.getId(), true, order.getStatus());
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getClientId(),
                order.getFuelType(),
                order.getQuantityLiters(),
                order.getStatus(),
                order.getCreatedAt());
    }
}