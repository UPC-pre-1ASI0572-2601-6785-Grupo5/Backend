package com.fueltrack.platform.fulfillment.domain.model;

/**
 * Circular geofence used to validate whether a vehicle can unlock the valve.
 *
 * @param centerLat the center latitude
 * @param centerLng the center longitude
 * @param radiusMeters the radius in meters
 */
public record Geofence(Double centerLat, Double centerLng, Double radiusMeters) {

    private static final double EARTH_RADIUS_METERS = 6_371_000d;

    /**
     * Checks whether the provided coordinates are inside the geofence.
     *
     * @param latitude the latitude to validate
     * @param longitude the longitude to validate
     * @return true when the point is inside the geofence
     */
    public boolean isInside(Double latitude, Double longitude) {
        double latDistance = Math.toRadians(latitude - centerLat);
        double lngDistance = Math.toRadians(longitude - centerLng);
        double centerLatRadians = Math.toRadians(centerLat);
        double latitudeRadians = Math.toRadians(latitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2)
                * Math.cos(centerLatRadians) * Math.cos(latitudeRadians);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS_METERS * c;

        return distance <= radiusMeters;
    }
}