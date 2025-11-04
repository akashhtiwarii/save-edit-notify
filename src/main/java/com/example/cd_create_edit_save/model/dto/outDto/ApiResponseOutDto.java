package com.example.cd_create_edit_save.model.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseOutDto<T> {
    private String status;
    private String message;
    private T data;
    private Instant timestamp;

    public static <T> ApiResponseOutDto<T> success(T data) {
        return ApiResponseOutDto.<T>builder()
                .status("success")
                .message("Request processed successfully")
                .data(data)
                .timestamp(Instant.now())
                .build();
    }

    public static <T> ApiResponseOutDto<T> error(String message) {
        return ApiResponseOutDto.<T>builder()
                .status("error")
                .message(message)
                .data(null)
                .timestamp(Instant.now())
                .build();
    }
}
