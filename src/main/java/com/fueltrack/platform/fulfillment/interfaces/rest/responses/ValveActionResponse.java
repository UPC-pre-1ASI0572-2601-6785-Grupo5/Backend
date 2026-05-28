package com.fueltrack.platform.fulfillment.interfaces.rest.responses;

import com.fueltrack.platform.fulfillment.domain.model.DeliveryStatus;
import com.fueltrack.platform.fulfillment.domain.model.ValveStatus;

/**
 * Response payload for fulfillment actions.
 *
 * @param orderId the order identifier
 * @param valveStatus the current valve status
 * @param deliveryStatus the current delivery status
 * @param message a human-readable outcome message
 */
public record ValveActionResponse(Long orderId, ValveStatus valveStatus, DeliveryStatus deliveryStatus, String message) {
}