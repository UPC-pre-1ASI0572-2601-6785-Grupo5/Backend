package com.fueltrack.platform.logisticsiottelemetry.infrastructure.persistence;

import com.fueltrack.platform.logisticsiottelemetry.domain.model.TelemetryReading;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for telemetry readings.
 */
public interface JpaTelemetryReadingRepository extends JpaRepository<TelemetryReading, Long> {

    /**
     * Retrieves the latest telemetry reading for a vehicle.
     *
     * @param vehicleId the vehicle identifier
     * @return the latest telemetry reading, if any
     */
    Optional<TelemetryReading> findTopByVehicle_IdOrderByTimestampDesc(Long vehicleId);

    /**
     * Retrieves telemetry readings for a vehicle.
     *
     * @param vehicleId the vehicle identifier
     * @return the telemetry history
     */
    List<TelemetryReading> findByVehicle_IdOrderByTimestampDesc(Long vehicleId);
}