package com.example.cd_create_edit_save.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for FeeTypeShortCode entity output.
 * 
 * @author Krishna
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeeTypeShortCodeOutDTO {

	/**
	 * The short code representing the fee type. This is a unique identifier with
	 * maximum length of 2 characters.
	 */
	private String feeTypeShortCode;

	/**
	 * The descriptive name of the fee type. Maximum length is 50 characters.
	 */
	private String feeType;
}