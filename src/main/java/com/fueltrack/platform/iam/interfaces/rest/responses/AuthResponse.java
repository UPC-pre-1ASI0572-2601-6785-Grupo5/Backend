package com.fueltrack.platform.iam.interfaces.rest.responses;

import com.fueltrack.platform.iam.domain.model.UserRole;

/**
 * Authentication response containing the issued JWT and user profile data.
 *
 * @param token the JWT token
 * @param tokenType the token type, typically Bearer
 * @param id the authenticated user's identifier
 * @param email the authenticated user's email address
 * @param name the authenticated user's display name
 * @param role the authenticated user's role
 */
public record AuthResponse(
        String token,
        String tokenType,
        Long id,
        String email,
        String name,
        UserRole role) {
}