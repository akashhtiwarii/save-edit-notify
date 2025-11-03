package com.example.cd_create_edit_save.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cd_create_edit_save.constants.AppConstants;
import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.ChaCodeOutDTO;
import com.example.cd_create_edit_save.service.ChaCodeService;

import java.util.List;

/**
 * REST Controller for managing Channel Code operations. Provides endpoint for
 * retrieving channel codes.
 *
 * @author Krishna
 * @version 1.0
 */
@RestController
@RequestMapping(AppConstants.API_V1_CHA_CODES)
@RequiredArgsConstructor
@Slf4j
public class ChaCodeController {

	private final ChaCodeService chaCodeService;

	/**
	 * Retrieves all channel codes.
	 *
	 * @return ResponseEntity containing ApiResponseOutDto with list of all channel
	 *         codes and HTTP status 200
	 */
	@GetMapping
	public ResponseEntity<ApiResponseOutDto<List<ChaCodeOutDTO>>> getAllChaCodes() {
		log.info("Fetching all channel codes");
		ApiResponseOutDto<List<ChaCodeOutDTO>> response = chaCodeService.getAllChaCodes();
		log.info("Successfully retrieved {} channel codes", response.getData().size());
		return ResponseEntity.ok(response);
	}
}
