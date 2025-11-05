package com.example.cd_create_edit_save.exception;

/**
 * Exception thrown when a requested resource cannot be found.
 *
 * <p>This exception is typically used when an entity or record
 * with the specified identifier does not exist in the system.</p>
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new {@code ResourceNotFoundException} with the specified detail message.
     *
     * @param message the detail message describing the missing resource
     */
    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
