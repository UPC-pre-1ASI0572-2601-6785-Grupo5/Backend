package com.fueltrack.platform.orderpayment.interfaces.rest.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignatureRequest {
    @NotBlank(message = "Signature data is required")
    private String signature;
}
