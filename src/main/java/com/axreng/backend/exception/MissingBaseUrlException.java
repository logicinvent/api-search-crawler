package com.axreng.backend.exception;

/**
 * Exception thrown when the BASE_URL environment variable is not set or empty.
 *
 * @author Jean Fernandes
 */
public class MissingBaseUrlException extends RuntimeException {
    public MissingBaseUrlException(String message) {
        super(message);
    }
}
