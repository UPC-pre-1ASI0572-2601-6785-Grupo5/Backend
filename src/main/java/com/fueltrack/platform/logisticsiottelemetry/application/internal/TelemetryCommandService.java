package com.fueltrack.platform.logisticsiottelemetry.application.internal;

import com.fueltrack.platform.logisticsiottelemetry.domain.model.TelemetryReading;
import com.fueltrack.platform.logisticsiottelemetry.domain.model.Vehicle;
import com.fueltrack.platform.logisticsiottelemetry.domain.model.VehicleStatus;
import com.fueltrack.platform.logisticsiottelemetry.domain.services.TelemetryReadingRepository;
import com.fueltrack.platform.logisticsiottelemetry.domain.services.VehicleRepository;
import com.fueltrack.platform.logisticsiottelemetry.interfaces.rest.requests.TelemetryReadingRequest;
import com.fueltrack.platform.logisticsiottelemetry.interfaces.rest.responses.TelemetryReadingResponse;
import com.fueltrack.platform.logisticsiottelemetry.interfaces.rest.responses.VehicleResponse;
import jakarta.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Application service for vehicle and telemetry operations.
 */
@Service
public class TelemetryCommandService {

    private final VehicleRepository vehicleRepository;
    private final TelemetryReadingRepository telemetryReadingRepository;

    /**
     * Creates a new telemetry command service.
     *
     * @param vehicleRepository the vehicle repository port
     * @param telemetryReadingRepository the telemetry repository port
     */
    public TelemetryCommandService(VehicleRepository vehicleRepository,
                                   TelemetryReadingRepository telemetryReadingRepository) {
        this.vehicleRepository = vehicleRepository;
        this.telemetryReadingRepository = telemetryReadingRepository;
    }

    /**
     * Ingests a telemetry reading for a vehicle.
     *
     * @param vehicleId the vehicle identifier
     * @param request the telemetry payload
     * @return the stored telemetry response
     */
    @Transactional
    public TelemetryReadingResponse ingestTelemetry(Long vehicleId, TelemetryReadingRequest request) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        TelemetryReading telemetryReading = TelemetryReading.builder()
                .vehicle(vehicle)
                .latitude(request.latitude())
                .longitude(request.longitude())
                .fuelLevel(request.fuelLevel())
                .pressure(request.pressure())
                .timestamp(OffsetDateTime.now())
                .build();

        TelemetryReading savedReading = telemetryReadingRepository.save(telemetryReading);
        vehicle.setStatus(VehicleStatus.IN_ROUTE);
        vehicleRepository.save(vehicle);

        return toResponse(savedReading);
    }

    /**
     * Returns the latest telemetry reading for a vehicle.
     *
     * @param vehicleId the vehicle identifier
     * @return the latest telemetry response
     */
    public TelemetryReadingResponse getLatestTelemetry(Long vehicleId) {
        vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        TelemetryReading reading = telemetryReadingRepository.findTopByVehicle_IdOrderByTimestampDesc(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Telemetry not found"));
        return toResponse(reading);
    }

    /**
     * Returns all vehicles.
     *
     * @return the vehicle list
     */
    public List<VehicleResponse> getVehicles() {
        return vehicleRepository.findAll().stream()
                .map(vehicle -> new VehicleResponse(
                        vehicle.getId(),
                        vehicle.getPlate(),
                        vehicle.getDriverId(),
                        vehicle.getStatus()))
                .toList();
    }

    private TelemetryReadingResponse toResponse(TelemetryReading reading) {
        return new TelemetryReadingResponse(
                reading.getVehicle().getId(),
                reading.getLatitude(),
                reading.getLongitude(),
                reading.getFuelLevel(),
                reading.getPressure(),
                reading.getTimestamp());
    }
}