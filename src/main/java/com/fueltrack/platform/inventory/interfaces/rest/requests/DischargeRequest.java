package com.fueltrack.platform.inventory.interfaces.rest.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Request payload for discharging fuel from a tank.
 */
public record DischargeRequest(
        @NotBlank String fuelType,
        @NotNull @Positive Double gallons) {
}
