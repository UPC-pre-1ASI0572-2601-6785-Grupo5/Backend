package com.fueltrack.platform.orderpayment.interfaces.rest.responses;

import com.fueltrack.platform.orderpayment.domain.model.OrderStatus;

/**
 * Response payload returned after payment validation.
 *
 * @param orderId the order identifier
 * @param validated whether the payment was validated
 * @param status the resulting order status
 */
public record PaymentValidationResponse(Long orderId, boolean validated, OrderStatus status) {
}