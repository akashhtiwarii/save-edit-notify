package com.example.cd_create_edit_save.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.entity.MonthlyFeeValue;
import com.example.cd_create_edit_save.service.MonthlyFeeValueService;

import java.util.List;

/**
 * REST Controller for managing Monthly Fee Values.
 * 
 * @author Krishna
 * @version 1.0
 */
@RestController
@RequestMapping("/api/v1/monthly-fee-values")
@RequiredArgsConstructor
@Slf4j
@Validated
public class MonthlyFeeValueController {

	private final MonthlyFeeValueService monthlyFeeValueService;

	/**
	 * Retrieves all monthly fee values.
	 * <p>
	 * GET endpoint that returns a list of all monthly fee values in the system.
	 * </p>
	 * 
	 * @return ResponseEntity containing list of all monthly fee values and HTTP 200
	 *         OK status
	 */
	@GetMapping
	public ResponseEntity<ApiResponseOutDto<List<MonthlyFeeValue>>> getAllMonthlyFeeValues() {
		log.info("Received request to get all monthly fee values");

		ApiResponseOutDto<List<MonthlyFeeValue>> response = monthlyFeeValueService.getAllMonthlyFeeValues();
		log.info("Successfully retrieved {} monthly fee values", response.getData().size());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
