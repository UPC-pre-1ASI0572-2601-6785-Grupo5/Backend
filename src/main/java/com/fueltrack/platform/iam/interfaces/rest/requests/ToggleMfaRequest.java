package com.fueltrack.platform.iam.interfaces.rest.requests;

public record ToggleMfaRequest(
        boolean enableMfa) {
}
