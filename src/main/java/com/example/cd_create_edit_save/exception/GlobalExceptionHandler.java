package com.example.cd_create_edit_save.exception;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler that provides centralized error handling
 * for all REST controllers in the application.
 *
 * <p>It captures both custom and generic exceptions and returns
 * a consistent {@link ApiResponseOutDto} structure with proper HTTP status codes.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Builds a standardized error response with the given status, message, and optional error data.
     */
    private ResponseEntity<ApiResponseOutDto<Object>> buildErrorResponse(HttpStatus status,
                                                                         String message,
                                                                         Object errorData) {

        ApiResponseOutDto<Object> response = ApiResponseOutDto.<Object>builder()
                .status("FAILURE")
                .message(message)
                .data(errorData)
                .timestamp(Instant.now())
                .build();

        return new ResponseEntity<>(response, status);
    }

    /**
     * Handles invalid request exceptions.
     */
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponseOutDto<Object>> handleInvalidRequest(InvalidRequestException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    /**
     * Handles resource conflict exceptions (e.g., duplicate entries).
     */
    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ApiResponseOutDto<Object>> handleResourceConflict(ResourceConflictException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), null);
    }

    /**
     * Handles resource not found exceptions.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseOutDto<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
    }

    /**
     * Handles all unexpected exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseOutDto<Object>> handleGenericException(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred: " + ex.getMessage(), null);
    }

    /**
     * Handles validation errors for invalid request body fields.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseOutDto<Object>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return buildErrorResponse(HttpStatus.BAD_REQUEST,
                "Validation failed", errors);
    }
}
