package com.fueltrack.platform.logisticsiottelemetry.infrastructure.persistence;

import com.fueltrack.platform.logisticsiottelemetry.domain.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for vehicles.
 */
public interface JpaVehicleRepository extends JpaRepository<Vehicle, Long> {
}