package com.example.cd_create_edit_save.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cd_create_edit_save.constants.AppConstants;
import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.PrinCodeOutDTO;
import com.example.cd_create_edit_save.service.PrinCodeService;

import java.util.List;

/**
 * REST Controller for managing Principal Code operations. Provides endpoints
 * for CRUD operations on principal codes.
 *
 * @author Krishna
 * @version 1.0
 */
@RestController
@RequestMapping(AppConstants.API_V1_PRIN_CODES)
@RequiredArgsConstructor
@Slf4j
public class PrinCodeController {

	private final PrinCodeService prinCodeService;

	/**
	 * Retrieves all principal codes.
	 *
	 * @return ResponseEntity containing list of all principal codes and HTTP status
	 *         200
	 */
	@GetMapping
	public ResponseEntity<ApiResponseOutDto<List<PrinCodeOutDTO>>> getAllPrinCodes() {
		log.info("REST request to get all PrinCodes");

		ApiResponseOutDto<List<PrinCodeOutDTO>> response = prinCodeService.getAllPrinCodes();
		log.info("Successfully retrieved {} prin codes", response.getData().size());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
