package com.example.cd_create_edit_save.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cd_create_edit_save.constants.AppConstants;
import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.CwsProdCodeOutDTO;
import com.example.cd_create_edit_save.service.CwsProdCodeService;

import java.util.List;

/**
 * REST Controller for CWS Product Code operations. Provides endpoints for
 * managing and retrieving CWS product codes.
 * 
 * @author Krishna
 * @version 1.0
 */
@RestController
@RequestMapping(AppConstants.API_V1_CWS_PROD)
@RequiredArgsConstructor
@Slf4j
public class CwsProdCodeController {

	private final CwsProdCodeService cwsProdCodeService;

	/**
	 * Retrieves all CWS product codes.
	 * 
	 * @return ResponseEntity containing ApiResponseOutDto with list of all CWS
	 *         product codes and HTTP 200 status
	 */
	@GetMapping
	public ResponseEntity<ApiResponseOutDto<List<CwsProdCodeOutDTO>>> getAllCwsProdCodes() {
		log.info("Received request to fetch all CWS product codes");

		ApiResponseOutDto<List<CwsProdCodeOutDTO>> response = cwsProdCodeService.getAllCwsProdCodes();

		log.info("Successfully retrieved {} CWS product codes", response.getData().size());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}