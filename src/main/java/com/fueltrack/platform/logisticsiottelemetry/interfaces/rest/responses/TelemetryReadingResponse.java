package com.fueltrack.platform.logisticsiottelemetry.interfaces.rest.responses;

import java.time.OffsetDateTime;

/**
 * Response payload for telemetry readings.
 *
 * @param vehicleId the vehicle identifier
 * @param latitude the latitude
 * @param longitude the longitude
 * @param fuelLevel the fuel level
 * @param pressure the pressure
 * @param timestamp the reading timestamp
 */
public record TelemetryReadingResponse(
        Long vehicleId,
        Double latitude,
        Double longitude,
        Double fuelLevel,
        Double pressure,
        OffsetDateTime timestamp) {
}