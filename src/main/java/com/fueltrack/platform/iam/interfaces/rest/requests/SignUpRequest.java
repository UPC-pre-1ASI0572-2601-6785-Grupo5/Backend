package com.fueltrack.platform.iam.interfaces.rest.requests;

import com.fueltrack.platform.iam.domain.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request payload for user registration.
 *
 * @param email the user's email address
 * @param password the user's raw password
 * @param fullName the user's display name
 * @param role the user's role
 */
public record SignUpRequest(
        @Email @NotBlank String email,
        @NotBlank String password,
        @NotBlank String fullName,
        @NotNull UserRole role) {
}