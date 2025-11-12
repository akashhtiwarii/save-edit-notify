package com.example.cd_create_edit_save.controller;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.Instant;
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
import com.example.cd_create_edit_save.model.dto.outDto.FeeValueOutDTO;
import com.example.cd_create_edit_save.service.FeeValueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class FeeValueControllerTest {

	@Mock
	private FeeValueService monthlyFeeValueService;

	@InjectMocks
	private FeeValueController feeValueController;

	private ObjectMapper objectMapper;
	private MockMvc mockMvc;
	private List<FeeValueOutDTO> mockMonthlyFeeValueList;
	private List<FeeValueOutDTO> mockAnnualFeeValueList;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(feeValueController).build();

		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		FeeValueOutDTO feeValue1 = new FeeValueOutDTO();
		feeValue1.setDescription("Monthly Maintenance Fee");
		feeValue1.setFeeValue(new BigDecimal("25.50"));
		feeValue1.setFeeType("MONTHLY");

		FeeValueOutDTO feeValue2 = new FeeValueOutDTO();
		feeValue2.setDescription("Monthly Service Fee");
		feeValue2.setFeeValue(new BigDecimal("50.00"));
		feeValue2.setFeeType("MONTHLY");

		FeeValueOutDTO feeValue3 = new FeeValueOutDTO();
		feeValue3.setDescription("Monthly Processing Fee");
		feeValue3.setFeeValue(new BigDecimal("15.00"));
		feeValue3.setFeeType("MONTHLY");

		mockMonthlyFeeValueList = Arrays.asList(feeValue1, feeValue2, feeValue3);

		FeeValueOutDTO annualFeeValue1 = new FeeValueOutDTO();
		annualFeeValue1.setDescription("Annual Membership Fee");
		annualFeeValue1.setFeeValue(new BigDecimal("300.00"));
		annualFeeValue1.setFeeType("ANNUAL");

		mockAnnualFeeValueList = Arrays.asList(annualFeeValue1);
	}

	@Test
	public void testGetAllMonthlyFeeValues() throws Exception {
		ApiResponseOutDto<List<FeeValueOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(mockMonthlyFeeValueList);
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("Fee vlaue type retrieved successfully.");
		mockResponse.setTimestamp(Instant.now());

		when(monthlyFeeValueService.getFeeValuesByType("MONTHLY")).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/products/fee-values/type/MONTHLY").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(monthlyFeeValueService, times(1)).getFeeValuesByType("MONTHLY");
	}

	@Test
	public void testGetAllAnnualFeeValues() throws Exception {
		ApiResponseOutDto<List<FeeValueOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(mockAnnualFeeValueList);
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("Fee vlaue type retrieved successfully.");
		mockResponse.setTimestamp(Instant.now());

		when(monthlyFeeValueService.getFeeValuesByType("ANNUAL")).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/products/fee-values/type/ANNUAL").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(monthlyFeeValueService, times(1)).getFeeValuesByType("ANNUAL");
	}

	@Test
	public void testGetAllMonthlyFeeValuesEmptyList() throws Exception {
		ApiResponseOutDto<List<FeeValueOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(Collections.emptyList());
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("Fee vlaue type retrieved successfully.");
		mockResponse.setTimestamp(Instant.now());

		when(monthlyFeeValueService.getFeeValuesByType("MONTHLY")).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/products/fee-values/type/MONTHLY").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(monthlyFeeValueService, times(1)).getFeeValuesByType("MONTHLY");
	}
}
