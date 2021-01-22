package ru.flendger.demo.store.demo.store.exeptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

    public ErrorMessage(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
