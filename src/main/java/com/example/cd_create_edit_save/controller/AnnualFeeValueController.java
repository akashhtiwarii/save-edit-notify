package com.example.cd_create_edit_save.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cd_create_edit_save.constants.AppConstants;
import com.example.cd_create_edit_save.model.dto.outDto.AnnualFeeValueOutDTO;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.service.AnnualFeeValueService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller for managing Annual Fee Values.
 * 
 * @author Krishna
 * @version 1.0
 */
@RestController
@RequestMapping(AppConstants.API_ANNUAL_FEE_VALUES)
@RequiredArgsConstructor
@Slf4j
@Validated
public class AnnualFeeValueController {

	private final AnnualFeeValueService annualFeeValueService;

	/**
	 * Retrieves all annual fee values.
	 * 
	 * @return ResponseEntity containing list of all annual fee values.
	 */
	@GetMapping
	public ResponseEntity<ApiResponseOutDto<List<AnnualFeeValueOutDTO>>> getAllAnnualFeeValues() {
		log.info("Fetching all annual fee values");
		ApiResponseOutDto<List<AnnualFeeValueOutDTO>> response = annualFeeValueService.getAllAnnualFeeValues();
		log.info("Successfully retrieved {} annual fee values", response.getData().size());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
