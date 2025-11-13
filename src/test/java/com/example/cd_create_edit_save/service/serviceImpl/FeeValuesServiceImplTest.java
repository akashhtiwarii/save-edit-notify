package com.example.cd_create_edit_save.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.cd_create_edit_save.repository.FeeValuesRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.FeeValueOutDTO;
import com.example.cd_create_edit_save.model.entity.FeeValues;

class FeeValuesServiceImplTest {

	@Mock
	private FeeValuesRepository monthlyFeeValueRepository;

	@InjectMocks
	private FeeValuesServiceImpl feeValueService;

	public FeeValuesServiceImplTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetFeeValuesByType() {
		String feeType = "MONTHLY";
		FeeValues feeValue1 = new FeeValues();
		feeValue1.setId(1L);
		feeValue1.setDescription("Monthly Maintenance Fee");
		feeValue1.setFeeType("MONTHLY");
		feeValue1.setFeeValue(new BigDecimal("25.50"));
		feeValue1.setCreatedBy("admin");
		feeValue1.setCreatedDatetime(LocalDateTime.now());
		feeValue1.setUpdatedBy("admin");
		feeValue1.setUpdatedDatetime(LocalDateTime.now());

		FeeValues feeValue2 = new FeeValues();
		feeValue2.setId(2L);
		feeValue2.setDescription("Monthly Service Fee");
		feeValue2.setFeeType("MONTHLY");
		feeValue2.setFeeValue(new BigDecimal("50.00"));
		feeValue2.setCreatedBy("admin");
		feeValue2.setCreatedDatetime(LocalDateTime.now());
		feeValue2.setUpdatedBy("admin");
		feeValue2.setUpdatedDatetime(LocalDateTime.now());

		List<FeeValues> feeValues = Arrays.asList(feeValue1, feeValue2);

		when(monthlyFeeValueRepository.findByFeeType(feeType)).thenReturn(feeValues);

		ApiResponseOutDto<List<FeeValueOutDTO>> result = feeValueService.getFeeValuesByType(feeType);

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Fee value type retrieved successfully.", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(2, result.getData().size());

		assertEquals("Monthly Maintenance Fee", result.getData().get(0).getDescription());
		assertEquals(new BigDecimal("25.50"), result.getData().get(0).getFeeValue());
		assertEquals("MONTHLY", result.getData().get(0).getFeeType());

		assertEquals("Monthly Service Fee", result.getData().get(1).getDescription());
		assertEquals(new BigDecimal("50.00"), result.getData().get(1).getFeeValue());
		assertEquals("MONTHLY", result.getData().get(1).getFeeType());

		assertNotNull(result.getTimestamp());

		verify(monthlyFeeValueRepository, times(1)).findByFeeType(feeType);
	}

	@Test
	void testGetFeeValuesByType_AnnualFeeType() {
		String feeType = "ANNUAL";
		FeeValues feeValue1 = new FeeValues();
		feeValue1.setId(1L);
		feeValue1.setDescription("Annual Membership Fee");
		feeValue1.setFeeType("ANNUAL");
		feeValue1.setFeeValue(new BigDecimal("300.00"));
		feeValue1.setCreatedBy("admin");
		feeValue1.setCreatedDatetime(LocalDateTime.now());
		feeValue1.setUpdatedBy("admin");
		feeValue1.setUpdatedDatetime(LocalDateTime.now());

		List<FeeValues> feeValues = Arrays.asList(feeValue1);

		when(monthlyFeeValueRepository.findByFeeType(feeType)).thenReturn(feeValues);

		ApiResponseOutDto<List<FeeValueOutDTO>> result = feeValueService.getFeeValuesByType(feeType);

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Fee value type retrieved successfully.", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(1, result.getData().size());

		assertEquals("Annual Membership Fee", result.getData().get(0).getDescription());
		assertEquals(new BigDecimal("300.00"), result.getData().get(0).getFeeValue());
		assertEquals("ANNUAL", result.getData().get(0).getFeeType());

		assertNotNull(result.getTimestamp());

		verify(monthlyFeeValueRepository, times(1)).findByFeeType(feeType);
	}

	@Test
	void testGetFeeValuesByType_EmptyList() {
		String feeType = "MONTHLY";
		List<FeeValues> emptyList = Collections.emptyList();
		when(monthlyFeeValueRepository.findByFeeType(feeType)).thenReturn(emptyList);

		ApiResponseOutDto<List<FeeValueOutDTO>> result = feeValueService.getFeeValuesByType(feeType);

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Fee value type retrieved successfully.", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(0, result.getData().size());

		verify(monthlyFeeValueRepository, times(1)).findByFeeType(feeType);
	}
}