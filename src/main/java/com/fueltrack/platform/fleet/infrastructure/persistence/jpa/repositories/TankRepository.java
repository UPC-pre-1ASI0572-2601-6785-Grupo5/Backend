package com.fueltrack.platform.fleet.infrastructure.persistence.jpa.repositories;

import com.fueltrack.platform.fleet.domain.model.aggregates.Tank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TankRepository extends JpaRepository<Tank, Long> {
    List<Tank> findByProviderId(Long providerId);
}
