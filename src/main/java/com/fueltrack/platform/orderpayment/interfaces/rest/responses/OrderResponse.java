package com.fueltrack.platform.orderpayment.interfaces.rest.responses;

import com.fueltrack.platform.orderpayment.domain.model.OrderStatus;
import java.time.OffsetDateTime;

/**
 * Response payload for order views.
 *
 * @param id the order identifier
 * @param clientId the client identifier
 * @param fuelType the fuel type
 * @param quantityLiters the requested quantity
 * @param status the current order status
 * @param createdAt the creation timestamp
 */
public record OrderResponse(
        Long id,
        Long clientId,
        String fuelType,
        Double quantityLiters,
        OrderStatus status,
        OffsetDateTime createdAt) {
}