package com.fueltrack.platform.logisticsiottelemetry.interfaces.rest;

import com.fueltrack.platform.logisticsiottelemetry.application.internal.TelemetryCommandService;
import com.fueltrack.platform.logisticsiottelemetry.interfaces.rest.requests.TelemetryReadingRequest;
import com.fueltrack.platform.logisticsiottelemetry.interfaces.rest.responses.TelemetryReadingResponse;
import com.fueltrack.platform.logisticsiottelemetry.interfaces.rest.responses.VehicleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for telemetry ingestion and vehicle queries.
 */
@Tag(name = "LogisticsIoTTelemetry", description = "Vehicle telemetry and fleet endpoints")
@RestController
@RequestMapping("/api/v1/vehicles")
public class TelemetryController {

    private final TelemetryCommandService telemetryCommandService;

    /**
     * Creates a new telemetry controller.
     *
     * @param telemetryCommandService the application service
     */
    public TelemetryController(TelemetryCommandService telemetryCommandService) {
        this.telemetryCommandService = telemetryCommandService;
    }

    /**
     * Ingests telemetry for a vehicle.
     *
     * @param id the vehicle identifier
     * @param request the telemetry payload
     * @return the stored telemetry response
     */
    @Operation(summary = "Ingest telemetry for a vehicle")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Telemetry ingested successfully",
                    content = @Content(schema = @Schema(implementation = TelemetryReadingResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid telemetry payload"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PostMapping("/{id}/telemetry")
    @ResponseStatus(HttpStatus.CREATED)
    public TelemetryReadingResponse ingestTelemetry(@PathVariable Long id,
                                                    @Valid @RequestBody TelemetryReadingRequest request) {
        return telemetryCommandService.ingestTelemetry(id, request);
    }

    /**
     * Returns the latest telemetry reading for a vehicle.
     *
     * @param id the vehicle identifier
     * @return the latest telemetry response
     */
    @Operation(summary = "Get the latest telemetry reading for a vehicle")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Latest telemetry found",
                    content = @Content(schema = @Schema(implementation = TelemetryReadingResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vehicle or telemetry not found")
    })
    @GetMapping("/{id}/telemetry/latest")
    public TelemetryReadingResponse getLatestTelemetry(@PathVariable Long id) {
        return telemetryCommandService.getLatestTelemetry(id);
    }

    /**
     * Returns the list of vehicles.
     *
     * @return the fleet view
     */
    @Operation(summary = "List vehicles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicles retrieved successfully",
                    content = @Content(schema = @Schema(implementation = VehicleResponse.class)))
    })
    @GetMapping
    public List<VehicleResponse> getVehicles() {
        return telemetryCommandService.getVehicles();
    }
}