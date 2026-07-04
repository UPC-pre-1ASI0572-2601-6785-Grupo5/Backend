package com.fueltrack.platform.iam.application.internal;

import com.fueltrack.platform.iam.domain.model.User;
import com.fueltrack.platform.iam.domain.services.UserRepository;
import com.fueltrack.platform.iam.interfaces.rest.requests.ChangePasswordRequest;
import com.fueltrack.platform.iam.interfaces.rest.requests.ToggleMfaRequest;
import com.fueltrack.platform.iam.interfaces.rest.requests.UpdateProfileRequest;
import com.fueltrack.platform.iam.interfaces.rest.responses.AuthResponse;
import com.fueltrack.platform.iam.infrastructure.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserManagementService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserManagementService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> updateProfile(Long id, UpdateProfileRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.fullName() != null && !request.fullName().isBlank()) {
            user.setFullName(request.fullName());
        }
        if (request.email() != null && !request.email().isBlank()) {
            user.setEmail(request.email());
        }
        if (request.companyName() != null) user.setCompanyName(request.companyName());
        if (request.taxId() != null) user.setTaxId(request.taxId());
        if (request.phone() != null) user.setPhone(request.phone());
        if (request.address() != null) user.setAddress(request.address());
        return Optional.of(userRepository.save(user));
    }

    public Optional<User> changePassword(Long id, ChangePasswordRequest request) {
        return userRepository.findById(id).map(user -> {
            if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
                throw new IllegalArgumentException("La contraseña actual es incorrecta.");
            }
            user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
            return userRepository.save(user);
        });
    }

    public Optional<User> toggleMfa(Long id, ToggleMfaRequest request) {
        return userRepository.findById(id).map(user -> {
            user.setMfaEnabled(request.enableMfa());
            return userRepository.save(user);
        });
    }

    public AuthResponse toAuthResponse(User user) {
        return toAuthResponseWithToken(user, null);
    }

    public AuthResponse toAuthResponseWithToken(User user, String token) {
        return new AuthResponse(
                token,
                token != null ? "Bearer" : null,
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole(),
                user.getCompanyName(),
                user.getTaxId(),
                user.getPhone(),
                user.getAddress(),
                Boolean.TRUE.equals(user.getMfaEnabled()),
                user.getSubscriptionPlan());
    }
}
