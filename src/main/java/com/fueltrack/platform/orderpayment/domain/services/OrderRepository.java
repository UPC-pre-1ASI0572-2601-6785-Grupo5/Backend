package com.fueltrack.platform.orderpayment.domain.services;

import com.fueltrack.platform.orderpayment.domain.model.Order;
import java.util.Optional;

/**
 * Repository port for managing orders.
 */
public interface OrderRepository {

    /**
     * Stores an order.
     *
     * @param order the order to store
     * @return the stored order
     */
    Order save(Order order);

    /**
     * Finds an order by identifier.
     *
     * @param id the order identifier
     * @return the matching order, if any
     */
    Optional<Order> findById(Long id);
}