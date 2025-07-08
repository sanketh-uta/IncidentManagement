package com.sanbro.AuthenticationService.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionDto {
    private String message;
    private String error;
    private int status;
    private LocalDateTime timestamp;
    private String path;

    public ExceptionDto(String message, String error, int status, String path) {
        this.message = message;
        this.error = error;
        this.status = status;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
