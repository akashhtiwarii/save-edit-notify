package com.example.cd_create_edit_save.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for CWS Product Code output.
 * 
 * @author Krishna
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CwsProdCodeOutDTO {

	/**
	 * The unique product code identifier used in the CWS.
	 */
	private String cwsProdCode;

	/**
	 * The description of the CWS product code.
	 */
	private String description;

	/**
	 * The current status of the CWS product code.
	 */
	private String status;
}