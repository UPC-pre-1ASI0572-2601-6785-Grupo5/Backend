package com.fueltrack.platform.orderpayment.domain.services;

import com.fueltrack.platform.orderpayment.domain.model.Payment;
import java.util.Optional;

/**
 * Repository port for managing payments.
 */
public interface PaymentRepository {

    /**
     * Stores a payment record.
     *
     * @param payment the payment to store
     * @return the stored payment
     */
    Payment save(Payment payment);

    /**
     * Finds the latest payment for an order.
     *
     * @param orderId the order identifier
     * @return the matching payment, if any
     */
    Optional<Payment> findTopByOrder_IdOrderByCreatedAtDesc(Long orderId);

    /**
     * Deletes all payments for a given order.
     *
     * @param orderId the order identifier
     */
    void deleteByOrderId(Long orderId);
}