package com.fueltrack.platform.logisticsiottelemetry.interfaces.rest.requests;

import jakarta.validation.constraints.NotNull;

/**
 * Request payload for ingesting telemetry from a vehicle.
 *
 * @param latitude the current latitude
 * @param longitude the current longitude
 * @param fuelLevel the measured fuel level
 * @param pressure the measured pressure
 */
public record TelemetryReadingRequest(
        @NotNull Double latitude,
        @NotNull Double longitude,
        @NotNull Double fuelLevel,
        @NotNull Double pressure) {
}