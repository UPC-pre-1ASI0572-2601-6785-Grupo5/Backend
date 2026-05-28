package com.fueltrack.platform.fulfillment.domain.model;

/**
 * Latest position reported by a delivery vehicle.
 *
 * @param latitude the latitude
 * @param longitude the longitude
 */
public record VehiclePosition(Double latitude, Double longitude) {
}