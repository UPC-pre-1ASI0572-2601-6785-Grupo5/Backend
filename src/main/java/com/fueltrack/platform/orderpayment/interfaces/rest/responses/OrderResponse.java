package com.fueltrack.platform.orderpayment.interfaces.rest.responses;

import com.fueltrack.platform.orderpayment.domain.model.OrderStatus;
import java.time.OffsetDateTime;

/**
 * Response payload for order views.
 */
public record OrderResponse(
        Long id,
        String fuelType,
        Double gallons,
        String documentRef,
        OrderStatus status,
        OffsetDateTime createdAt,
        Long requesterId,
        String truckId) {
}
