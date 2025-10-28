package com.example.cd_create_edit_save.exception;

public class ResourceConflictException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceConflictException(final String message) {
        super(message);
    }
}
