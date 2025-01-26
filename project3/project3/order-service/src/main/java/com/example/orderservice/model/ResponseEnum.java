package com.example.orderservice.model;

public enum ResponseEnum {
    SUCCESS("Operation completed successfully"),
    FAILURE("Operation failed"),
    NOT_FOUND("Resource not found"),
    BAD_REQUEST("Invalid request");

    private final String message;

    ResponseEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
