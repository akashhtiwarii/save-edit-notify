package com.example.cd_create_edit_save.exception;

import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.ResponseDetailsOutDto;
import com.example.cd_create_edit_save.model.dto.ResponseMetaOutDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ApiResponseOutDto<Object>> buildErrorResponse(HttpStatus status,
                                                                         String code,
                                                                         String message,
                                                                         Object errorData) {

        ApiResponseOutDto<Object> response = ApiResponseOutDto.<Object>builder()
                .status("failure")
                .response(ResponseDetailsOutDto.builder()
                        .code(code)
                        .message(message)
                        .build())
                .data(errorData)
                .meta(ResponseMetaOutDto.builder()
                        .timestamp(Instant.now())
                        .build())
                .build();

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponseOutDto<Object>> handleInvalidRequest(InvalidRequestException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", ex.getMessage(), null);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ApiResponseOutDto<Object>> handleResourceConflict(ResourceConflictException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, "RESOURCE_CONFLICT", ex.getMessage(), null);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseOutDto<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", ex.getMessage(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseOutDto<Object>> handleGenericException(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR",
                "An unexpected error occurred: " + ex.getMessage(), null);
    }
}
