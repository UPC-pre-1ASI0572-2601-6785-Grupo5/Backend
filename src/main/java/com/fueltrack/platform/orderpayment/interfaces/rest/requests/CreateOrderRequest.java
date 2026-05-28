package com.fueltrack.platform.orderpayment.interfaces.rest.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Request payload for creating a fuel order.
 *
 * @param clientId the client identifier
 * @param fuelType the fuel type
 * @param quantityLiters the requested quantity in liters
 */
public record CreateOrderRequest(
        @NotNull Long clientId,
        @NotBlank String fuelType,
        @NotNull @Positive Double quantityLiters) {
}