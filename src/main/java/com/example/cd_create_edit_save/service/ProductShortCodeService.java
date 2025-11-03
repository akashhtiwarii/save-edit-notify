package com.example.cd_create_edit_save.service;

import java.util.List;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductShortCodeOutDTO;

/**
 * Service interface for managing Product Short Code operations.
 * 
 * @author Krishna
 * @version 1.0
 */
public interface ProductShortCodeService {

	/**
	 * Retrieves all product short codes.
	 *
	 * @return list of all product short codes
	 */
	ApiResponseOutDto<List<ProductShortCodeOutDTO>> getAllProductShortCodes();

}