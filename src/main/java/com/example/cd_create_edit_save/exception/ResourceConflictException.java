package com.example.cd_create_edit_save.exception;

/**
 * Exception thrown when a resource conflict occurs,
 * such as attempting to create or update a resource that already exists.
 *
 * <p>This exception is typically used to indicate duplicate records
 * or constraint violations in the application.</p>
 */
public class ResourceConflictException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code ResourceConflictException} with the specified detail message.
     *
     * @param message the detail message describing the conflict
     */
    public ResourceConflictException(final String message) {
        super(message);
    }
}
