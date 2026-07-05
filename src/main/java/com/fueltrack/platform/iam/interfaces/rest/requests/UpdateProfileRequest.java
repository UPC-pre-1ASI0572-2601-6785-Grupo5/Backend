package com.fueltrack.platform.iam.interfaces.rest.requests;

public record UpdateProfileRequest(
        String fullName,
        String email,
        String companyName,
        String taxId,
        String phone,
        String address,
        String subscriptionPlan,
        String profilePicture) {
}
