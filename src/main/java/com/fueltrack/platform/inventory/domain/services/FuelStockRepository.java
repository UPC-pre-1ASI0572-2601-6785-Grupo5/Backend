package com.fueltrack.platform.inventory.domain.services;

import com.fueltrack.platform.inventory.domain.model.FuelStock;
import java.util.List;
import java.util.Optional;

/**
 * Repository port for managing fuel stocks.
 */
public interface FuelStockRepository {

    FuelStock save(FuelStock fuelStock);

    Optional<FuelStock> findByFuelType(String fuelType);

    List<FuelStock> findAll();

    boolean existsByFuelType(String fuelType);
}
