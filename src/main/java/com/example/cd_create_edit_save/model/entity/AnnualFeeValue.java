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

/**
 * Entity class representing Annual Fee Values in the system.
 * <p>
 * This entity stores information about annual fees associated with different
 * flags. Each record contains a flag name and its corresponding annual fee
 * amount.
 * </p>
 * 
 * @author Krishna
 * @version 1.0
 */
@Table(name = "annual_fee_values")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnualFeeValue {

	/**
	 * Unique identifier for the annual fee value record.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Name of the flag associated with this annual fee.
	 */
	@NotBlank(message = "Flag name is required")
	@Column(name = "flag_name", nullable = false, length = 100)
	private String flagName;

	/**
	 * Annual fee amount in decimal format.
	 */
	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.0", inclusive = true, message = "Amount must be greater than or equal to 0")
	@Column(name = "amount", nullable = false, precision = 10, scale = 2)
	private BigDecimal amount;

}
