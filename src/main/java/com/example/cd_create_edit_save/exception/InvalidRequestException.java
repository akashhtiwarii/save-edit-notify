package com.example.cd_create_edit_save.exception;

/**
 * Exception thrown when a request contains invalid or malformed data.
 *
 * <p>This is a custom runtime exception used to indicate that the input
 * provided by the client does not meet the required business or validation rules.</p>
 */
public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code InvalidRequestException} with the specified detail message.
     *
     * @param message the detail message describing the reason for the exception
     */
    public InvalidRequestException(final String message) {
        super(message);
    }
}
