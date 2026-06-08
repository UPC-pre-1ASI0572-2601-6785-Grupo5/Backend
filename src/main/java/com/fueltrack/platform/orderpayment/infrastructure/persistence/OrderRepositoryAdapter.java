package com.fueltrack.platform.orderpayment.infrastructure.persistence;

import com.fueltrack.platform.orderpayment.domain.model.Order;
import com.fueltrack.platform.orderpayment.domain.services.OrderRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Infrastructure adapter for the order repository port.
 */
@Repository
public class OrderRepositoryAdapter implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;

    public OrderRepositoryAdapter(JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @Override
    public Order save(Order order) {
        return jpaOrderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaOrderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return jpaOrderRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    public List<Order> findByRequesterId(Long requesterId) {
        return jpaOrderRepository.findByRequesterIdOrderByCreatedAtDesc(requesterId);
    }
}
