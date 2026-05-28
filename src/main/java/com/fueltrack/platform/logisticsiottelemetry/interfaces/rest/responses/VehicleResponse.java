package com.fueltrack.platform.logisticsiottelemetry.interfaces.rest.responses;

import com.fueltrack.platform.logisticsiottelemetry.domain.model.VehicleStatus;

/**
 * Response payload for vehicle listings.
 *
 * @param id the vehicle identifier
 * @param plate the license plate
 * @param driverId the driver identifier
 * @param status the vehicle status
 */
public record VehicleResponse(Long id, String plate, Long driverId, VehicleStatus status) {
}