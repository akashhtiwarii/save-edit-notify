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
import com.example.cd_create_edit_save.model.dto.outDto.CwsProdCodeOutDTO;
import com.example.cd_create_edit_save.service.CwsProdCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CwsProdCodeControllerTest {

	@Mock
	private CwsProdCodeService cwsProdCodeService;

	@InjectMocks
	private CwsProdCodeController cwsProdCodeController;

	private ObjectMapper objectMapper = new ObjectMapper();
	private MockMvc mockMvc;
	private List<CwsProdCodeOutDTO> mockCwsProdCodeList;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(cwsProdCodeController).build();

		CwsProdCodeOutDTO cwsProdCode1 = new CwsProdCodeOutDTO();
		cwsProdCode1.setCwsProdCode("PROD001");
		cwsProdCode1.setDescription("Product Code One Description");
		cwsProdCode1.setStatus("ACTIVE");

		CwsProdCodeOutDTO cwsProdCode2 = new CwsProdCodeOutDTO();
		cwsProdCode2.setCwsProdCode("PROD002");
		cwsProdCode2.setDescription("Product Code Two Description");
		cwsProdCode2.setStatus("ACTIVE");

		mockCwsProdCodeList = Arrays.asList(cwsProdCode1, cwsProdCode2);
	}

	@Test
	public void testGetAllCwsProdCodes() throws Exception {
		ApiResponseOutDto<List<CwsProdCodeOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(mockCwsProdCodeList);
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("CWS product codes retrieved successfully");

		when(cwsProdCodeService.getAllCwsProdCodes()).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cws-prod-codes")
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(cwsProdCodeService, times(1)).getAllCwsProdCodes();
	}

	@Test
	public void testGetAllCwsProdCodesEmptyList() throws Exception {
		ApiResponseOutDto<List<CwsProdCodeOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(Collections.emptyList());
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("No CWS product codes found");

		when(cwsProdCodeService.getAllCwsProdCodes()).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cws-prod-codes").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(cwsProdCodeService, times(1)).getAllCwsProdCodes();
	}

}
