package com.fueltrack.platform.inventory.interfaces.rest.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Request payload for refilling a fuel tank.
 */
public record RefillRequest(
        @NotBlank String fuelType,
        @NotNull @Positive Double gallons) {
}
