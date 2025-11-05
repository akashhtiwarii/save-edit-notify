package com.example.cd_create_edit_save.model.dto;


import lombok.Data;

import com.example.cd_create_edit_save.validator.AprRangeValid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@AprRangeValid
public class ProductRequestInDto {

    @Size(max = 100, message = "Search text must not exceed 100 characters")
    private String text;

    @DecimalMin(value = "0.0", inclusive = true, message = "Minimum APR must be greater than or equal to 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Minimum APR cannot exceed 100")
    private Double min_apr;

    @DecimalMin(value = "0.0", inclusive = true, message = "Maximum APR must be greater than or equal to 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "Maximum APR cannot exceed 100")
    private Double max_apr;

    @Pattern(
            regexp = "ACTIVE|INACTIVE|DISCONTINUED",
            message = "Status must be one of: ACTIVE, INACTIVE, DISCONTINUED"
    )
    private String status;

    @Min(value = 0, message = "Offset must be greater than or equal to 0")
    @Null
    private Long offset;

    @Min(value = 1, message = "Limit must be at least 1")
    @Max(value = 1000, message = "Limit cannot exceed 1000")
    @Null
    private Long limit;
}
