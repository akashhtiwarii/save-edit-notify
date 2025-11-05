package com.example.cd_create_edit_save.model.dto;


import lombok.*;

import com.example.cd_create_edit_save.validator.AprRangeValid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@AprRangeValid
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
            regexp = "PENDING|APPROVED|ACTIVE|EXPIRED|REJECTED",
            message = "Status must be one of: PENDING, APPROVED, ACTIVE, EXPIRED, REJECTED"
    )
    private String status;

    @PositiveOrZero(message = "Offset must be greater than or equal to 0")
    private Long offset;

    @Positive(message = "Limit must be greater than 0")
    private Long limit;
}
