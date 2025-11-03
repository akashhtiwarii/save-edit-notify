package com.example.cd_create_edit_save.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.FeeTypeShortCodeOutDTO;
import com.example.cd_create_edit_save.model.entity.FeeTypeShortCode;
import com.example.cd_create_edit_save.repository.FeeTypeShortCodeRepository;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.util.*;

class FeeTypeShortCodeServiceImplTest {

	@Mock
	private FeeTypeShortCodeRepository feeTypeShortCodeRepository;

	@InjectMocks
	private FeeTypeShortCodeServiceImpl feeTypeShortCodeService;

	public FeeTypeShortCodeServiceImplTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllFeeTypeShortCodes() {

		FeeTypeShortCode feeTypeShortCode1 = new FeeTypeShortCode();
		feeTypeShortCode1.setFeeTypeShortCode("FTS001");
		feeTypeShortCode1.setFeeType("Processing Fee");

		FeeTypeShortCode feeTypeShortCode2 = new FeeTypeShortCode();
		feeTypeShortCode2.setFeeTypeShortCode("FTS002");
		feeTypeShortCode2.setFeeType("Service Fee");

		List<FeeTypeShortCode> feeTypeShortCodes = Arrays.asList(feeTypeShortCode1, feeTypeShortCode2);

		when(feeTypeShortCodeRepository.findAll()).thenReturn(feeTypeShortCodes);

		ApiResponseOutDto<List<FeeTypeShortCodeOutDTO>> result = feeTypeShortCodeService.getAllFeeTypeShortCodes();

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Fee type short codes retrieved successfully", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(2, result.getData().size());

		assertEquals("FTS001", result.getData().get(0).getFeeTypeShortCode());
		assertEquals("Processing Fee", result.getData().get(0).getFeeType());

		assertEquals("FTS002", result.getData().get(1).getFeeTypeShortCode());
		assertEquals("Service Fee", result.getData().get(1).getFeeType());

		assertNotNull(result.getTimestamp());

		verify(feeTypeShortCodeRepository, times(1)).findAll();
	}

	@Test
	void testGetAllFeeTypeShortCodes_EmptyList() {
		List<FeeTypeShortCode> emptyList = Collections.emptyList();
		when(feeTypeShortCodeRepository.findAll()).thenReturn(emptyList);

		ApiResponseOutDto<List<FeeTypeShortCodeOutDTO>> result = feeTypeShortCodeService.getAllFeeTypeShortCodes();

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Fee type short codes retrieved successfully", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(0, result.getData().size());

		verify(feeTypeShortCodeRepository, times(1)).findAll();
	}
}