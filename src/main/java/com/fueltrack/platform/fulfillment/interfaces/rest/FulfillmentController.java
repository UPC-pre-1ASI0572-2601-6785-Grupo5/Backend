package com.fueltrack.platform.fulfillment.interfaces.rest;

import com.fueltrack.platform.fulfillment.application.internal.FulfillmentCommandService;
import com.fueltrack.platform.fulfillment.interfaces.rest.responses.ValveActionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for fulfillment operations.
 */
@Tag(name = "Fulfillment", description = "Valve unlock and delivery completion endpoints")
@RestController
@RequestMapping("/api/v1/fulfillment")
public class FulfillmentController {

    private final FulfillmentCommandService fulfillmentCommandService;

    /**
     * Creates a new fulfillment controller.
     *
     * @param fulfillmentCommandService the application service
     */
    public FulfillmentController(FulfillmentCommandService fulfillmentCommandService) {
        this.fulfillmentCommandService = fulfillmentCommandService;
    }

    /**
     * Unlocks the valve for an order.
     *
     * @param orderId the order identifier
     * @return the action response
     */
    @Operation(summary = "Unlock the delivery valve for an order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Valve unlocked successfully",
                    content = @Content(schema = @Schema(implementation = ValveActionResponse.class))),
            @ApiResponse(responseCode = "400", description = "Valve cannot be unlocked"),
            @ApiResponse(responseCode = "404", description = "Delivery order not found")
    })
    @PostMapping("/{orderId}/unlock-valve")
    public ValveActionResponse unlockValve(@PathVariable Long orderId) {
        return fulfillmentCommandService.unlockValve(orderId);
    }

    /**
     * Completes a delivery order.
     *
     * @param orderId the order identifier
     * @return the action response
     */
    @Operation(summary = "Complete a delivery order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delivery completed successfully",
                    content = @Content(schema = @Schema(implementation = ValveActionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Delivery order not found")
    })
    @PostMapping("/{orderId}/complete")
    public ValveActionResponse complete(@PathVariable Long orderId) {
        return fulfillmentCommandService.complete(orderId);
    }

}