package com.example.cd_create_edit_save.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Channel Code. Used to transfer channel code data
 * between layers.
 *
 * @author Krishna
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChaCodeOutDTO {

	/**
	 * The unique channel code identifier.
	 */
	private String chaCode;

	/**
	 * The description of the channel code.
	 */
	private String description;

	/**
	 * The current status of the channel code.
	 */
	private String status;
}
