package com.example.cd_create_edit_save.controller;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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
import com.example.cd_create_edit_save.model.dto.outDto.RewardsTypeShortCodeOutDTO;
import com.example.cd_create_edit_save.service.RewardsTypeShortCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RewardsTypeShortCodeControllerTest {

	@Mock
	private RewardsTypeShortCodeService rewardsTypeShortCodeService;

	@InjectMocks
	private RewardsTypeShortCodeController rewardsTypeShortCodeController;

	private ObjectMapper objectMapper = new ObjectMapper();
	private MockMvc mockMvc;
	private List<RewardsTypeShortCodeOutDTO> mockRewardsTypeShortCodeList;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(rewardsTypeShortCodeController).build();

		RewardsTypeShortCodeOutDTO rewardsTypeShortCode1 = new RewardsTypeShortCodeOutDTO();
		rewardsTypeShortCode1.setRewardsTypeShortCode("CB");
		rewardsTypeShortCode1.setRewardsType("Cashback");

		RewardsTypeShortCodeOutDTO rewardsTypeShortCode2 = new RewardsTypeShortCodeOutDTO();
		rewardsTypeShortCode2.setRewardsTypeShortCode("PT");
		rewardsTypeShortCode2.setRewardsType("Points");

		RewardsTypeShortCodeOutDTO rewardsTypeShortCode3 = new RewardsTypeShortCodeOutDTO();
		rewardsTypeShortCode3.setRewardsTypeShortCode("ML");
		rewardsTypeShortCode3.setRewardsType("Miles");

		mockRewardsTypeShortCodeList = Arrays.asList(rewardsTypeShortCode1, rewardsTypeShortCode2,
				rewardsTypeShortCode3);
	}

	@Test
	public void testGetAllRewardsTypeShortCodes() throws Exception {
		ApiResponseOutDto<List<RewardsTypeShortCodeOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(mockRewardsTypeShortCodeList);
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("Rewards type short codes retrieved successfully");

		when(rewardsTypeShortCodeService.getAllRewardsTypeShortCodes()).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/products/rewards-type-short-codes").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(rewardsTypeShortCodeService, times(1)).getAllRewardsTypeShortCodes();
	}

	@Test
	public void testGetAllRewardsTypeShortCodesEmptyList() throws Exception {
		ApiResponseOutDto<List<RewardsTypeShortCodeOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(Collections.emptyList());
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("No rewards type short codes found");

		when(rewardsTypeShortCodeService.getAllRewardsTypeShortCodes()).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/products/rewards-type-short-codes").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(rewardsTypeShortCodeService, times(1)).getAllRewardsTypeShortCodes();
	}

}