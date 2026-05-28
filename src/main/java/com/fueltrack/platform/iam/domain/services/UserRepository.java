package com.fueltrack.platform.iam.domain.services;

import com.fueltrack.platform.iam.domain.model.User;
import java.util.Optional;

/**
 * Repository port for persisting and retrieving users.
 */
public interface UserRepository {

    /**
     * Stores the provided user.
     *
     * @param user the user to persist
     * @return the saved user
     */
    User save(User user);

    /**
     * Finds a user by email.
     *
     * @param email the email to search for
     * @return the matching user, if any
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by identifier.
     *
     * @param id the identifier to search for
     * @return the matching user, if any
     */
    Optional<User> findById(Long id);

    /**
     * Checks whether an email is already registered.
     *
     * @param email the email to check
     * @return true when the email already exists
     */
    boolean existsByEmail(String email);
}