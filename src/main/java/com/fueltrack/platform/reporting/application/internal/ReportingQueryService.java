package com.fueltrack.platform.reporting.application.internal;

import com.fueltrack.platform.reporting.domain.model.ConsumptionReport;
import com.fueltrack.platform.reporting.domain.services.ConsumptionReportRepository;
import com.fueltrack.platform.reporting.interfaces.rest.responses.ConsumptionReportResponse;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Application service for reporting queries.
 */
@Service
public class ReportingQueryService {

    private final ConsumptionReportRepository consumptionReportRepository;

    /**
     * Creates a new reporting query service.
     *
     * @param consumptionReportRepository the report repository port
     */
    public ReportingQueryService(ConsumptionReportRepository consumptionReportRepository) {
        this.consumptionReportRepository = consumptionReportRepository;
    }

    /**
     * Retrieves consumption reports for a client.
     *
     * @param clientId the client identifier
     * @return the report responses
     */
    public List<ConsumptionReportResponse> getConsumptionReports(Long clientId) {
        return consumptionReportRepository.findByClientIdOrderByGeneratedAtDesc(clientId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ConsumptionReportResponse toResponse(ConsumptionReport report) {
        return new ConsumptionReportResponse(
                report.getId(),
                report.getOrderId(),
                report.getClientId(),
                report.getLitersDelivered(),
                report.getBurnRate(),
                report.getGeneratedAt());
    }
}