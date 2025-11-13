package com.example.cd_create_edit_save.model.dto.inDto;

import com.example.cd_create_edit_save.enums.DateChangeEnum;
import com.example.cd_create_edit_save.validator.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDateUpdateInDTO {

    @NotBlank(message = "Start date is required.")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "Start date must be in format yyyy-MM-dd"
    )
    private String newStartDate;

    @NotBlank(message = "Start time is required.")
    @Pattern(
            regexp = "^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$",
            message = "Start time must be in format HH:mm:ss"
    )
    private String newStartTime;

    @NotBlank(message = "End date is required.")
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "End date must be in format yyyy-MM-dd"
    )
    private String newEndDate;

    @NotBlank(message = "End time is required.")
    @Pattern(
            regexp = "^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$",
            message = "End time must be in format HH:mm:ss"
    )
    private String newEndTime;

    @NotBlank(message = "Reason for date change is required.")
    @ValidEnum(
            enumClass = DateChangeEnum.class,
            message = "Reason must be one of: CAMPAIGN_SCHEDULE_CHANGE, PRODUCT_LAUNCH_CHANGE, EXECUTIVE_ORDER."
    )
    private String reasonForDateChange;

    @Size(max = 400, message = "Additional notes must be less than 400 characters.")
    private String additionalNotes;

    @NotBlank(message = "Approver is required.")
    private String approver;

}
