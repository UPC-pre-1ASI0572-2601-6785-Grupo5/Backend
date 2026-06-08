package com.fueltrack.platform.inventory.infrastructure.integration;

import com.fueltrack.platform.inventory.application.internal.InventoryCommandService;
import com.fueltrack.platform.orderpayment.domain.services.InventoryDischargePort;
import org.springframework.stereotype.Component;

/**
 * Bridges the OrderPayment context to the Inventory context for discharge operations.
 */
@Component
public class InventoryDischargeAdapter implements InventoryDischargePort {

    private final InventoryCommandService inventoryCommandService;

    public InventoryDischargeAdapter(InventoryCommandService inventoryCommandService) {
        this.inventoryCommandService = inventoryCommandService;
    }

    @Override
    public void discharge(String fuelType, Double gallons) {
        inventoryCommandService.discharge(fuelType, gallons);
    }
}
