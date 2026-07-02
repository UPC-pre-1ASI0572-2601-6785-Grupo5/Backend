package com.fueltrack.platform.orderpayment.interfaces.rest.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for dispatching an order with an assigned truck.
 */
public record DispatchOrderRequest(@JsonProperty("truckId") @NotBlank String truckId) {
}
