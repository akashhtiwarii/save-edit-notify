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
import com.example.cd_create_edit_save.model.dto.outDto.ChaCodeOutDTO;
import com.example.cd_create_edit_save.service.ChaCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChaCodeControllerTest {

	@Mock
	private ChaCodeService chaCodeService;

	@InjectMocks
	private ChaCodeController chaCodeController;

	private ObjectMapper objectMapper = new ObjectMapper();
	private MockMvc mockMvc;
	private List<ChaCodeOutDTO> mockChaCodeList;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(chaCodeController).build();

		ChaCodeOutDTO chaCode1 = new ChaCodeOutDTO("CH001", "Channel One Description", "ACTIVE");
		ChaCodeOutDTO chaCode2 = new ChaCodeOutDTO("CH002", "Channel Two Description", "ACTIVE");

		mockChaCodeList = Arrays.asList(chaCode1, chaCode2);
	}

	@Test
	public void testGetAllChaCodes() throws Exception {
		ApiResponseOutDto<List<ChaCodeOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(mockChaCodeList);
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("Channel codes retrieved successfully");

		when(chaCodeService.getAllChaCodes()).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/cha-codes").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(chaCodeService, times(1)).getAllChaCodes();
	}

	@Test
	public void testGetAllChaCodesEmptyList() throws Exception {
		ApiResponseOutDto<List<ChaCodeOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(Collections.emptyList());
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("No channel codes found");

		when(chaCodeService.getAllChaCodes()).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/cha-codes").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(chaCodeService, times(1)).getAllChaCodes();
	}

}