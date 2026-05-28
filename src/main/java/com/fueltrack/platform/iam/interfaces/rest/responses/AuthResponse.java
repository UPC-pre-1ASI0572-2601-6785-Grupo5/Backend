package com.fueltrack.platform.iam.interfaces.rest.responses;

import com.fueltrack.platform.iam.domain.model.UserRole;

/**
 * Authentication response containing the issued JWT and user profile data.
 *
 * @param token the JWT token
 * @param tokenType the token type, typically Bearer
 * @param userId the authenticated user's identifier
 * @param email the authenticated user's email address
 * @param fullName the authenticated user's full name
 * @param role the authenticated user's role
 */
public record AuthResponse(
        String token,
        String tokenType,
        Long userId,
        String email,
        String fullName,
        UserRole role) {
}