package com.example.cd_create_edit_save.service;

import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.UserOutDTO;

import java.util.List;

/**
 * Service interface for User-related business operations. Defines the contract
 * for user management functionality.
 * 
 * @author Krishna
 * @version 1.0
 */
public interface UserService {

	/**
	 * Retrieves all users from the database.
	 * 
	 * @return List of {@link UserOutDTO} containing all user records
	 */
	ApiResponseOutDto<List<UserOutDTO>> getAllUsers();

}