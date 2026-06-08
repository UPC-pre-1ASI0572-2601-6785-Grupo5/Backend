package com.fueltrack.platform.inventory.infrastructure.persistence;

import com.fueltrack.platform.inventory.domain.model.FuelStock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for fuel stocks.
 */
public interface JpaFuelStockRepository extends JpaRepository<FuelStock, Long> {

    Optional<FuelStock> findByFuelType(String fuelType);

    boolean existsByFuelType(String fuelType);
}
