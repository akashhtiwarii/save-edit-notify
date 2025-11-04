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
import com.example.cd_create_edit_save.model.dto.outDto.FeeTypeShortCodeOutDTO;
import com.example.cd_create_edit_save.service.FeeTypeShortCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FeeTypeShortCodeControllerTest {

	@Mock
	private FeeTypeShortCodeService feeTypeShortCodeService;

	@InjectMocks
	private FeeTypeShortCodeController feeTypeShortCodeController;

	private ObjectMapper objectMapper = new ObjectMapper();
	private MockMvc mockMvc;
	private List<FeeTypeShortCodeOutDTO> mockFeeTypeShortCodeList;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(feeTypeShortCodeController).build();

		FeeTypeShortCodeOutDTO feeTypeShortCode1 = new FeeTypeShortCodeOutDTO();
		feeTypeShortCode1.setFeeTypeShortCode("AD");
		feeTypeShortCode1.setFeeType("Administration Fee");

		FeeTypeShortCodeOutDTO feeTypeShortCode2 = new FeeTypeShortCodeOutDTO();
		feeTypeShortCode2.setFeeTypeShortCode("PR");
		feeTypeShortCode2.setFeeType("Processing Fee");

		FeeTypeShortCodeOutDTO feeTypeShortCode3 = new FeeTypeShortCodeOutDTO();
		feeTypeShortCode3.setFeeTypeShortCode("SV");
		feeTypeShortCode3.setFeeType("Service Fee");

		mockFeeTypeShortCodeList = Arrays.asList(feeTypeShortCode1, feeTypeShortCode2, feeTypeShortCode3);
	}

	@Test
	public void testGetAllFeeTypeShortCodes() throws Exception {
		ApiResponseOutDto<List<FeeTypeShortCodeOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(mockFeeTypeShortCodeList);
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("Fee type short codes retrieved successfully");

		when(feeTypeShortCodeService.getAllFeeTypeShortCodes()).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fee-type-short-codes").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(feeTypeShortCodeService, times(1)).getAllFeeTypeShortCodes();
	}

	@Test
	public void testGetAllFeeTypeShortCodesEmptyList() throws Exception {
		ApiResponseOutDto<List<FeeTypeShortCodeOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(Collections.emptyList());
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("No fee type short codes found");

		when(feeTypeShortCodeService.getAllFeeTypeShortCodes()).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fee-type-short-codes").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(feeTypeShortCodeService, times(1)).getAllFeeTypeShortCodes();
	}

}
