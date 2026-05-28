package com.fueltrack.platform.orderpayment.infrastructure.persistence;

import com.fueltrack.platform.orderpayment.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for orders.
 */
public interface JpaOrderRepository extends JpaRepository<Order, Long> {
}