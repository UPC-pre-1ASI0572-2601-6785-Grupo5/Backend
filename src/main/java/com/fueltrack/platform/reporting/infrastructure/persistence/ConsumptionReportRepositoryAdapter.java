package com.fueltrack.platform.reporting.infrastructure.persistence;

import com.fueltrack.platform.reporting.domain.model.ConsumptionReport;
import com.fueltrack.platform.reporting.domain.services.ConsumptionReportRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Infrastructure adapter for the consumption report repository port.
 */
@Repository
public class ConsumptionReportRepositoryAdapter implements ConsumptionReportRepository {

    private final JpaConsumptionReportRepository jpaConsumptionReportRepository;

    /**
     * Creates a new adapter instance.
     *
     * @param jpaConsumptionReportRepository the Spring Data repository
     */
    public ConsumptionReportRepositoryAdapter(JpaConsumptionReportRepository jpaConsumptionReportRepository) {
        this.jpaConsumptionReportRepository = jpaConsumptionReportRepository;
    }

    @Override
    public ConsumptionReport save(ConsumptionReport report) {
        return jpaConsumptionReportRepository.save(report);
    }

    @Override
    public List<ConsumptionReport> findByClientIdOrderByGeneratedAtDesc(Long clientId) {
        return jpaConsumptionReportRepository.findByClientIdOrderByGeneratedAtDesc(clientId);
    }
}