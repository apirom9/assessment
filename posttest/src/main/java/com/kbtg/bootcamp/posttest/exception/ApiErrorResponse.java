package com.kbtg.bootcamp.posttest.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiErrorResponse {
    private final String error;
    private final int status;
    private final ZonedDateTime timestamp;

    public ApiErrorResponse(String message, int httpStatus, ZonedDateTime dateTime) {
        this.error = message;
        this.status = httpStatus;
        this.timestamp = dateTime;
    }

    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
