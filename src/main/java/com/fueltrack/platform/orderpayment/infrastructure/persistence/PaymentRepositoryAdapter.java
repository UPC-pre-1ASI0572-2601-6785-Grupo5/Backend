package com.fueltrack.platform.orderpayment.infrastructure.persistence;

import com.fueltrack.platform.orderpayment.domain.model.Payment;
import com.fueltrack.platform.orderpayment.domain.services.PaymentRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Infrastructure adapter for the payment repository port.
 */
@Repository
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final JpaPaymentRepository jpaPaymentRepository;

    /**
     * Creates a new payment repository adapter.
     *
     * @param jpaPaymentRepository the Spring Data repository
     */
    public PaymentRepositoryAdapter(JpaPaymentRepository jpaPaymentRepository) {
        this.jpaPaymentRepository = jpaPaymentRepository;
    }

    @Override
    public Payment save(Payment payment) {
        return jpaPaymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> findTopByOrder_IdOrderByCreatedAtDesc(Long orderId) {
        return jpaPaymentRepository.findTopByOrder_IdOrderByCreatedAtDesc(orderId);
    }
}