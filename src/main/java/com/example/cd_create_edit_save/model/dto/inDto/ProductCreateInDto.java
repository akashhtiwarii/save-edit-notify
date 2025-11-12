package com.example.cd_create_edit_save.model.dto.inDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) used for creating a new product.
 *
 * <p>This class defines all input fields required for product creation,
 * along with validation constraints to ensure data integrity.</p>
 *
 * <p>It is typically used in {@code POST} requests to the product API.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateInDto {

    /** Short code representing the product (3 characters). */
    @NotBlank(message = "Product short code is required")
    @Size(max = 3, message = "Product short code must be 3 characters")
    private String productShtCd;

    /** Fee type short code (2 characters). */
    @NotBlank(message = "Fee type short code is required")
    @Size(max = 2, message = "Fee type short code must be 2 characters")
    private String feeTypeShtCd;

    /** Rewards type short code (2 characters). */
    @NotBlank(message = "Rewards type short code is required")
    @Size(max = 2, message = "Rewards type short code must be 2 characters")
    private String rewardsTypeShtCd;

    /** Type of APR (FIXED or VARIABLE). */
    @NotBlank(message = "APR type is required")
    @Pattern(regexp = "^(FIXED|VARIABLE)$", message = "APR type must be either FIXED or VARIABLE")
    private String aprType;

    /** Type of APR value (SPECIFIC or RANGE). */
    @NotBlank(message = "APR value type is required")
    @Pattern(regexp = "^(SPECIFIC|RANGE)$", message = "APR value type must be either SPECIFIC or RANGE")
    private String aprValueType;

    /** Minimum purchase APR value. */
    @NotNull(message = "Purchase APR min is required")
    @DecimalMin(value = "0.00", message = "Purchase APR min must be >= 0")
    @DecimalMax(value = "35.99", message = "Purchase APR min must be <= 35.99")
    private BigDecimal purchaseAprMin;

    /** Maximum purchase APR value. */
    @NotNull(message = "Purchase APR max is required")
    @DecimalMin(value = "0.00", message = "Purchase APR max must be >= 0")
    @DecimalMax(value = "35.99", message = "Purchase APR max must be <= 35.99")
    private BigDecimal purchaseAprMax;

    /** Minimum cash APR value. */
    @NotNull(message = "Cash APR min is required")
    @DecimalMin(value = "0.00", message = "Cash APR min must be >= 0")
    @DecimalMax(value = "40.00", message = "Cash APR min must be <= 40.00")
    private BigDecimal cashAprMin;

    /** Maximum cash APR value. */
    @NotNull(message = "Cash APR max is required")
    @DecimalMin(value = "0.00", message = "Cash APR max must be >= 0")
    @DecimalMax(value = "40.00", message = "Cash APR max must be <= 40.00")
    private BigDecimal cashAprMax;

    /** URL for terms and conditions document (must start with HTTPS). */
    @NotBlank(message = "Terms & Conditions link is required")
    @URL(protocol = "https", message = "Terms & Conditions link must start with https")
    @Size(max = 5000, message = "Terms & Conditions link too long")
    private String termsConditionsLink;

    /** URL for cardholder agreement document (must start with HTTPS). */
    @NotBlank(message = "Cardholder Agreement link is required")
    @URL(protocol = "https", message = "Cardholder Agreement link must start with https")
    @Size(max = 5000, message = "Cardholder Agreement link too long")
    private String cardholderAgreementLink;

    /** URL for product/card image (must start with HTTPS). */
    @NotBlank(message = "Card Image link is required")
    @URL(protocol = "https", message = "Card Image link must start with https")
    @Size(max = 500, message = "Card Image link too long")
    private String cardImageLink;

    /** Minimum allowed credit line. */
    @NotNull(message = "Credit line min is required")
    @Min(value = 0, message = "Credit line min must be >= 0")
    private Integer creditLineMin;

    /** Maximum allowed credit line. */
    @NotNull(message = "Credit line max is required")
    @Min(value = 0, message = "Credit line max must be >= 0")
    private Integer creditLineMax;

    /** Indicates if a security deposit is required (Y or N). */
    @NotBlank(message = "Security deposit indicator is required")
    @Pattern(regexp = "^[YN]$", message = "Security deposit indicator must be Y or N")
    private String securityDepositIndicator;

    /** Minimum required security deposit amount (optional). */
    private Integer securityDepositMin;

    /** Maximum allowed security deposit amount (optional). */
    private Integer securityDepositMax;

    /** Name of the person who must approve the product. */
    @NotBlank(message = "To be approved by is required")
    @Size(max = 255, message = "To be approved by name too long")
    private String toBeApprovedBy;

    /** Approval priority level (NORMAL PRIORITY, HIGH PRIORITY, or LOW PRIORITY). */
    private String approvalPriorityLevel;

    /** Optional comments to the approver. */
    @Size(max = 1000, message = "Comments to approver too long")
    private String commentsToApprover;

    /** Product principal code. */
    @NotBlank(message = "PRIN code is required")
    @Size(max = 50, message = "PRIN code too long")
    private String prin;

    /** CWS product ID. */
    @NotBlank(message = "CWS Product ID is required")
    @Size(max = 50, message = "CWS Product ID too long")
    private String cwsProductId;

    /** Channel code associated with the product. */
    @NotBlank(message = "CHA Code is required")
    @Size(max = 50, message = "CHA Code too long")
    private String chaCode;

    /** Product configuration flags (optional). */
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

    /** Universal product code (UPC). */
    @NotNull(message = "UPC is required")
    @Size(max = 50, message = "UPC code too long")
    private String upc;

    /** Start date of the product’s validity. */
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    /** End date of the product’s validity. */
    @NotNull(message = "End date is required")
    private LocalDateTime endDate;
}
