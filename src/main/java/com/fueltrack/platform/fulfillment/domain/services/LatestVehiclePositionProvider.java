package com.fueltrack.platform.fulfillment.domain.services;

import com.fueltrack.platform.fulfillment.domain.model.VehiclePosition;
import java.util.Optional;

/**
 * Port that provides the latest vehicle position from telemetry data.
 */
public interface LatestVehiclePositionProvider {

    /**
     * Returns the latest known position for a vehicle.
     *
     * @param vehicleId the vehicle identifier
     * @return the latest vehicle position, if available
     */
    Optional<VehiclePosition> findLatestPosition(Long vehicleId);
}