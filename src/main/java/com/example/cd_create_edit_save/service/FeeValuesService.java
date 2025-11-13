package com.example.cd_create_edit_save.service;

import java.util.List;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.FeeValueOutDTO;

/**
 * Service interface for managing Monthly Fee Values.
 * 
 * @author Krishna
 * @version 1.0
 */
public interface FeeValuesService {

	/**
	 * Retrieves all fee values filtered by fee type.
	 * 
	 * @param feeType the type of fee (ANNUAL or MONTHLY)
	 * @return a list of fee values matching the specified fee type
	 */
	ApiResponseOutDto<List<FeeValueOutDTO>> getFeeValuesByType(String feeType);
}