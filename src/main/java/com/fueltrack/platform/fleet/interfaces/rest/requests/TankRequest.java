package com.fueltrack.platform.fleet.interfaces.rest.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TankRequest {
    @NotBlank
    private String plate;
    
    @NotBlank
    private String model;
    
    @NotNull
    private Double capacityGallons;
    
    
    @NotNull
    private Double currentFuelGallons;
}
