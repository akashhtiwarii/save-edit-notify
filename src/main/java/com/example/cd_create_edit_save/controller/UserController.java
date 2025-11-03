package com.example.cd_create_edit_save.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cd_create_edit_save.constants.AppConstants;
import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.UserOutDTO;
import com.example.cd_create_edit_save.service.UserService;

import java.util.List;

/**
 * REST controller for managing user-related operations.
 * 
 * @author Krishna
 * @version 1.0
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.API_V1_USERS)
public class UserController {

	private final UserService userService;

	/**
	 * Retrieves all users from the database.
	 *
	 * @return {@link ResponseEntity} containing {@link ApiResponseOutDto} with a
	 *         list of {@link UserOutDTO}.
	 * 
	 */
	@GetMapping
	public ResponseEntity<ApiResponseOutDto<List<UserOutDTO>>> getAllUsers() {
		log.info("Received request to retrieve all users");
		ApiResponseOutDto<List<UserOutDTO>> response = userService.getAllUsers();
		log.info("Successfully retrieved {} users", response.getData().size());
		return ResponseEntity.ok(response);
	}
}
