package com.example.cd_create_edit_save.service;

import java.util.List;

import com.example.cd_create_edit_save.model.dto.outDto.AnnualFeeValueOutDTO;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;

/**
 * Service interface for managing Annual Fee Values.
 * 
 * @author Krishna
 * @version 1.0
 */
public interface AnnualFeeValueService {

	/**
	 * Retrieves all annual fee values from the database.
	 * 
	 * @return a list of all annual fee values
	 */
	ApiResponseOutDto<List<AnnualFeeValueOutDTO>> getAllAnnualFeeValues();

}