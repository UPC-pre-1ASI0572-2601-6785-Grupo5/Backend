package com.fueltrack.platform.reporting.interfaces.rest;

import com.fueltrack.platform.reporting.application.internal.ReportingQueryService;
import com.fueltrack.platform.reporting.interfaces.rest.responses.ConsumptionReportResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for reporting queries.
 */
@Tag(name = "Reporting", description = "Consumption report endpoints")
@RestController
@RequestMapping("/api/v1/reports")
public class ReportingController {

    private final ReportingQueryService reportingQueryService;

    /**
     * Creates a new reporting controller.
     *
     * @param reportingQueryService the application service
     */
    public ReportingController(ReportingQueryService reportingQueryService) {
        this.reportingQueryService = reportingQueryService;
    }

    /**
     * Retrieves consumption reports for the specified client.
     *
     * @param clientId the client identifier
     * @return the matching reports
     */
    @Operation(summary = "Get consumption reports by client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reports retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ConsumptionReportResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid client identifier")
    })
    @GetMapping("/consumption")
    public List<ConsumptionReportResponse> getConsumptionReports(@RequestParam Long clientId) {
        return reportingQueryService.getConsumptionReports(clientId);
    }
}