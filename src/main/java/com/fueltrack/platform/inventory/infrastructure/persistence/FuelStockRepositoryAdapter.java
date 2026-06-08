package com.fueltrack.platform.inventory.infrastructure.persistence;

import com.fueltrack.platform.inventory.domain.model.FuelStock;
import com.fueltrack.platform.inventory.domain.services.FuelStockRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Infrastructure adapter for the fuel stock repository port.
 */
@Repository
public class FuelStockRepositoryAdapter implements FuelStockRepository {

    private final JpaFuelStockRepository jpaFuelStockRepository;

    public FuelStockRepositoryAdapter(JpaFuelStockRepository jpaFuelStockRepository) {
        this.jpaFuelStockRepository = jpaFuelStockRepository;
    }

    @Override
    public FuelStock save(FuelStock fuelStock) {
        return jpaFuelStockRepository.save(fuelStock);
    }

    @Override
    public Optional<FuelStock> findByFuelType(String fuelType) {
        return jpaFuelStockRepository.findByFuelType(fuelType);
    }

    @Override
    public List<FuelStock> findAll() {
        return jpaFuelStockRepository.findAll();
    }

    @Override
    public boolean existsByFuelType(String fuelType) {
        return jpaFuelStockRepository.existsByFuelType(fuelType);
    }
}
