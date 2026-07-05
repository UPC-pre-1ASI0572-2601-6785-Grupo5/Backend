package com.fueltrack.platform.orderpayment.infrastructure.persistence;

import com.fueltrack.platform.orderpayment.domain.model.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for payments.
 */
public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Finds the latest payment for an order.
     *
     * @param orderId the order identifier
     * @return the latest payment, if any
     */
    Optional<Payment> findTopByOrder_IdOrderByCreatedAtDesc(Long orderId);

    /**
     * Deletes all payments for a given order.
     *
     * @param orderId the order identifier
     */
    void deleteByOrder_Id(Long orderId);
}