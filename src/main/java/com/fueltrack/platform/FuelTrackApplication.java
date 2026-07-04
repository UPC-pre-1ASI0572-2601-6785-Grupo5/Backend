package com.fueltrack.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fueltrack.platform.iam.domain.model.User;
import com.fueltrack.platform.iam.domain.model.UserRole;
import com.fueltrack.platform.iam.domain.services.UserRepository;

/**
 * Main entry point for the FuelTrack backend application.
 */
@SpringBootApplication
public class FuelTrackApplication {

    /**
     * Starts the Spring Boot application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(FuelTrackApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("123").isEmpty()) {
                User user1 = new User();
                user1.setEmail("123");
                user1.setPasswordHash(passwordEncoder.encode("123"));
                user1.setFullName("Cliente Especial");
                user1.setRole(UserRole.REQUESTER);
                userRepository.save(user1);
            }
            if (userRepository.findByEmail("321").isEmpty()) {
                User user2 = new User();
                user2.setEmail("321");
                user2.setPasswordHash(passwordEncoder.encode("321"));
                user2.setFullName("Proveedor Especial");
                user2.setRole(UserRole.PROVIDER);
                userRepository.save(user2);
            }
        };
    }
}