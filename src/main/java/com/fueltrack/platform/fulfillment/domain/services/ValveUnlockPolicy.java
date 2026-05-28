package com.fueltrack.platform.fulfillment.domain.services;

import com.fueltrack.platform.fulfillment.domain.model.Geofence;
import com.fueltrack.platform.fulfillment.domain.model.VehiclePosition;
import org.springframework.stereotype.Component;

/**
 * Domain service that decides whether a delivery valve can be unlocked.
 */
@Component
public class ValveUnlockPolicy {

    /**
     * Checks whether the vehicle is inside the configured geofence.
     *
     * @param geofence the configured geofence
     * @param vehiclePosition the latest vehicle position
     */
    public void assertCanUnlock(Geofence geofence, VehiclePosition vehiclePosition) {
        if (vehiclePosition == null || !geofence.isInside(vehiclePosition.latitude(), vehiclePosition.longitude())) {
            throw new IllegalStateException("Valve can only be unlocked when the vehicle is inside the geofence");
        }
    }
}