package com.example.cd_create_edit_save.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.UserOutDTO;
import com.example.cd_create_edit_save.model.entity.User;
import com.example.cd_create_edit_save.repository.UserRepository;

import java.util.*;

class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	public UserServiceImplTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllUsers() {

		User user1 = new User();
		user1.setUserId(1);
		user1.setFirstName("John");
		user1.setLastName("Doe");
		user1.setRole("ADMIN");

		User user2 = new User();
		user2.setUserId(2);
		user2.setFirstName("Jane");
		user2.setLastName("Smith");
		user2.setRole("USER");

		List<User> users = Arrays.asList(user1, user2);

		when(userRepository.findAll()).thenReturn(users);

		ApiResponseOutDto<List<UserOutDTO>> result = userService.getAllUsers();

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Users retrieved successfully", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(2, result.getData().size());

		assertEquals(1, result.getData().get(0).getUserId());
		assertEquals("John Doe", result.getData().get(0).getName());
		assertEquals("ADMIN", result.getData().get(0).getRole());

		assertEquals(2, result.getData().get(1).getUserId());
		assertEquals("Jane Smith", result.getData().get(1).getName());
		assertEquals("USER", result.getData().get(1).getRole());

		assertNotNull(result.getTimestamp());

		verify(userRepository, times(1)).findAll();
	}

	@Test
	void testGetAllUsers_EmptyList() {

		List<User> emptyList = Collections.emptyList();
		when(userRepository.findAll()).thenReturn(emptyList);

		ApiResponseOutDto<List<UserOutDTO>> result = userService.getAllUsers();

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Users retrieved successfully", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(0, result.getData().size());

		verify(userRepository, times(1)).findAll();
	}
}
