package com.example.cd_create_edit_save.service;

import java.util.List;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.PrinCodeOutDTO;

/**
 * Service interface for managing Principal Code operations.
 *
 * @author Krishna
 * @version 1.0
 */
public interface PrinCodeService {

	/**
	 * Retrieves all principal codes.
	 *
	 * @return list of all principal codes
	 */
	ApiResponseOutDto<List<PrinCodeOutDTO>> getAllPrinCodes();

}
