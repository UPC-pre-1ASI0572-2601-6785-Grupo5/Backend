package com.fueltrack.platform.reporting.interfaces.rest.responses;

import java.time.OffsetDateTime;

/**
 * Response payload for consumption reports.
 *
 * @param id the report identifier
 * @param orderId the order identifier
 * @param clientId the client identifier
 * @param litersDelivered the delivered liters
 * @param burnRate the burn rate
 * @param generatedAt the generation timestamp
 */
public record ConsumptionReportResponse(
        Long id,
        Long orderId,
        Long clientId,
        Double litersDelivered,
        Double burnRate,
        OffsetDateTime generatedAt) {
}