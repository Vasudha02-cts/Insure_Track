package com.insuretrack.exception;

/**
 * Simple runtime exception to represent 404-like cases.
 * Handled by GlobalExceptionHandler for a consistent JSON error response.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) { super(message); }
}