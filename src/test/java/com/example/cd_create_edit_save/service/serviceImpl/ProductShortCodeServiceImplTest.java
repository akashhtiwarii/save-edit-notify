package com.example.cd_create_edit_save.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductShortCodeOutDTO;
import com.example.cd_create_edit_save.model.entity.ProductShortCode;
import com.example.cd_create_edit_save.repository.ProductShortCodeRepository;

import java.util.*;

class ProductShortCodeServiceImplTest {

	@Mock
	private ProductShortCodeRepository repository;

	@InjectMocks
	private ProductShortCodeServiceImpl productShortCodeService;

	public ProductShortCodeServiceImplTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllProductShortCodes() {

		ProductShortCode productShortCode1 = new ProductShortCode();
		productShortCode1.setProductShortCode("PSC001");
		productShortCode1.setProduct("Product A");

		ProductShortCode productShortCode2 = new ProductShortCode();
		productShortCode2.setProductShortCode("PSC002");
		productShortCode2.setProduct("Product B");

		List<ProductShortCode> productShortCodes = Arrays.asList(productShortCode1, productShortCode2);

		when(repository.findAll()).thenReturn(productShortCodes);

		ApiResponseOutDto<List<ProductShortCodeOutDTO>> result = productShortCodeService.getAllProductShortCodes();

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Product short codes retrieved successfully", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(2, result.getData().size());

		assertEquals("PSC001", result.getData().get(0).getProductShortCode());
		assertEquals("Product A", result.getData().get(0).getProduct());

		assertEquals("PSC002", result.getData().get(1).getProductShortCode());
		assertEquals("Product B", result.getData().get(1).getProduct());

		assertNotNull(result.getTimestamp());

		verify(repository, times(1)).findAll();
	}

	@Test
	void testGetAllProductShortCodes_EmptyList() {

		List<ProductShortCode> emptyList = Collections.emptyList();
		when(repository.findAll()).thenReturn(emptyList);

		ApiResponseOutDto<List<ProductShortCodeOutDTO>> result = productShortCodeService.getAllProductShortCodes();

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Product short codes retrieved successfully", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(0, result.getData().size());

		verify(repository, times(1)).findAll();
	}
}