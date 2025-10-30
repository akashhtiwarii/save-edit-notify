package com.example.cd_create_edit_save.service;

import java.util.List;

import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.FeeTypeShortCodeOutDTO;

/**
 * Service interface for FeeTypeShortCode operations.
 * 
 * @author Krishna
 * @version 1.0
 */
public interface FeeTypeShortCodeService {

	/**
	 * Retrieves all fee type short codes from the database.
	 * 
	 * @return a list of {@link FeeTypeShortCodeOutDTO} containing all fee type
	 *         short codes. Returns an empty list if no records are found.
	 */
	ApiResponseOutDto<List<FeeTypeShortCodeOutDTO>> getAllFeeTypeShortCodes();
}