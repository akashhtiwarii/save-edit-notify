package com.example.cd_create_edit_save.service;

import java.util.List;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.MonthlyFeeValueOutDTO;

/**
 * Service interface for managing Monthly Fee Values.
 * 
 * @author Krishna
 * @version 1.0
 */
public interface MonthlyFeeValueService {

	/**
	 * Retrieves all monthly fee values from the database.
	 * 
	 * @return a list of all monthly fee values
	 */
	ApiResponseOutDto<List<MonthlyFeeValueOutDTO>> getAllMonthlyFeeValues();
}