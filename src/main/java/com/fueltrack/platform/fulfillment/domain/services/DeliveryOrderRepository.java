package com.fueltrack.platform.fulfillment.domain.services;

import com.fueltrack.platform.fulfillment.domain.model.DeliveryOrder;
import java.util.Optional;

/**
 * Repository port for fulfillment delivery orders.
 */
public interface DeliveryOrderRepository {

    /**
     * Stores a delivery order.
     *
     * @param deliveryOrder the delivery order to save
     * @return the stored delivery order
     */
    DeliveryOrder save(DeliveryOrder deliveryOrder);

    /**
     * Finds a delivery order by order identifier.
     *
     * @param orderId the order identifier
     * @return the matching delivery order, if any
     */
    Optional<DeliveryOrder> findById(Long orderId);
}