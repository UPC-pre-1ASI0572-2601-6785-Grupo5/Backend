package com.fueltrack.platform.iam.interfaces.rest.requests;

public record UpdateProfileRequest(
        String companyName,
        String taxId,
        String phone,
        String address) {
}
