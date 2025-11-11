package com.example.cd_create_edit_save.model.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.example.cd_create_edit_save.enums.FeeType;

/**
 * Data Transfer Object (DTO) for transferring monthly fee value data between
 * the API layer and service or persistence layers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeValueOutDTO {

	/**
	 * Name of the flag associated with this monthly fee.
	 */
	private String flagName;

	/**
	 * Monthly fee amount in decimal format.
	 */
	private BigDecimal amount;

	/**
	 * Type of fee - either ANNUAL or MONTHLY.
	 */
	private FeeType feeType;
}
