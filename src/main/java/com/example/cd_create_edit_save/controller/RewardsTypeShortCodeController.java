package com.example.cd_create_edit_save.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cd_create_edit_save.constants.AppConstants;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.RewardsTypeShortCodeOutDTO;
import com.example.cd_create_edit_save.service.RewardsTypeShortCodeService;

import java.util.List;

/**
 * REST Controller for RewardsTypeShortCode operations. Provides endpoints for
 * managing and retrieving rewards type short codes.
 * 
 * @author Krishna
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping(AppConstants.API_V1_REWARDS_TYPE_SHORT_CODES)
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RewardsTypeShortCodeController {

	private final RewardsTypeShortCodeService rewardsTypeShortCodeService;

	/**
	 * Retrieves all rewards type short codes.
	 * 
	 * @return {@link ResponseEntity} containing a list of
	 *         {@link RewardsTypeShortCodeOutDTO} with HTTP status 200 (OK).
	 */
	@GetMapping
	public ResponseEntity<ApiResponseOutDto<List<RewardsTypeShortCodeOutDTO>>> getAllRewardsTypeShortCodes() {
		log.info("Received request to fetch all rewards type short codes");

		ApiResponseOutDto<List<RewardsTypeShortCodeOutDTO>> response = rewardsTypeShortCodeService
				.getAllRewardsTypeShortCodes();

		log.info("Response status: {}, Data size: {}", response.getStatus(), response.getData().size());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}