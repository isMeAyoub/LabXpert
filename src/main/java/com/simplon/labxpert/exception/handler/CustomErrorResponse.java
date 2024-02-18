package com.simplon.labxpert.exception.handler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.nio.file.Path;
import java.time.LocalDateTime;
/**
 * This class is used to create a custom error response.
 * It contains the timestamp, the status and the error message.
 */
@Getter
@Setter
public class CustomErrorResponse {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String error;
}
