package com.fueltrack.platform.logisticsiottelemetry.domain.services;

import com.fueltrack.platform.logisticsiottelemetry.domain.model.TelemetryReading;
import java.util.List;
import java.util.Optional;

/**
 * Repository port for telemetry readings.
 */
public interface TelemetryReadingRepository {

    /**
     * Stores a telemetry reading.
     *
     * @param telemetryReading the telemetry reading to save
     * @return the stored telemetry reading
     */
    TelemetryReading save(TelemetryReading telemetryReading);

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