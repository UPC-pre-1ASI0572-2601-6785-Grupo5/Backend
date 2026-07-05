package com.fueltrack.platform.orderpayment.domain.model;

/**
 * Lifecycle status for a fuel order.
 */
public enum OrderStatus {
    PENDING_APPROVAL,
    APPROVED,
    IN_TRANSIT,
    DELIVERED,
    COMPLETED
}