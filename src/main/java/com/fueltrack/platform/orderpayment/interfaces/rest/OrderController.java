package com.fueltrack.platform.orderpayment.interfaces.rest;

import com.fueltrack.platform.iam.domain.model.User;
import com.fueltrack.platform.iam.domain.model.UserRole;
import com.fueltrack.platform.iam.domain.services.UserRepository;
import com.fueltrack.platform.orderpayment.application.internal.OrderCommandService;
import com.fueltrack.platform.orderpayment.interfaces.rest.requests.CreateOrderRequest;
import com.fueltrack.platform.orderpayment.interfaces.rest.requests.DispatchOrderRequest;
import com.fueltrack.platform.orderpayment.interfaces.rest.responses.OrderResponse;
import com.fueltrack.platform.orderpayment.interfaces.rest.responses.PaymentValidationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for order lifecycle operations.
 */
@Tag(name = "Orders", description = "Order creation, listing, approval and dispatch")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final UserRepository userRepository;

    public OrderController(OrderCommandService orderCommandService, UserRepository userRepository) {
        this.orderCommandService = orderCommandService;
        this.userRepository = userRepository;
    }

    @Operation(summary = "List orders — REQUESTER sees own orders, PROVIDER sees all")
    @GetMapping
    public List<OrderResponse> listOrders(@AuthenticationPrincipal UserDetails currentUser) {
        User user = resolveUser(currentUser);
        return orderCommandService.listOrders(user.getId(), user.getRole() == UserRole.PROVIDER);
    }

    @Operation(summary = "Create a new fuel order")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request,
                                     @AuthenticationPrincipal UserDetails currentUser) {
        User user = resolveUser(currentUser);
        return orderCommandService.createOrder(request, user.getId());
    }

    @Operation(summary = "Get an order by id")
    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        return orderCommandService.getOrder(id);
    }

    @Operation(summary = "Approve an order (PROVIDER only)")
    @PatchMapping("/{id}/approve")
    public OrderResponse approveOrder(@PathVariable Long id) {
        return orderCommandService.approveOrder(id);
    }

    @Operation(summary = "Update gallons of an order (Admin/Manual fix)")
    @PatchMapping("/{id}/gallons")
    public OrderResponse updateGallons(@PathVariable Long id, @RequestBody Double newGallons) {
        return orderCommandService.updateGallons(id, newGallons);
    }

    @Operation(summary = "Dispatch an order with an assigned truck (PROVIDER only)")
    @PatchMapping("/{id}/dispatch")
    public OrderResponse dispatchOrder(@PathVariable Long id,
                                       @Valid @RequestBody DispatchOrderRequest request) {
        return orderCommandService.dispatchOrder(id, request.getTruckId());
    }

    @Operation(summary = "Validate payment for an order")
    @PostMapping("/{id}/validate-payment")
    public PaymentValidationResponse validatePayment(@PathVariable Long id) {
        return orderCommandService.validatePayment(id);
    }

    @Operation(summary = "Cancel/Delete an order")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        orderCommandService.deleteOrder(id);
    }

    private User resolveUser(UserDetails currentUser) {
        return userRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Authenticated user not found"));
    }
}
