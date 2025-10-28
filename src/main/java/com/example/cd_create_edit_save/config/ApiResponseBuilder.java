package com.example.cd_create_edit_save.config;

import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.ResponseDetailsOutDto;
import com.example.cd_create_edit_save.model.dto.ResponseMetaOutDto;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public class ApiResponseBuilder {

    private ApiResponseBuilder() {
        // Prevent instantiation
    }

    public static <T> ApiResponseOutDto<T> success(T data, String message) {
        return ApiResponseOutDto.<T>builder()
                .status("success")
                .response(ResponseDetailsOutDto.builder()
                        .code(String.valueOf(HttpStatus.OK.value()))
                        .message(message != null ? message : "Request processed successfully.")
                        .build())
                .data(data)
                .meta(ResponseMetaOutDto.builder()
                        .timestamp(Instant.now())
                        .build())
                .build();
    }

    public static <T> ApiResponseOutDto<T> success(HttpStatus status, T data, String message) {
        return ApiResponseOutDto.<T>builder()
                .status("success")
                .response(ResponseDetailsOutDto.builder()
                        .code(String.valueOf(status.value()))
                        .message(message != null ? message : status.getReasonPhrase())
                        .build())
                .data(data)
                .meta(ResponseMetaOutDto.builder()
                        .timestamp(Instant.now())
                        .build())
                .build();
    }

    public static <T> ApiResponseOutDto<T> failure(HttpStatus status, String message, T errorData) {
        return ApiResponseOutDto.<T>builder()
                .status("failure")
                .response(ResponseDetailsOutDto.builder()
                        .code(String.valueOf(status.value()))
                        .message(message != null ? message : status.getReasonPhrase())
                        .build())
                .data(errorData)
                .meta(ResponseMetaOutDto.builder()
                        .timestamp(Instant.now())
                        .build())
                .build();
    }
}
