package com.example.cd_create_edit_save.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.UserOutDTO;
import com.example.cd_create_edit_save.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserControllerTest {

	@Mock
	private UserService userService;

	@InjectMocks
	private UserController userController;

	private ObjectMapper objectMapper = new ObjectMapper();
	private MockMvc mockMvc;
	private List<UserOutDTO> mockUserList;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

		UserOutDTO user1 = UserOutDTO.builder().userId(1).name("John Doe").role("ADMIN").build();

		UserOutDTO user2 = UserOutDTO.builder().userId(2).name("Jane Smith").role("USER").build();

		UserOutDTO user3 = UserOutDTO.builder().userId(3).name("Robert Johnson").role("MANAGER").build();

		mockUserList = Arrays.asList(user1, user2, user3);
	}

	@Test
	public void testGetAllUsers() throws Exception {
		ApiResponseOutDto<List<UserOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(mockUserList);
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("Users retrieved successfully");

		when(userService.getAllUsers()).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(userService, times(1)).getAllUsers();
	}

	@Test
	public void testGetAllUsersEmptyList() throws Exception {
		ApiResponseOutDto<List<UserOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(Collections.emptyList());
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("No users found");

		when(userService.getAllUsers()).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(userService, times(1)).getAllUsers();
	}

}