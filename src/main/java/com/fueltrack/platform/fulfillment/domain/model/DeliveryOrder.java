package com.fueltrack.platform.fulfillment.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Delivery order aggregate that tracks valve and delivery completion state.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "delivery_orders")
public class DeliveryOrder {

    @Id
    private Long orderId;

    @Column(nullable = false)
    private Long vehicleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ValveStatus valveStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DeliveryStatus deliveryStatus;
}