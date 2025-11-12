package com.example.cd_create_edit_save.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.cd_create_edit_save.constants.AppConstants;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.FeeValueOutDTO;
import com.example.cd_create_edit_save.service.FeeValueService;

import java.util.List;

/**
 * REST Controller for managing Monthly Fee Values.
 * 
 * @author Krishna
 * @version 1.0
 */
@RestController
@RequestMapping(AppConstants.API_FEE_VALUES)
@RequiredArgsConstructor
@Slf4j
@Validated
public class FeeValueController {

	private final FeeValueService monthlyFeeValueService;

	/**
	 * Retrieves fee values filtered by type. GET endpoint specifically for
	 * filtering by fee type.
	 * 
	 * @return ResponseEntity containing filtered list of fee values and HTTP 200 OK
	 *         status
	 */
	@GetMapping("/type/{feeType}")
	public ResponseEntity<ApiResponseOutDto<List<FeeValueOutDTO>>> getAllMonthlyFeeValues(
			@PathVariable("feeType") String feeType) {
		log.info("Received request to get all monthly fee values");

		ApiResponseOutDto<List<FeeValueOutDTO>> response = monthlyFeeValueService.getFeeValuesByType(feeType);
		log.info("Successfully retrieved {} monthly fee values", response.getData().size());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
