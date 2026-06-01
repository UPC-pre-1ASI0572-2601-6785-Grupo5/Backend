package com.fueltrack.platform.orderpayment.interfaces.rest.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Request payload for creating a fuel order.
 *
 * @param fuelType the fuel type
 * @param gallons the requested quantity in gallons
 * @param documentRef the reference document identifier
 */
public record CreateOrderRequest(
        @NotBlank String fuelType,
        @NotNull @Positive Double gallons,
        @NotBlank String documentRef) {
}