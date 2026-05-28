package com.fueltrack.platform.iam.infrastructure.persistence;

import com.fueltrack.platform.iam.domain.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for users.
 */
public interface JpaUserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by email.
     *
     * @param email the email to search for
     * @return the matching user, if any
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks whether an email is already registered.
     *
     * @param email the email to check
     * @return true when the email exists
     */
    boolean existsByEmail(String email);
}