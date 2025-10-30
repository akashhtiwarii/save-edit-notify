package com.example.cd_create_edit_save.service;

import java.util.List;

import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.RewardsTypeShortCodeOutDTO;

/**
 * Service interface for RewardsTypeShortCode operations.
 * 
 * @author Krishna
 * @version 1.0
 */
public interface RewardsTypeShortCodeService {

	/**
	 * Retrieves all rewards type short codes from the database.
	 * 
	 * @return a list of {@link RewardsTypeShortCodeOutDTO} containing all rewards
	 *         type short codes.
	 */
	ApiResponseOutDto<List<RewardsTypeShortCodeOutDTO>> getAllRewardsTypeShortCodes();
}