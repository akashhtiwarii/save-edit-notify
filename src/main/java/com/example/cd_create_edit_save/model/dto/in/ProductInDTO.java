package com.example.cd_create_edit_save.model.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.cd_create_edit_save.enums.APRValueType;
import com.example.cd_create_edit_save.enums.AprType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Product input. Used to receive and validate product
 * information from the client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInDTO {

	/**
	 * The product short code (3 characters).
	 */
	@NotBlank(message = "Product short code is required")
	@Size(min = 3, max = 3, message = "Product short code must be exactly 3 characters")
	private String productShtCd;

	/**
	 * The fee type short code (2 characters).
	 */
	@NotBlank(message = "Fee type short code is required")
	@Size(min = 2, max = 2, message = "Fee type short code must be exactly 2 characters")
	private String feeTypeShtCd;

	/**
	 * The rewards type short code (2 characters).
	 */
	@NotBlank(message = "Rewards type short code is required")
	@Size(min = 2, max = 2, message = "Rewards type short code must be exactly 2 characters")
	private String rewardsTypeShtCd;

	/**
	 * The APR type for the product.
	 */
	@Enumerated(EnumType.STRING)
	private AprType aprType;

	/**
	 * The APR value type for the product.
	 */
	@Enumerated(EnumType.STRING)
	private APRValueType aprValueType;

	/**
	 * The minimum purchase APR percentage. Must be between 0.00 and 999.99.
	 */
	@DecimalMin(value = "0.00", message = "Purchase APR min must be at least 0.00")
	@DecimalMax(value = "999.99", message = "Purchase APR min must not exceed 999.99")
	@Digits(integer = 3, fraction = 2, message = "Purchase APR min must have at most 3 digits before and 2 digits after decimal")
	private BigDecimal purchaseAprMin;

	/**
	 * The maximum purchase APR percentage. Must be between 0.00 and 999.99.
	 */
	@DecimalMin(value = "0.00", message = "Purchase APR max must be at least 0.00")
	@DecimalMax(value = "999.99", message = "Purchase APR max must not exceed 999.99")
	@Digits(integer = 3, fraction = 2, message = "Purchase APR max must have at most 3 digits before and 2 digits after decimal")
	private BigDecimal purchaseAprMax;

	/**
	 * The minimum cash advance APR percentage. Must be between 0.00 and 999.99.
	 */
	@DecimalMin(value = "0.00", message = "Cash APR min must be at least 0.00")
	@DecimalMax(value = "999.99", message = "Cash APR min must not exceed 999.99")
	@Digits(integer = 3, fraction = 2, message = "Cash APR min must have at most 3 digits before and 2 digits after decimal")
	private BigDecimal cashAprMin;

	/**
	 * The maximum cash advance APR percentage. Must be between 0.00 and 999.99.
	 */
	@DecimalMin(value = "0.00", message = "Cash APR max must be at least 0.00")
	@DecimalMax(value = "999.99", message = "Cash APR max must not exceed 999.99")
	@Digits(integer = 3, fraction = 2, message = "Cash APR max must have at most 3 digits before and 2 digits after decimal")
	private BigDecimal cashAprMax;

	/**
	 * The minimum credit line amount. Must be a positive value.
	 */
	@Min(value = 0, message = "Credit line min must be at least 0")
	private Integer creditLineMin;

	/**
	 * The maximum credit line amount. Must be a positive value.
	 */
	@Min(value = 0, message = "Credit line max must be at least 0")
	private Integer creditLineMax;

	/**
	 * Indicator for security deposit requirement (Y/N). Must be a single character.
	 */
	@Size(max = 1, message = "Security deposit indicator must be 1 character")
	@Pattern(regexp = "[YN]", message = "Security deposit indicator must be Y or N")
	private String securityDepositIndicator;

	/**
	 * The minimum deposit amount required. Must be a positive value.
	 */
	@Min(value = 0, message = "Deposit min must be at least 0")
	private Integer depositMin;

	/**
	 * The maximum deposit amount allowed. Must be a positive value.
	 */
	@Min(value = 0, message = "Deposit max must be at least 0")
	private Integer depositMax;

	/**
	 * The terms and conditions of the product. Maximum length: 5000 characters.
	 */
	@Size(max = 5000, message = "Terms and conditions must not exceed 5000 characters")
	private String termsConditions;

	/**
	 * The cardholder agreement details. Maximum length: 5000 characters.
	 */
	@Size(max = 5000, message = "Cardholder agreement must not exceed 5000 characters")
	private String cardholderAgreement;

	/**
	 * The URL or path to the card image. Maximum length: 500 characters.
	 */
	@Size(max = 500, message = "Card image path must not exceed 500 characters")
	private String cardImage;

	/**
	 * The current status of the product. Maximum length: 50 characters.
	 */
	@Size(max = 50, message = "Status must not exceed 50 characters")
	private String status;

	/**
	 * The user who created the product record. Maximum length: 255 characters.
	 */
	@Size(max = 255, message = "Created by must not exceed 255 characters")
	private String createdBy;

	/**
	 * The date and time when the product was created.
	 */
	private LocalDateTime createdDatetime;

	/**
	 * The user who reviewed the product. Maximum length: 255 characters.
	 */
	@Size(max = 255, message = "Reviewed by must not exceed 255 characters")
	private String reviewedBy;

	/**
	 * The date and time when the product was reviewed.
	 */
	private LocalDateTime reviewedDatetime;

	/**
	 * The user who overrode the product settings. Maximum length: 100 characters.
	 */
	@Size(max = 100, message = "Override by must not exceed 100 characters")
	private String overrideBy;

	/**
	 * The date and time when the override occurred.
	 */
	private LocalDateTime overrideDatetime;

	/**
	 * The justification for the override action.
	 */
	private byte[] overrideJustification;

	/**
	 * Comments provided during the review process. Maximum length: 1000 characters.
	 */
	@Size(max = 1000, message = "Review comments must not exceed 1000 characters")
	private String reviewComments;

	/**
	 * The PRIN (Product Identifier Number) value.
	 */
	private String prin;

	/**
	 * The CWS product identifier.
	 */
	private String cwsProductId;

	/**
	 * The CHA (Cardholder Agreement) code. Maximum length: 50 characters.
	 */
	@Size(max = 50, message = "CHA code must not exceed 50 characters")
	private String chaCode;

	/**
	 * Array of boarding indicators for the product.
	 */
	private String[] boardingIndicator;

	/**
	 * The start date when the product becomes active.
	 */
	@NotNull(message = "Start date is required")
	private LocalDateTime startDate;

	/**
	 * The end date when the product becomes inactive.
	 */
	@NotNull(message = "End date is required")
	private LocalDateTime endDate;
}