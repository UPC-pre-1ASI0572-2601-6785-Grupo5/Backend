package com.fueltrack.platform.orderpayment.infrastructure.persistence;

import com.fueltrack.platform.orderpayment.domain.model.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for orders.
 */
public interface JpaOrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByRequesterIdOrderByCreatedAtDesc(Long requesterId);

    List<Order> findAllByOrderByCreatedAtDesc();
    
    List<Order> findByProviderIdIsNullOrProviderIdOrderByCreatedAtDesc(Long providerId);
}
