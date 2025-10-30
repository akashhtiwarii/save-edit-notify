package com.example.cd_create_edit_save.controller;

import com.example.cd_create_edit_save.constants.AppConstants;
import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.ProductShortCodeOutDTO;
import com.example.cd_create_edit_save.service.ProductShortCodeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Product Short Code operations.
 * 
 * @author Krishna
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping(AppConstants.API_V1_PRODUCT_SHORT_CODES)
@RequiredArgsConstructor
public class ProductShortCodeController {

	private final ProductShortCodeService productShortCodeService;

	/**
	 * Retrieves all product short codes.
	 *
	 * @return ResponseEntity containing list of all product short codes and HTTP
	 *         status 200
	 */
	@GetMapping
	public ResponseEntity<ApiResponseOutDto<List<ProductShortCodeOutDTO>>> getAllProductShortCodes() {
		log.info("REST request to get all ProductShortCodes");

		ApiResponseOutDto<List<ProductShortCodeOutDTO>> response = productShortCodeService.getAllProductShortCodes();
		log.info("Response status: {}, Data size: {}", response.getStatus(), response.getData().size());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
