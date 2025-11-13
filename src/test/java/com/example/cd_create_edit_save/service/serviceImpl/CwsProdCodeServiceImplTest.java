package com.example.cd_create_edit_save.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.util.*;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.CwsProdCodeOutDTO;
import com.example.cd_create_edit_save.model.entity.CwsProdCode;
import com.example.cd_create_edit_save.repository.CwsProdCodeRepository;

class CwsProdCodeServiceImplTest {

	@Mock
	private CwsProdCodeRepository cwsProdCodeRepository;

	@InjectMocks
	private CwsProdCodeServiceImpl cwsProdCodeService;

	public CwsProdCodeServiceImplTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllCwsProdCodes() {

		CwsProdCode cwsProdCode1 = new CwsProdCode();
		cwsProdCode1.setCwsProdCode("CWS001");
		cwsProdCode1.setDescription("Product Code 1");
		cwsProdCode1.setStatus("ACTIVE");

		CwsProdCode cwsProdCode2 = new CwsProdCode();
		cwsProdCode2.setCwsProdCode("CWS002");
		cwsProdCode2.setDescription("Product Code 2");
		cwsProdCode2.setStatus("INACTIVE");

		List<CwsProdCode> cwsProdCodes = Arrays.asList(cwsProdCode1, cwsProdCode2);

		when(cwsProdCodeRepository.findAll()).thenReturn(cwsProdCodes);

		ApiResponseOutDto<List<CwsProdCodeOutDTO>> result = cwsProdCodeService.getAllCwsProdCodes();

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("CWS product codes retrieved successfully", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(2, result.getData().size());

		assertEquals("CWS001", result.getData().get(0).getCwsProdCode());
		assertEquals("Product Code 1", result.getData().get(0).getDescription());
		assertEquals("ACTIVE", result.getData().get(0).getStatus());

		assertEquals("CWS002", result.getData().get(1).getCwsProdCode());
		assertEquals("Product Code 2", result.getData().get(1).getDescription());
		assertEquals("INACTIVE", result.getData().get(1).getStatus());

		assertNotNull(result.getTimestamp());

		verify(cwsProdCodeRepository, times(1)).findAll();
	}

	@Test
	void testGetAllCwsProdCodes_EmptyList() {
		List<CwsProdCode> emptyList = Collections.emptyList();
		when(cwsProdCodeRepository.findAll()).thenReturn(emptyList);

		ApiResponseOutDto<List<CwsProdCodeOutDTO>> result = cwsProdCodeService.getAllCwsProdCodes();

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("CWS product codes retrieved successfully", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(0, result.getData().size());

		verify(cwsProdCodeRepository, times(1)).findAll();
	}
}