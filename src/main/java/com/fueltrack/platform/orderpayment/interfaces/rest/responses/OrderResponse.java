package com.fueltrack.platform.orderpayment.interfaces.rest.responses;

import com.fueltrack.platform.orderpayment.domain.model.OrderStatus;
import java.time.OffsetDateTime;

/**
 * Response payload for order views.
 *
 * @param id the order identifier
 * @param fuelType the fuel type
 * @param gallons the requested quantity in gallons
 * @param documentRef the reference document identifier
 * @param status the current order status
 * @param createdAt the creation timestamp
 */
public record OrderResponse(
        Long id,
        String fuelType,
        Double gallons,
        String documentRef,
        OrderStatus status,
        OffsetDateTime createdAt) {
}