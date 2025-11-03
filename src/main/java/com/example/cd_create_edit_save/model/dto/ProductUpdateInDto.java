package com.example.cd_create_edit_save.model.dto;

import com.example.cd_create_edit_save.validator.ValidSecurityDeposit;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidSecurityDeposit
public class ProductUpdateInDto {

    // NON-EDITABLE FIELDS (for validation only - will throw error if changed)
    @NotBlank(message = "Product short code is required")
    @Size(max = 3, message = "Product short code must be 3 characters")
    private String productShtCd;

    @NotBlank(message = "Fee type short code is required")
    @Size(max = 2, message = "Fee type short code must be 2 characters")
    private String feeTypeShtCd;

    @NotBlank(message = "Rewards type short code is required")
    @Size(max = 2, message = "Rewards type short code must be 2 characters")
    private String rewardsTypeShtCd;

    // EDITABLE FIELDS
    @NotBlank(message = "APR type is required")
    @Pattern(regexp = "^(FIXED|VARIABLE)$", message = "APR type must be either FIXED or VARIABLE")
    private String aprType;

    @NotBlank(message = "APR value type is required")
    @Pattern(regexp = "^(SPECIFIC|RANGE)$", message = "APR value type must be either SPECIFIC or RANGE")
    private String aprValueType;

    @NotNull(message = "Purchase APR min is required")
    @DecimalMin(value = "0.00", message = "Purchase APR min must be >= 0")
    @DecimalMax(value = "35.99", message = "Purchase APR min must be <= 35.99")
    private BigDecimal purchaseAprMin;

    @NotNull(message = "Purchase APR max is required")
    @DecimalMin(value = "0.00", message = "Purchase APR max must be >= 0")
    @DecimalMax(value = "35.99", message = "Purchase APR max must be <= 35.99")
    private BigDecimal purchaseAprMax;

    @NotNull(message = "Cash APR min is required")
    @DecimalMin(value = "0.00", message = "Cash APR min must be >= 0")
    @DecimalMax(value = "40.00", message = "Cash APR min must be <= 40.00")
    private BigDecimal cashAprMin;

    @NotNull(message = "Cash APR max is required")
    @DecimalMin(value = "0.00", message = "Cash APR max must be >= 0")
    @DecimalMax(value = "40.00", message = "Cash APR max must be <= 40.00")
    private BigDecimal cashAprMax;

    @NotBlank(message = "Terms & Conditions link is required")
    @URL(protocol = "https", message = "Terms & Conditions link must start with https")
    @Size(max = 5000, message = "Terms & Conditions link too long")
    private String termsConditionsLink;

    @NotBlank(message = "Cardholder Agreement link is required")
    @URL(protocol = "https", message = "Cardholder Agreement link must start with https")
    @Size(max = 5000, message = "Cardholder Agreement link too long")
    private String cardholderAgreementLink;

    @NotBlank(message = "Card Image link is required")
    @URL(protocol = "https", message = "Card Image link must start with https")
    @Size(max = 500, message = "Card Image link too long")
    private String cardImageLink;

    @NotNull(message = "Credit line min is required")
    @Min(value = 0, message = "Credit line min must be >= 0")
    private Integer creditLineMin;

    @NotNull(message = "Credit line max is required")
    @Min(value = 0, message = "Credit line max must be >= 0")
    private Integer creditLineMax;

    @NotBlank(message = "Security deposit indicator is required")
    @Pattern(regexp = "^[YN]$", message = "Security deposit indicator must be Y or N")
    private String securityDepositIndicator;

    private Integer securityDepositMin;
    private Integer securityDepositMax;

    @NotBlank(message = "PRIN code is required")
    @Size(max = 50, message = "PRIN code too long")
    private String prin;

    @NotBlank(message = "CWS Product ID is required")
    @Size(max = 50, message = "CWS Product ID too long")
    private String cwsProductId;

    @NotBlank(message = "CHA Code is required")
    @Size(max = 50, message = "CHA Code too long")
    private String chaCode;

    private Boolean pcFlag1;
    private Boolean pcFlag2;
    private Boolean pcFlag3;
    private Boolean pcFlag4;
    private Boolean pcFlag5;
    private Boolean pcFlag6;
    private Boolean pcFlag7;
    private Boolean pcFlag8;
    private Boolean pcFlag9;
    private Boolean pcFlag10;
    private Boolean upc;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;

    @NotBlank(message = "Reviewer name is required")
    @Size(max = 255, message = "Reviewer name too long")
    private String reviewedBy;

    @Size(max = 1000, message = "Review comments too long")
    private String reviewComments;
}