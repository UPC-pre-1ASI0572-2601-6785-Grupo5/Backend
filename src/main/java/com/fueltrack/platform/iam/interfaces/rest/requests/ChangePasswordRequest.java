package com.fueltrack.platform.iam.interfaces.rest.requests;

public record ChangePasswordRequest(
        String currentPassword,
        String newPassword) {
}
