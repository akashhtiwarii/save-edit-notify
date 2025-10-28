package com.example.cd_create_edit_save.exception;

public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidRequestException(final String message) {
        super(message);
    }
}
