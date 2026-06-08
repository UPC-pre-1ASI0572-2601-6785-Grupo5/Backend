package com.fueltrack.platform.inventory.application.internal;

import com.fueltrack.platform.inventory.domain.model.FuelStock;
import com.fueltrack.platform.inventory.domain.services.FuelStockRepository;
import com.fueltrack.platform.inventory.interfaces.rest.responses.StockResponse;
import jakarta.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Application service for inventory commands and queries.
 */
@Service
public class InventoryCommandService {

    private final FuelStockRepository fuelStockRepository;

    public InventoryCommandService(FuelStockRepository fuelStockRepository) {
        this.fuelStockRepository = fuelStockRepository;
    }

    public List<StockResponse> listStocks() {
        return fuelStockRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public StockResponse refill(String fuelType, Double gallons) {
        FuelStock stock = findStock(fuelType);
        double newLevel = stock.getCurrentGallons() + gallons;
        if (newLevel > stock.getMaxCapacityGallons()) {
            throw new IllegalStateException("Refill exceeds maximum tank capacity of "
                    + stock.getMaxCapacityGallons() + " gallons");
        }
        stock.setCurrentGallons(newLevel);
        stock.setUpdatedAt(OffsetDateTime.now());
        return toResponse(fuelStockRepository.save(stock));
    }

    @Transactional
    public StockResponse discharge(String fuelType, Double gallons) {
        FuelStock stock = findStock(fuelType);
        if (stock.getCurrentGallons() < gallons) {
            throw new IllegalStateException("Insufficient stock: only "
                    + stock.getCurrentGallons() + " gallons available for " + fuelType);
        }
        stock.setCurrentGallons(stock.getCurrentGallons() - gallons);
        stock.setUpdatedAt(OffsetDateTime.now());
        return toResponse(fuelStockRepository.save(stock));
    }

    private FuelStock findStock(String fuelType) {
        return fuelStockRepository.findByFuelType(fuelType)
                .orElseThrow(() -> new IllegalArgumentException("Fuel type not found: " + fuelType));
    }

    private StockResponse toResponse(FuelStock stock) {
        return new StockResponse(
                stock.getId(),
                stock.getFuelType(),
                stock.getCurrentGallons(),
                stock.getMaxCapacityGallons());
    }
}
