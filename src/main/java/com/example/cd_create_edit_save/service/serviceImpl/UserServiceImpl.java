package com.example.cd_create_edit_save.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.UserOutDTO;
import com.example.cd_create_edit_save.model.entity.User;
import com.example.cd_create_edit_save.repository.UserRepository;
import com.example.cd_create_edit_save.service.UserService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserService} interface. Provides business logic for
 * user-related operations.
 * 
 * @author Krishna
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	/**
	 * Repository for accessing user data.
	 */
	private final UserRepository userRepository;

	/**
	 * Retrieves all users from the database and converts them to DTOs.
	 * 
	 * @return List of {@link UserOutDTO} containing all user records
	 */
	@Override
	@Transactional(readOnly = true)
	public ApiResponseOutDto<List<UserOutDTO>> getAllUsers() {
		List<User> users = userRepository.findAll();

		List<UserOutDTO> userDTOs = users.stream().map(this::convertToDTO).collect(Collectors.toList());

		return ApiResponseOutDto.<List<UserOutDTO>>builder().status("SUCCESS").message("Users retrieved successfully")
				.data(userDTOs).timestamp(Instant.now()).build();
	}

	/**
	 * Converts a {@link User} entity to {@link UserOutDTO}.
	 * 
	 * @param user The user entity to convert
	 * @return UserOutDTO containing user information
	 */
	private UserOutDTO convertToDTO(User user) {
		return UserOutDTO.builder().userId(user.getUserId()).name(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail()).userName(user.getUsername()).role(user.getRole()).build();
	}
}
