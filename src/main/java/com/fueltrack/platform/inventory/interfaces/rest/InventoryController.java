package com.fueltrack.platform.inventory.interfaces.rest;

import com.fueltrack.platform.inventory.application.internal.InventoryCommandService;
import com.fueltrack.platform.inventory.interfaces.rest.requests.DischargeRequest;
import com.fueltrack.platform.inventory.interfaces.rest.requests.RefillRequest;
import com.fueltrack.platform.inventory.interfaces.rest.responses.StockResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for fuel inventory operations.
 */
@Tag(name = "Inventory", description = "Fuel stock management endpoints")
@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryCommandService inventoryCommandService;

    public InventoryController(InventoryCommandService inventoryCommandService) {
        this.inventoryCommandService = inventoryCommandService;
    }

    @Operation(summary = "Get current stock levels for all fuel types")
    @GetMapping("/stocks")
    public List<StockResponse> listStocks() {
        return inventoryCommandService.listStocks();
    }

    @Operation(summary = "Refill a fuel tank (PROVIDER only)")
    @PostMapping("/refill")
    public StockResponse refill(@Valid @RequestBody RefillRequest request) {
        return inventoryCommandService.refill(request.fuelType(), request.gallons());
    }

    @Operation(summary = "Discharge fuel from a tank")
    @PostMapping("/discharge")
    public StockResponse discharge(@Valid @RequestBody DischargeRequest request) {
        return inventoryCommandService.discharge(request.fuelType(), request.gallons());
    }
}
