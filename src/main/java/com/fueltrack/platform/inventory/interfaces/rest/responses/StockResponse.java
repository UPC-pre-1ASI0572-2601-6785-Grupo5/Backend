package com.fueltrack.platform.inventory.interfaces.rest.responses;

/**
 * Response payload for a single fuel stock entry.
 */
public record StockResponse(
        Long id,
        String fuelType,
        Double currentGallons,
        Double maxCapacityGallons) {
}
