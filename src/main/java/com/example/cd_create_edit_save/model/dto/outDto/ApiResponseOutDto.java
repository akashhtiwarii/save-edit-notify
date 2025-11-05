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
    /**
     * To get success or error status or api request.
     */
    private String status;
    /**
     * Message to get description of error or success message.
     */
    private String message;

    /**
     *  Generic data .
     */
    private T data;

    /**
     * Timestamp for the response.
     */
    private Instant timestamp;


    /**
     * To send success status as reponse.
     * @param data
     * @param <T>
     * @return sends success response.
     */
    public static <T> ApiResponseOutDto<T> success(final T data) {
        return ApiResponseOutDto.<T>builder()
                .status("success")
                .message("Data retrieved succesfully.")
                .data(data)
                .timestamp(Instant.now())
                .build();
    }

    /**
     * To send success status as response with custom message.
     * @param data
     * @param msg
     * @param <T>
     * @return success reponse with custom message.
     */
    public static <T> ApiResponseOutDto<T> success(final T data , final String msg) {
        return ApiResponseOutDto.<T>builder()
                .status("success")
                .message(msg)
                .data(data)
                .timestamp(Instant.now())
                .build();
    }

    /**
     * To send error message as response.
     * @param message
     *  @param <T>
     * @return error message
     *
     */
    public static <T> ApiResponseOutDto<T> error(final String message) {
        return ApiResponseOutDto.<T>builder()
                .status("error")
                .message(message)
                .data(null)
                .timestamp(Instant.now())
                .build();
    }
}
