package com.fueltrack.platform.fulfillment.infrastructure.persistence;

import com.fueltrack.platform.fulfillment.domain.model.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for delivery orders.
 */
public interface JpaDeliveryOrderRepository extends JpaRepository<DeliveryOrder, Long> {
}