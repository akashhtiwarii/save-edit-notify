package com.example.cd_create_edit_save.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ChaCodeOutDTO;
import com.example.cd_create_edit_save.model.entity.ChaCode;
import com.example.cd_create_edit_save.repository.ChaCodeRepository;

import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.util.*;

class ChaCodeServiceImplTest {

	@Mock
	private ChaCodeRepository repository;

	@InjectMocks
	private ChaCodeServiceImpl chaCodeService;

	public ChaCodeServiceImplTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllChaCodes() {
		ChaCode chaCode1 = new ChaCode();
		chaCode1.setChaCode("CH001");
		chaCode1.setDescription("Online Channel");
		chaCode1.setStatus("ACTIVE");

		ChaCode chaCode2 = new ChaCode();
		chaCode2.setChaCode("CH002");
		chaCode2.setDescription("Offline Channel");
		chaCode2.setStatus("INACTIVE");

		List<ChaCode> chaCodes = Arrays.asList(chaCode1, chaCode2);

		when(repository.findAll()).thenReturn(chaCodes);


		ApiResponseOutDto<List<ChaCodeOutDTO>> result = chaCodeService.getAllChaCodes();

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Channel codes retrieved successfully", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(2, result.getData().size());

		assertEquals("CH001", result.getData().get(0).getChaCode());
		assertEquals("Online Channel", result.getData().get(0).getDescription());
		assertEquals("ACTIVE", result.getData().get(0).getStatus());

		assertEquals("CH002", result.getData().get(1).getChaCode());
		assertEquals("Offline Channel", result.getData().get(1).getDescription());
		assertEquals("INACTIVE", result.getData().get(1).getStatus());

		assertNotNull(result.getTimestamp());

		verify(repository, times(1)).findAll();
	}

	@Test
	void testGetAllChaCodes_EmptyList() {
		List<ChaCode> emptyList = Collections.emptyList();
		when(repository.findAll()).thenReturn(emptyList);

		ApiResponseOutDto<List<ChaCodeOutDTO>> result = chaCodeService.getAllChaCodes();

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Channel codes retrieved successfully", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(0, result.getData().size());

		verify(repository, times(1)).findAll();
	}
}
