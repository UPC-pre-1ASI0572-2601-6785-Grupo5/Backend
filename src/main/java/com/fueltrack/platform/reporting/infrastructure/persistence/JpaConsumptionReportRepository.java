package com.fueltrack.platform.reporting.infrastructure.persistence;

import com.fueltrack.platform.reporting.domain.model.ConsumptionReport;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for consumption reports.
 */
public interface JpaConsumptionReportRepository extends JpaRepository<ConsumptionReport, Long> {

    /**
     * Retrieves all reports for a client ordered by generation time.
     *
     * @param clientId the client identifier
     * @return the matching reports
     */
    List<ConsumptionReport> findByClientIdOrderByGeneratedAtDesc(Long clientId);
}