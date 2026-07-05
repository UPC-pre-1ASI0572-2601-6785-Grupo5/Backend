package com.fueltrack.platform.orderpayment.interfaces.rest.responses;

import com.fueltrack.platform.orderpayment.domain.model.OrderStatus;
import java.time.OffsetDateTime;

/**
 * Response payload for order views.
 */
public record OrderResponse(
        Long id,
        String fuelType,
        String name,
        Double gallons,
        String documentRef,
        OrderStatus status,
        OffsetDateTime createdAt,
        Long requesterId,
        String truckId,
        Long driverId,
        Long tankId,
        Long providerId,
        Integer etaMinutes,
        OffsetDateTime dispatchedAt,
        OffsetDateTime completedAt,
        String securityHash,
        String providerName,
        String providerAddress,
        String driverName,
        String driverProfilePicture,
        Integer driverTrips) {
}
