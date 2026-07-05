package com.fueltrack.platform.orderpayment.interfaces.rest.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for dispatching an order with an assigned truck.
 */
public class DispatchOrderRequest {
    private String truckId;
    private Long driverId;
    
    private Long tankId;

    public DispatchOrderRequest() {}

    public DispatchOrderRequest(@JsonProperty("truckId") String truckId, @JsonProperty("driverId") Long driverId, @JsonProperty("tankId") Long tankId) {
        this.truckId = truckId;
        this.driverId = driverId;
        this.tankId = tankId;
    }

    public String getTruckId() {
        return truckId;
    }

    public void setTruckId(String truckId) {
        this.truckId = truckId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getTankId() {
        return tankId;
    }

    public void setTankId(Long tankId) {
        this.tankId = tankId;
    }
}
