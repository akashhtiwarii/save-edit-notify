package com.example.cd_create_edit_save.service;

import java.util.List;

import com.example.cd_create_edit_save.enums.FeeType;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.FeeValueOutDTO;

/**
 * Service interface for managing Monthly Fee Values.
 * 
 * @author Krishna
 * @version 1.0
 */
public interface FeeValueService {

//	/**
//	 * Retrieves all monthly fee values from the database.
//	 * 
//	 * @return a list of all monthly fee values
//	 */
//	ApiResponseOutDto<List<FeeValueOutDTO>> getAllMonthlyFeeValues();

	/**
	 * Retrieves all fee values filtered by fee type.
	 * 
	 * @param feeType the type of fee (ANNUAL or MONTHLY)
	 * @return a list of fee values matching the specified fee type
	 */
	ApiResponseOutDto<List<FeeValueOutDTO>> getFeeValuesByType(FeeType feeType);
}