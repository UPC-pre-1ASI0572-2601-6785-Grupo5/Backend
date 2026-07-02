package com.fueltrack.platform.orderpayment.interfaces.rest.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for dispatching an order with an assigned truck.
 */
public class DispatchOrderRequest {
    @NotBlank
    private String truckId;

    public DispatchOrderRequest() {}

    public DispatchOrderRequest(@JsonProperty("truckId") String truckId) {
        this.truckId = truckId;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }
}
