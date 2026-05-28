package com.fueltrack.platform.reporting.domain.services;

import com.fueltrack.platform.reporting.domain.model.ConsumptionReport;
import java.util.List;

/**
 * Repository port for consumption reports.
 */
public interface ConsumptionReportRepository {

    /**
     * Stores a consumption report.
     *
     * @param report the report to save
     * @return the stored report
     */
    ConsumptionReport save(ConsumptionReport report);

    /**
     * Retrieves all reports for a client.
     *
     * @param clientId the client identifier
     * @return the matching reports
     */
    List<ConsumptionReport> findByClientIdOrderByGeneratedAtDesc(Long clientId);
}