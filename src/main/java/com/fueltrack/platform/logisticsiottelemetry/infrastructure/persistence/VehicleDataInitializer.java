package com.fueltrack.platform.logisticsiottelemetry.infrastructure.persistence;

import com.fueltrack.platform.logisticsiottelemetry.domain.model.Vehicle;
import com.fueltrack.platform.logisticsiottelemetry.domain.model.VehicleStatus;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds initial vehicle data on first startup.
 */
@Component
public class VehicleDataInitializer implements CommandLineRunner {

    private final JpaVehicleRepository vehicleRepository;

    public VehicleDataInitializer(JpaVehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void run(String... args) {
        List<Vehicle> seeds = List.of(
                build("VOLVO-FMX-01", 1L),
                build("SCANIA-P360-04", 2L),
                build("HINO-500-18", 3L)
        );
        seeds.forEach(v -> {
            if (!vehicleRepository.existsByPlate(v.getPlate())) {
                vehicleRepository.save(v);
            }
        });
    }

    private Vehicle build(String plate, Long driverId) {
        return Vehicle.builder()
                .plate(plate)
                .driverId(driverId)
                .status(VehicleStatus.AVAILABLE)
                .build();
    }
}
