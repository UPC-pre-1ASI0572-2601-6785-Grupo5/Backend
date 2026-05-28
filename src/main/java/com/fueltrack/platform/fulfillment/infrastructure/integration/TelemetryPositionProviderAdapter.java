package com.fueltrack.platform.fulfillment.infrastructure.integration;

import com.fueltrack.platform.fulfillment.domain.model.VehiclePosition;
import com.fueltrack.platform.fulfillment.domain.services.LatestVehiclePositionProvider;
import com.fueltrack.platform.logisticsiottelemetry.domain.services.TelemetryReadingRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Adapter that reads the latest vehicle telemetry from the LogisticsIoTTelemetry bounded context.
 */
@Component
public class TelemetryPositionProviderAdapter implements LatestVehiclePositionProvider {

    private final TelemetryReadingRepository telemetryReadingRepository;

    /**
     * Creates a new telemetry position provider adapter.
     *
     * @param telemetryReadingRepository the telemetry repository port
     */
    public TelemetryPositionProviderAdapter(TelemetryReadingRepository telemetryReadingRepository) {
        this.telemetryReadingRepository = telemetryReadingRepository;
    }

    @Override
    public Optional<VehiclePosition> findLatestPosition(Long vehicleId) {
        return telemetryReadingRepository.findTopByVehicle_IdOrderByTimestampDesc(vehicleId)
                .map(reading -> new VehiclePosition(reading.getLatitude(), reading.getLongitude()));
    }
}