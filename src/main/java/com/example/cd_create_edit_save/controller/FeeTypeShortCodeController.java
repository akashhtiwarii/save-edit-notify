package com.example.cd_create_edit_save.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cd_create_edit_save.constants.AppConstants;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.FeeTypeShortCodeOutDTO;
import com.example.cd_create_edit_save.service.FeeTypeShortCodeService;

import java.util.List;

/**
 * REST Controller for FeeTypeShortCode operations.
 * 
 * @author Krishna
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping(AppConstants.API_V1_FEE_TYPE_SHORT_CODES)
@RequiredArgsConstructor
public class FeeTypeShortCodeController {

	private final FeeTypeShortCodeService feeTypeShortCodeService;

	/**
	 * Retrieves all fee type short codes.
	 * 
	 * 
	 * @return {@link ResponseEntity} containing a list of
	 *         {@link FeeTypeShortCodeOutDTO} with HTTP status 200 (OK).
	 */
	@GetMapping
	public ResponseEntity<ApiResponseOutDto<List<FeeTypeShortCodeOutDTO>>> getAllFeeTypeShortCodes() {
		log.info("Received request to fetch all fee type short codes");

		ApiResponseOutDto<List<FeeTypeShortCodeOutDTO>> response = feeTypeShortCodeService.getAllFeeTypeShortCodes();

		log.info("Successfully retrieved {} fee type short codes", response.getData().size());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}