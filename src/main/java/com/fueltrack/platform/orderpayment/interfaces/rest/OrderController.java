package com.fueltrack.platform.orderpayment.interfaces.rest;

import com.fueltrack.platform.orderpayment.application.internal.OrderCommandService;
import com.fueltrack.platform.orderpayment.interfaces.rest.requests.CreateOrderRequest;
import com.fueltrack.platform.orderpayment.interfaces.rest.responses.OrderResponse;
import com.fueltrack.platform.orderpayment.interfaces.rest.responses.PaymentValidationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for order lifecycle operations.
 */
@Tag(name = "OrderPayment", description = "Order creation and payment validation endpoints")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderCommandService orderCommandService;

    /**
     * Creates a new order controller.
     *
     * @param orderCommandService the application service
     */
    public OrderController(OrderCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    /**
     * Creates a new order.
     *
     * @param request the order request payload
     * @return the created order
     */
    @Operation(summary = "Create a new fuel order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully",
                    content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid order request")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderCommandService.createOrder(request);
    }

    /**
     * Gets an order by identifier.
     *
     * @param id the order identifier
     * @return the order view
     */
    @Operation(summary = "Get an order by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order found",
                    content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        return orderCommandService.getOrder(id);
    }

    /**
     * Validates payment for the specified order.
     *
     * @param id the order identifier
     * @return the payment validation result
     */
    @Operation(summary = "Validate payment for an order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment validated",
                    content = @Content(schema = @Schema(implementation = PaymentValidationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Payment validation failed"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PostMapping("/{id}/validate-payment")
    public PaymentValidationResponse validatePayment(@PathVariable Long id) {
        return orderCommandService.validatePayment(id);
    }
}