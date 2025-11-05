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
import com.example.cd_create_edit_save.model.dto.outDto.PrinCodeOutDTO;
import com.example.cd_create_edit_save.service.PrinCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PrinCodeControllerTest {

	@Mock
	private PrinCodeService prinCodeService;

	@InjectMocks
	private PrinCodeController prinCodeController;

	private ObjectMapper objectMapper = new ObjectMapper();
	private MockMvc mockMvc;
	private List<PrinCodeOutDTO> mockPrinCodeList;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(prinCodeController).build();

		PrinCodeOutDTO prinCode1 = new PrinCodeOutDTO();
		prinCode1.setPrinCode("PRIN001");
		prinCode1.setDescription("Principal Code One Description");
		prinCode1.setStatus("ACTIVE");

		PrinCodeOutDTO prinCode2 = new PrinCodeOutDTO();
		prinCode2.setPrinCode("PRIN002");
		prinCode2.setDescription("Principal Code Two Description");
		prinCode2.setStatus("ACTIVE");

		PrinCodeOutDTO prinCode3 = new PrinCodeOutDTO();
		prinCode3.setPrinCode("PRIN003");
		prinCode3.setDescription("Principal Code Three Description");
		prinCode3.setStatus("INACTIVE");

		mockPrinCodeList = Arrays.asList(prinCode1, prinCode2, prinCode3);
	}

	@Test
	public void testGetAllPrinCodes() throws Exception {
		ApiResponseOutDto<List<PrinCodeOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(mockPrinCodeList);
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("Principal codes retrieved successfully");

		when(prinCodeService.getAllPrinCodes()).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/prin-codes").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(prinCodeService, times(1)).getAllPrinCodes();
	}

	@Test
	public void testGetAllPrinCodesEmptyList() throws Exception {
		ApiResponseOutDto<List<PrinCodeOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(Collections.emptyList());
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("No principal codes found");

		when(prinCodeService.getAllPrinCodes()).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/prin-codes").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(prinCodeService, times(1)).getAllPrinCodes();
	}

}
