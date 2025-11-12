//package com.example.cd_create_edit_save.model.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.DecimalMin;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
///**
// * Entity class representing Fee Values in the system.
// * 
// * @author Krishna
// * @version 1.0
// */
//@Table(name = "TBL_FEE_VALUES", schema = "product")
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class FeeValue {
//
//	/**
//	 * Unique identifier for the fee value record. Auto-generated using database
//	 * identity strategy.
//	 */
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "ID")
//	private Long id;
//
//	/**
//	 * Description of the fee. Cannot be null or blank.
//	 */
//	@NotBlank(message = "Description is required")
//	@Column(name = "DESCRIPTION", nullable = false)
//	private String description;
//
//	/**
//	 * Type of fee - represents the category or classification of the fee. Cannot be
//	 * null or blank.
//	 */
//	@NotBlank(message = "Fee type is required")
//	@Column(name = "FEE_TYPE", nullable = false)
//	private String feeType;
//
//	/**
//	 * Fee amount in decimal format. Must be a positive value (>= 0).
//	 */
//	@NotNull(message = "Fee value is required")
//	@DecimalMin(value = "0.0", inclusive = true, message = "Fee value must be greater than or equal to 0")
//	@Column(name = "FEE_VALUE", nullable = false)
//	private BigDecimal feeValue;
//
//	/**
//	 * Username or identifier of the user who created this record.
//	 */
//	@Column(name = "CREATED_BY")
//	private String createdBy;
//
//	/**
//	 * Timestamp when the record was created. Automatically set on insert.
//	 */
//	@CreationTimestamp
//	@Column(name = "CREATED_DATETIME", nullable = false, updatable = false)
//	private LocalDateTime createdDatetime;
//
//	/**
//	 * Username or identifier of the user who last updated this record.
//	 */
//	@Column(name = "UPDATED_BY")
//	private String updatedBy;
//
//	/**
//	 * Timestamp when the record was last updated. Automatically updated on
//	 * modification.
//	 */
//	@UpdateTimestamp
//	@Column(name = "UPDATED_DATETIME", nullable = false)
//	private LocalDateTime updatedDatetime;
//}