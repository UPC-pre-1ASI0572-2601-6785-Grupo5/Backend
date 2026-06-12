package com.fueltrack.platform.logisticsiottelemetry.infrastructure.persistence;

import com.fueltrack.platform.logisticsiottelemetry.domain.model.Vehicle;
import com.fueltrack.platform.logisticsiottelemetry.domain.model.VehicleStatus;
import com.fueltrack.platform.logisticsiottelemetry.domain.services.VehicleRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds initial vehicle data on first startup.
 */
@Component
public class VehicleDataInitializer implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;

    public VehicleDataInitializer(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void run(String... args) {
        Set<String> existingPlates = vehicleRepository.findAll()
                .stream()
                .map(Vehicle::getPlate)
                .collect(Collectors.toSet());

        List<Vehicle> seeds = List.of(
                build("VOLVO-FMX-01", 1L),
                build("SCANIA-P360-04", 2L),
                build("HINO-500-18", 3L)
        );

        seeds.stream()
                .filter(v -> !existingPlates.contains(v.getPlate()))
                .forEach(vehicleRepository::save);
    }

    private Vehicle build(String plate, Long driverId) {
        return Vehicle.builder()
                .plate(plate)
                .driverId(driverId)
                .status(VehicleStatus.AVAILABLE)
                .build();
    }
}
