package com.fueltrack.platform.orderpayment.domain.services;

import com.fueltrack.platform.orderpayment.domain.model.Order;
import java.util.List;
import java.util.Optional;

/**
 * Repository port for managing orders.
 */
public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAll();

    List<Order> findByRequesterId(Long requesterId);

    boolean existsById(Long id);

    void deleteById(Long id);
    
    List<Order> findByProviderIdIsNullOrProviderId(Long providerId);
}
