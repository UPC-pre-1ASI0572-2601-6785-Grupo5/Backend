package com.fueltrack.platform.interfaces.rest;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Standard error payload returned by the global exception handler.
 *
 * @param timestamp the error timestamp
 * @param status the HTTP status code
 * @param error the HTTP status reason phrase
 * @param message the error message
 * @param path the request path
 * @param validationErrors validation error details, if present
 */
public record ApiErrorResponse(
        OffsetDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<String> validationErrors) {
}