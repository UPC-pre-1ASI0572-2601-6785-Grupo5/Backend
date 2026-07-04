package com.fueltrack.platform.iam.interfaces.rest.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for user login.
 *
 * @param email the user's email address
 * @param password the user's raw password
 */
public record SignInRequest(
        @NotBlank String email,
        @NotBlank String password) {
}