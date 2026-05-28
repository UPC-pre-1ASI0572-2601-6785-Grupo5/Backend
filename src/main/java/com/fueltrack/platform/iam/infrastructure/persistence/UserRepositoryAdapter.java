package com.fueltrack.platform.iam.infrastructure.persistence;

import com.fueltrack.platform.iam.domain.model.User;
import com.fueltrack.platform.iam.domain.services.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Infrastructure adapter that bridges the domain repository port and Spring Data JPA.
 */
@Repository
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    /**
     * Creates a new adapter instance.
     *
     * @param jpaUserRepository the Spring Data repository
     */
    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
}