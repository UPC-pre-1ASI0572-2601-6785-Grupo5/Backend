package com.fueltrack.platform.fulfillment.interfaces.rest.responses;

import com.fueltrack.platform.fulfillment.domain.model.DeliveryStatus;
import com.fueltrack.platform.fulfillment.domain.model.ValveStatus;

/**
 * Response payload for delivery order state.
 *
 * @param orderId the order identifier
 * @param vehicleId the vehicle identifier
 * @param valveStatus the valve status
 * @param deliveryStatus the delivery status
 */
public record DeliveryOrderResponse(Long orderId, Long vehicleId, ValveStatus valveStatus, DeliveryStatus deliveryStatus) {
}