package com.fueltrack.platform.fulfillment.infrastructure.persistence;

import com.fueltrack.platform.fulfillment.domain.model.DeliveryOrder;
import com.fueltrack.platform.fulfillment.domain.services.DeliveryOrderRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Infrastructure adapter for the delivery order repository port.
 */
@Repository
public class DeliveryOrderRepositoryAdapter implements DeliveryOrderRepository {

    private final JpaDeliveryOrderRepository jpaDeliveryOrderRepository;

    /**
     * Creates a new adapter instance.
     *
     * @param jpaDeliveryOrderRepository the Spring Data repository
     */
    public DeliveryOrderRepositoryAdapter(JpaDeliveryOrderRepository jpaDeliveryOrderRepository) {
        this.jpaDeliveryOrderRepository = jpaDeliveryOrderRepository;
    }

    @Override
    public DeliveryOrder save(DeliveryOrder deliveryOrder) {
        return jpaDeliveryOrderRepository.save(deliveryOrder);
    }

    @Override
    public Optional<DeliveryOrder> findById(Long orderId) {
        return jpaDeliveryOrderRepository.findById(orderId);
    }
}