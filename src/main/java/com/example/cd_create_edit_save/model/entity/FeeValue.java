package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

import com.example.cd_create_edit_save.enums.FeeType;

/**
 * Entity class representing Monthly Fee Values in the system.
 * <p>
 * This entity stores information about monthly fees associated with different
 * flags. Each record contains a flag name and its corresponding monthly fee
 * amount.
 * </p>
 * 
 * @author Krishna
 * @version 1.0
 */
@Table(name = "fee_values")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeValue {

	/**
	 * Unique identifier for the monthly fee value record.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Name of the flag associated with this monthly fee.
	 */
	@NotBlank(message = "Flag name is required")
	@Column(name = "flag_name", nullable = false, length = 100)
	private String flagName;

	/**
	 * Monthly fee amount in decimal format. Must be a positive value (>= 0).
	 */
	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.0", inclusive = true, message = "Amount must be greater than or equal to 0")
	@Column(name = "amount", nullable = false, precision = 10, scale = 2)
	private BigDecimal amount;

	/**
	 * Type of fee - either ANNUAL or MONTHLY. Cannot be null.
	 */
	@NotNull(message = "Fee type is required")
	@Enumerated(EnumType.STRING)
	@Column(name = "fee_type", nullable = false, length = 20)
	private FeeType feeType;
}