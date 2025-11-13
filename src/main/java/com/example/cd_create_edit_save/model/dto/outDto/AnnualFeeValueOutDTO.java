package com.example.cd_create_edit_save.model.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for transferring annual fee value data between the
 * API layer and service or persistence layers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnualFeeValueOutDTO {

	/**
	 * Name of the flag associated with this annual fee.
	 */
	private String flagName;

	/**
	 * Annual fee amount in decimal format.
	 */
	private BigDecimal amount;
}
