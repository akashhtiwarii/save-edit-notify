package com.example.cd_create_edit_save.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseOutDto<T> {
    private String status;
    private ResponseDetailsOutDto response;
    private T data;
    private ResponseMetaOutDto meta;
}
