package com.fueltrack.platform.logisticsiottelemetry.infrastructure.persistence;

import com.fueltrack.platform.logisticsiottelemetry.domain.model.Vehicle;
import com.fueltrack.platform.logisticsiottelemetry.domain.services.VehicleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Infrastructure adapter for the vehicle repository port.
 */
@Repository
public class VehicleRepositoryAdapter implements VehicleRepository {

    private final JpaVehicleRepository jpaVehicleRepository;

    /**
     * Creates a new vehicle repository adapter.
     *
     * @param jpaVehicleRepository the Spring Data repository
     */
    public VehicleRepositoryAdapter(JpaVehicleRepository jpaVehicleRepository) {
        this.jpaVehicleRepository = jpaVehicleRepository;
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return jpaVehicleRepository.save(vehicle);
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return jpaVehicleRepository.findById(id);
    }

    @Override
    public List<Vehicle> findAll() {
        return jpaVehicleRepository.findAll();
    }
}