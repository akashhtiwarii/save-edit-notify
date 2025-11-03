package com.example.cd_create_edit_save.model.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Principal Code. Used to transfer principal code data
 * between layers.
 *
 * @author Krishna
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrinCodeOutDTO {

	/**
	 * The unique principal code identifier.
	 */
	private String prinCode;

	/**
	 * The description associated with the principal code.
	 */
	private String description;

	/**
	 * The current status of the principal code.
	 */
	private String status;
}