package com.fueltrack.platform.fulfillment.application.internal;

import com.fueltrack.platform.fulfillment.domain.model.DeliveryOrder;
import com.fueltrack.platform.fulfillment.domain.model.DeliveryStatus;
import com.fueltrack.platform.fulfillment.domain.model.Geofence;
import com.fueltrack.platform.fulfillment.domain.model.ValveStatus;
import com.fueltrack.platform.fulfillment.domain.services.DeliveryOrderRepository;
import com.fueltrack.platform.fulfillment.domain.services.LatestVehiclePositionProvider;
import com.fueltrack.platform.fulfillment.domain.services.ValveUnlockPolicy;
import com.fueltrack.platform.fulfillment.interfaces.rest.responses.ValveActionResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Application service for fulfillment actions.
 */
@Service
public class FulfillmentCommandService {

    private final DeliveryOrderRepository deliveryOrderRepository;
    private final LatestVehiclePositionProvider latestVehiclePositionProvider;
    private final ValveUnlockPolicy valveUnlockPolicy;
    private final Geofence geofence;

    /**
     * Creates a new fulfillment command service.
     *
     * @param deliveryOrderRepository the delivery order repository port
     * @param latestVehiclePositionProvider the telemetry position provider
     * @param valveUnlockPolicy the unlock policy
     * @param centerLat the geofence center latitude
     * @param centerLng the geofence center longitude
     * @param radiusMeters the geofence radius in meters
     */
    public FulfillmentCommandService(DeliveryOrderRepository deliveryOrderRepository,
                                     LatestVehiclePositionProvider latestVehiclePositionProvider,
                                     ValveUnlockPolicy valveUnlockPolicy,
                                     @Value("${app.fulfillment.geofence.center-lat}") Double centerLat,
                                     @Value("${app.fulfillment.geofence.center-lng}") Double centerLng,
                                     @Value("${app.fulfillment.geofence.radius-meters}") Double radiusMeters) {
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.latestVehiclePositionProvider = latestVehiclePositionProvider;
        this.valveUnlockPolicy = valveUnlockPolicy;
        this.geofence = new Geofence(centerLat, centerLng, radiusMeters);
    }

    /**
     * Unlocks the valve for the specified order when the vehicle is inside the geofence.
     *
     * @param orderId the delivery order identifier
     * @return the fulfillment action response
     */
    @Transactional
    public ValveActionResponse unlockValve(Long orderId) {
        DeliveryOrder deliveryOrder = findDeliveryOrder(orderId);
        var vehiclePosition = latestVehiclePositionProvider.findLatestPosition(deliveryOrder.getVehicleId())
                .orElseThrow(() -> new IllegalStateException("Latest vehicle telemetry not found"));

        valveUnlockPolicy.assertCanUnlock(geofence, vehiclePosition);

        deliveryOrder.setValveStatus(ValveStatus.UNLOCKED);
        if (deliveryOrder.getDeliveryStatus() == DeliveryStatus.PENDING) {
            deliveryOrder.setDeliveryStatus(DeliveryStatus.IN_PROGRESS);
        }
        deliveryOrderRepository.save(deliveryOrder);

        return new ValveActionResponse(deliveryOrder.getOrderId(), deliveryOrder.getValveStatus(), deliveryOrder.getDeliveryStatus(), "Valve unlocked successfully");
    }

    /**
     * Completes the delivery for the specified order.
     *
     * @param orderId the delivery order identifier
     * @return the fulfillment action response
     */
    @Transactional
    public ValveActionResponse complete(Long orderId) {
        DeliveryOrder deliveryOrder = findDeliveryOrder(orderId);
        deliveryOrder.setValveStatus(ValveStatus.LOCKED);
        deliveryOrder.setDeliveryStatus(DeliveryStatus.COMPLETED);
        deliveryOrderRepository.save(deliveryOrder);

        return new ValveActionResponse(deliveryOrder.getOrderId(), deliveryOrder.getValveStatus(), deliveryOrder.getDeliveryStatus(), "Delivery completed successfully");
    }

    private DeliveryOrder findDeliveryOrder(Long orderId) {
        return deliveryOrderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Delivery order not found"));
    }
}