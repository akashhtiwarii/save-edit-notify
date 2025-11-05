package com.example.cd_create_edit_save.model.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing product details returned in API responses.
 *
 * <p>This class encapsulates all product-related information such as identifiers,
 * APR values, credit limits, links, approval workflow details, and audit metadata.</p>
 *
 * <p>Used primarily as an output object in API responses from product-related operations.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOutDto {

    /** Unique identifier of the product. */
    private String productId;

    /** Short code representing the product. */
    private String productShtCd;

    /** Short code representing the fee type associated with the product. */
    private String feeTypeShtCd;

    /** Short code representing the rewards type associated with the product. */
    private String rewardsTypeShtCd;

    /** Type of Annual Percentage Rate (APR) applied to the product. */
    private String aprType;

    /** Type of APR value (e.g., fixed or variable). */
    private String aprValueType;

    /** Minimum purchase APR value. */
    private BigDecimal purchaseAprMin;

    /** Maximum purchase APR value. */
    private BigDecimal purchaseAprMax;

    /** Minimum cash APR value. */
    private BigDecimal cashAprMin;

    /** Maximum cash APR value. */
    private BigDecimal cashAprMax;

    /** URL to the product's terms and conditions document. */
    private String termsConditionsLink;

    /** URL to the cardholder agreement document. */
    private String cardholderAgreementLink;

    /** URL to the product or card image. */
    private String cardImageLink;

    /** Minimum allowed credit line for the product. */
    private Integer creditLineMin;

    /** Maximum allowed credit line for the product. */
    private Integer creditLineMax;

    /** Indicates if the product requires a security deposit. */
    private String securityDepositIndicator;

    /** Minimum required security deposit amount. */
    private Integer securityDepositMin;

    /** Maximum allowed security deposit amount. */
    private Integer securityDepositMax;

    /** Product principal code. */
    private String prin;

    /** Core web service product identifier. */
    private String cwsProductId;

    /** Channel code associated with the product. */
    private String chaCode;

    /** Indicator showing if the product is onboarded. */
    private String boardingIndicator;

    /** Product's start date or effective date. */
    private LocalDateTime startDate;

    /** Product's end date or expiry date. */
    private LocalDateTime endDate;

    /** Current status of the product (e.g., ACTIVE, INACTIVE, PENDING). */
    private String status;

    /** User who is expected to approve the product. */
    private String toBeApprovedBy;

    /** Priority level assigned for approval. */
    private String approvalPriorityLevel;

    /** Comments or notes added for the approver. */
    private String commentsToApprover;

    /** User who reviewed the product. */
    private String reviewedBy;

    /** Timestamp when the product was reviewed. */
    private LocalDateTime reviewedDatetime;

    /** Review comments or feedback. */
    private String reviewComments;

    /** User who performed an override action. */
    private String overrideBy;

    /** Timestamp when the override was performed. */
    private LocalDateTime overrideDatetime;

    /** Supporting document or justification for the override, stored as binary data. */
    private byte[] overrideJustification;

    /** User who created the product record. */
    private String createdBy;

    /** Timestamp when the product record was created. */
    private LocalDateTime createdDatetime;
}
