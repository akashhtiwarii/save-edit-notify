package com.example.cd_create_edit_save.model.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for RewardsTypeShortCode entity output.
 * 
 * @author Krishna
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardsTypeShortCodeOutDTO {

	/**
	 * The short code representing the rewards type.
	 */
	private String rewardsTypeShortCode;

	/**
	 * The descriptive name of the rewards type.
	 */
	private String rewardsType;
}