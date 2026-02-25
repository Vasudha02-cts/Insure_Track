package com.insuretrack.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handle Resource Not Found (Policies, Invoices, Users)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 2. Handle Logic/Validation Errors (e.g., DueDate < IssueDate)
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleBusinessLogicException(RuntimeException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 3. Handle Registration Errors (e.g., Email already exists, Second Admin)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleGeneralRuntimeException(RuntimeException ex) {
        // We can check message contents for specific DB/Logic errors
        if (ex.getMessage().contains("already exists") || ex.getMessage().contains("Admin allowed")) {
            return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
        }
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Helper method to create a consistent JSON error structure
    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }
}