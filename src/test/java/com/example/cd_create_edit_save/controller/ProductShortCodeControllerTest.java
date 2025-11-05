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
import com.example.cd_create_edit_save.model.dto.outDto.ProductShortCodeOutDTO;
import com.example.cd_create_edit_save.service.ProductShortCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductShortCodeControllerTest {

	@Mock
	private ProductShortCodeService productShortCodeService;

	@InjectMocks
	private ProductShortCodeController productShortCodeController;

	private ObjectMapper objectMapper = new ObjectMapper();
	private MockMvc mockMvc;
	private List<ProductShortCodeOutDTO> mockProductShortCodeList;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productShortCodeController).build();

		ProductShortCodeOutDTO productShortCode1 = new ProductShortCodeOutDTO();
		productShortCode1.setProductShortCode("PS001");
		productShortCode1.setProduct("Premium Savings Account");

		ProductShortCodeOutDTO productShortCode2 = new ProductShortCodeOutDTO();
		productShortCode2.setProductShortCode("CL002");
		productShortCode2.setProduct("Consumer Loan");

		ProductShortCodeOutDTO productShortCode3 = new ProductShortCodeOutDTO();
		productShortCode3.setProductShortCode("FD003");
		productShortCode3.setProduct("Fixed Deposit");

		mockProductShortCodeList = Arrays.asList(productShortCode1, productShortCode2, productShortCode3);
	}

	@Test
	public void testGetAllProductShortCodes() throws Exception {
		ApiResponseOutDto<List<ProductShortCodeOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(mockProductShortCodeList);
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("Product short codes retrieved successfully");

		when(productShortCodeService.getAllProductShortCodes()).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/product-short-codes")

				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(productShortCodeService, times(1)).getAllProductShortCodes();
	}

	@Test
	public void testGetAllProductShortCodesEmptyList() throws Exception {
		ApiResponseOutDto<List<ProductShortCodeOutDTO>> mockResponse = new ApiResponseOutDto<>();
		mockResponse.setData(Collections.emptyList());
		mockResponse.setStatus("SUCCESS");
		mockResponse.setMessage("No product short codes found");

		when(productShortCodeService.getAllProductShortCodes()).thenReturn(mockResponse);

		String expectedOutputJSON = objectMapper.writeValueAsString(mockResponse);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/product-short-codes").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(expectedOutputJSON))
				.andDo(MockMvcResultHandlers.print());

		verify(productShortCodeService, times(1)).getAllProductShortCodes();
	}

}