package com.fueltrack.platform.iam.interfaces.rest;

import com.fueltrack.platform.iam.application.internal.AuthenticationCommandService;
import com.fueltrack.platform.iam.interfaces.rest.requests.SignInRequest;
import com.fueltrack.platform.iam.interfaces.rest.requests.SignUpRequest;
import com.fueltrack.platform.iam.interfaces.rest.responses.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for authentication operations.
 */
@Tag(name = "Authentication", description = "User registration and login endpoints")
@RestController
@RequestMapping("/api/v1/authentication")
public class AuthController {

    private final AuthenticationCommandService authenticationCommandService;

    /**
     * Creates a new authentication controller.
     *
     * @param authenticationCommandService the authentication application service
     */
    public AuthController(AuthenticationCommandService authenticationCommandService) {
        this.authenticationCommandService = authenticationCommandService;
    }

    /**
     * Registers a new user account.
     *
     * @param request the registration payload
     * @return the created authentication response
     */
    @Operation(summary = "Sign up a new user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid registration request")
    })
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse signUp(@Valid @RequestBody SignUpRequest request) {
        return authenticationCommandService.register(request);
    }

    /**
     * Authenticates an existing user.
     *
     * @param request the login payload
     * @return the authentication response with JWT
     */
    @Operation(summary = "Sign in an existing user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User authenticated successfully",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid login request"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/sign-in")
    public AuthResponse signIn(@Valid @RequestBody SignInRequest request) {
        return authenticationCommandService.login(request);
    }
}