package com.fueltrack.platform.orderpayment.domain.services;

import java.math.BigDecimal;

/**
 * Port used to validate a payment through an external payment gateway.
 */
public interface PaymentGatewayClient {

    /**
     * Validates a payment request.
     *
     * @param orderId the related order identifier
     * @param amount the requested amount
     * @return true when the gateway approves the payment
     */
    boolean validatePayment(Long orderId, BigDecimal amount);
}