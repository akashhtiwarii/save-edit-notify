package com.example.cd_create_edit_save.exception;


import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link GlobalExceptionHandler}.
 */
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleInvalidRequestException() {
        InvalidRequestException exception = new InvalidRequestException("Invalid input");
        ResponseEntity<ApiResponseOutDto<Object>> response = exceptionHandler.handleInvalidRequest(exception);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("FAILURE", response.getBody().getStatus());
        assertEquals("Invalid input", response.getBody().getMessage());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void testHandleResourceConflictException() {
        ResourceConflictException exception = new ResourceConflictException("Duplicate entry found");
        ResponseEntity<ApiResponseOutDto<Object>> response = exceptionHandler.handleResourceConflict(exception);

        assertEquals(409, response.getStatusCodeValue());
        assertEquals("FAILURE", response.getBody().getStatus());
        assertEquals("Duplicate entry found", response.getBody().getMessage());
    }

    @Test
    void testHandleResourceNotFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");
        ResponseEntity<ApiResponseOutDto<Object>> response = exceptionHandler.handleResourceNotFound(exception);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("FAILURE", response.getBody().getStatus());
        assertEquals("Resource not found", response.getBody().getMessage());
    }

    @Test
    void testHandleGenericException() {
        Exception exception = new Exception("Something went wrong");
        ResponseEntity<ApiResponseOutDto<Object>> response = exceptionHandler.handleGenericException(exception);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("An unexpected error occurred"));
        assertEquals("FAILURE", response.getBody().getStatus());
    }

    @Test
    void testHandleValidationException() {
        // Mock BindingResult and FieldErrors
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("objectName", "name", "Name is required");
        FieldError fieldError2 = new FieldError("objectName", "price", "Price must be positive");

        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ApiResponseOutDto<Object>> response = exceptionHandler.handleValidationException(exception);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("FAILURE", response.getBody().getStatus());
        assertEquals("Validation failed", response.getBody().getMessage());

        @SuppressWarnings("unchecked")
        Map<String, String> errors = (Map<String, String>) response.getBody().getData();
        assertEquals(2, errors.size());
        assertEquals("Name is required", errors.get("name"));
        assertEquals("Price must be positive", errors.get("price"));
    }
}

