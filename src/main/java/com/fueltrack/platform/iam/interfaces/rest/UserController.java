package com.fueltrack.platform.iam.interfaces.rest;

import com.fueltrack.platform.iam.application.internal.UserManagementService;
import com.fueltrack.platform.iam.interfaces.rest.requests.ChangePasswordRequest;
import com.fueltrack.platform.iam.interfaces.rest.requests.ToggleMfaRequest;
import com.fueltrack.platform.iam.interfaces.rest.requests.UpdateProfileRequest;
import com.fueltrack.platform.iam.interfaces.rest.responses.AuthResponse;
import com.fueltrack.platform.iam.infrastructure.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserManagementService userManagementService;
    private final JwtService jwtService;

    public UserController(UserManagementService userManagementService, JwtService jwtService) {
        this.userManagementService = userManagementService;
        this.jwtService = jwtService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthResponse> getUserProfile(@PathVariable Long id) {
        return userManagementService.getUserById(id)
                .map(user -> ResponseEntity.ok(userManagementService.toAuthResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<AuthResponse> updateProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileRequest request) {
        return userManagementService.updateProfile(id, request)
                .map(user -> ResponseEntity.ok(userManagementService.toAuthResponseWithToken(user, jwtService.generateToken(user.getEmail()))))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request) {
        try {
            return userManagementService.changePassword(id, request)
                    .map(user -> ResponseEntity.ok().build())
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/mfa")
    public ResponseEntity<AuthResponse> toggleMfa(
            @PathVariable Long id,
            @RequestBody ToggleMfaRequest request) {
        return userManagementService.toggleMfa(id, request)
                .map(user -> ResponseEntity.ok(userManagementService.toAuthResponse(user)))
                .orElse(ResponseEntity.notFound().build());
    }
}
