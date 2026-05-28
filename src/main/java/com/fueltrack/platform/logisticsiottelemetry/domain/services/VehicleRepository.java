package com.fueltrack.platform.logisticsiottelemetry.domain.services;

import com.fueltrack.platform.logisticsiottelemetry.domain.model.Vehicle;
import java.util.List;
import java.util.Optional;

/**
 * Repository port for vehicles.
 */
public interface VehicleRepository {

    /**
     * Stores a vehicle.
     *
     * @param vehicle the vehicle to save
     * @return the stored vehicle
     */
    Vehicle save(Vehicle vehicle);

    /**
     * Finds a vehicle by identifier.
     *
     * @param id the vehicle identifier
     * @return the matching vehicle, if any
     */
    Optional<Vehicle> findById(Long id);

    /**
     * Retrieves all vehicles.
     *
     * @return all vehicles
     */
    List<Vehicle> findAll();
}