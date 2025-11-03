package com.example.cd_create_edit_save.service;

import java.util.List;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.CwsProdCodeOutDTO;

/**
 * Service interface for CWS Product Code operations.
 * 
 * @author Krishna
 * @version 1.0
 */
public interface CwsProdCodeService {

	/**
	 * Retrieves all CWS product codes from the system.
	 *
	 * @return ApiResponseOutDto containing list of CwsProdCodeOutDTO with status
	 *         and timestamp
	 */
	ApiResponseOutDto<List<CwsProdCodeOutDTO>> getAllCwsProdCodes();
}