package com.fueltrack.platform.logisticsiottelemetry.infrastructure.persistence;

import com.fueltrack.platform.logisticsiottelemetry.domain.model.TelemetryReading;
import com.fueltrack.platform.logisticsiottelemetry.domain.services.TelemetryReadingRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Infrastructure adapter for the telemetry repository port.
 */
@Repository
public class TelemetryReadingRepositoryAdapter implements TelemetryReadingRepository {

    private final JpaTelemetryReadingRepository jpaTelemetryReadingRepository;

    /**
     * Creates a new telemetry repository adapter.
     *
     * @param jpaTelemetryReadingRepository the Spring Data repository
     */
    public TelemetryReadingRepositoryAdapter(JpaTelemetryReadingRepository jpaTelemetryReadingRepository) {
        this.jpaTelemetryReadingRepository = jpaTelemetryReadingRepository;
    }

    @Override
    public TelemetryReading save(TelemetryReading telemetryReading) {
        return jpaTelemetryReadingRepository.save(telemetryReading);
    }

    @Override
    public Optional<TelemetryReading> findTopByVehicle_IdOrderByTimestampDesc(Long vehicleId) {
        return jpaTelemetryReadingRepository.findTopByVehicle_IdOrderByTimestampDesc(vehicleId);
    }

    @Override
    public List<TelemetryReading> findByVehicle_IdOrderByTimestampDesc(Long vehicleId) {
        return jpaTelemetryReadingRepository.findByVehicle_IdOrderByTimestampDesc(vehicleId);
    }
}