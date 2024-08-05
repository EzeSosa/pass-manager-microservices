package com.esosa.user_service.exception;

import java.time.LocalDateTime;

public class ExceptionMessage {
    private final String message;
    private final int status;
    private final LocalDateTime timestamp;

    public ExceptionMessage(String message, Integer status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}