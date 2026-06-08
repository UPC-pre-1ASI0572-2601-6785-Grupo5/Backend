package com.fueltrack.platform.orderpayment.interfaces.rest.requests;

import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for dispatching an order with an assigned truck.
 */
public record DispatchOrderRequest(@NotBlank String truckId) {
}
