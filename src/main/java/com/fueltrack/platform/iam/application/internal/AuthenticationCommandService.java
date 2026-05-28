package com.fueltrack.platform.iam.application.internal;

import com.fueltrack.platform.iam.domain.model.User;
import com.fueltrack.platform.iam.domain.services.UserRepository;
import com.fueltrack.platform.iam.infrastructure.security.JwtService;
import com.fueltrack.platform.iam.interfaces.rest.requests.SignInRequest;
import com.fueltrack.platform.iam.interfaces.rest.requests.SignUpRequest;
import com.fueltrack.platform.iam.interfaces.rest.responses.AuthResponse;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Application service that handles authentication commands.
 */
@Service
public class AuthenticationCommandService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * Creates a new authentication command service.
     *
     * @param userRepository the user repository port
     * @param passwordEncoder the password encoder
     * @param authenticationManager the authentication manager
     * @param jwtService the JWT utility
     */
    public AuthenticationCommandService(UserRepository userRepository,
                                        PasswordEncoder passwordEncoder,
                                        AuthenticationManager authenticationManager,
                                        JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Registers a new user.
     *
     * @param request the registration payload
     * @return the authentication response with a JWT
     */
    @Transactional
    public AuthResponse register(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = User.builder()
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .role(request.role())
                .build();

        User savedUser = userRepository.save(user);
        return buildAuthResponse(savedUser);
    }

    /**
     * Authenticates a user and returns a JWT.
     *
     * @param request the login payload
     * @return the authentication response with a JWT
     */
    public AuthResponse login(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return buildAuthResponse(user);
    }

    private AuthResponse buildAuthResponse(User user) {
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(
                token,
                "Bearer",
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole());
    }
}