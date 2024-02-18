package com.simplon.labxpert.exception.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to handle the custom exceptions.
 * MethodArgumentNotValidException is used to handle the validation exceptions.
 * DataIntegrityViolationException is used to handle the data integrity exceptions.
 * ConstraintViolationException is used to handle the constraint exceptions.
 * It contains the method handleValidationExceptions, handleDataIntegrityViolation and handleValidationExceptions.
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    // TODO : TO @ayoub ait si ahmad MAKE A MEANING PACKAGE FOR THIS CLASS
    // TODO : TO @ayoub ait si ahmad ADD JAVA DOC

    /**
     * This method is used to handle the validation exceptions.
     * It contains the method handleValidationExceptions.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method is used to handle the data integrity exceptions.
     * It contains the method handleDataIntegrityViolation.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(e.getMessage());
        errors.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method is used to handle the constraint exceptions.
     * It contains the method handleValidationExceptions.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleValidationExceptions(ConstraintViolationException ex) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}