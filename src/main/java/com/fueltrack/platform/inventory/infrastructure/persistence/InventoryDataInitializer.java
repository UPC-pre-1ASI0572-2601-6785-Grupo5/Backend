package com.fueltrack.platform.inventory.infrastructure.persistence;

import com.fueltrack.platform.inventory.domain.model.FuelStock;
import com.fueltrack.platform.inventory.domain.services.FuelStockRepository;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds initial fuel stock data on first startup.
 */
@Component
public class InventoryDataInitializer implements CommandLineRunner {

    private final FuelStockRepository fuelStockRepository;

    public InventoryDataInitializer(FuelStockRepository fuelStockRepository) {
        this.fuelStockRepository = fuelStockRepository;
    }

    @Override
    public void run(String... args) {
        List<FuelStock> seeds = List.of(
                build("Diesel B5 S-50", 42000.0, 50000.0),
                build("Gasohol 95 Plus", 28000.0, 50000.0),
                build("Gasohol 98", 12000.0, 50000.0)
        );
        seeds.forEach(stock -> {
            if (!fuelStockRepository.existsByFuelType(stock.getFuelType())) {
                fuelStockRepository.save(stock);
            }
        });
    }

    private FuelStock build(String fuelType, double current, double max) {
        return FuelStock.builder()
                .fuelType(fuelType)
                .currentGallons(current)
                .maxCapacityGallons(max)
                .updatedAt(OffsetDateTime.now())
                .build();
    }
}
