package com.fueltrack.platform.fleet.infrastructure.persistence.jpa.repositories;

import com.fueltrack.platform.fleet.domain.model.aggregates.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findByProviderId(Long providerId);
}
