package com.fueltrack.platform.orderpayment.domain.services;

/**
 * Port for discharging fuel inventory when an order is dispatched.
 */
public interface InventoryDischargePort {

    /**
     * Deducts the given volume from the specified fuel tank.
     *
     * @param fuelType the fuel type to discharge
     * @param gallons  the volume to deduct
     */
    void discharge(String fuelType, Double gallons);
}
