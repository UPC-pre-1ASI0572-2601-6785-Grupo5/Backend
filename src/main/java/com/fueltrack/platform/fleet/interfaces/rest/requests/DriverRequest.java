package com.fueltrack.platform.fleet.interfaces.rest.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverRequest {
    @NotBlank
    private String name;
    
    @NotBlank
    private String licenseNumber;
    
    
    private String profilePicture;
}
