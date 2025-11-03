package com.example.cd_create_edit_save.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.util.*;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.PrinCodeOutDTO;
import com.example.cd_create_edit_save.model.entity.PrinCode;
import com.example.cd_create_edit_save.repository.PrinCodeRepository;

class PrinCodeServiceImplTest {

	@Mock
	private PrinCodeRepository repository;

	@InjectMocks
	private PrinCodeServiceImpl prinCodeService;

	public PrinCodeServiceImplTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllPrinCodes() {
		PrinCode prinCode1 = new PrinCode();
		prinCode1.setPrinCode("PRIN001");
		prinCode1.setDescription("Principal Code 1");
		prinCode1.setStatus("ACTIVE");

		PrinCode prinCode2 = new PrinCode();
		prinCode2.setPrinCode("PRIN002");
		prinCode2.setDescription("Principal Code 2");
		prinCode2.setStatus("INACTIVE");

		List<PrinCode> prinCodes = Arrays.asList(prinCode1, prinCode2);

		when(repository.findAll()).thenReturn(prinCodes);

		ApiResponseOutDto<List<PrinCodeOutDTO>> result = prinCodeService.getAllPrinCodes();

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Principal codes retrieved successfully", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(2, result.getData().size());

		assertEquals("PRIN001", result.getData().get(0).getPrinCode());
		assertEquals("Principal Code 1", result.getData().get(0).getDescription());
		assertEquals("ACTIVE", result.getData().get(0).getStatus());

		assertEquals("PRIN002", result.getData().get(1).getPrinCode());
		assertEquals("Principal Code 2", result.getData().get(1).getDescription());
		assertEquals("INACTIVE", result.getData().get(1).getStatus());

		assertNotNull(result.getTimestamp());

		verify(repository, times(1)).findAll();
	}

	@Test
	void testGetAllPrinCodes_EmptyList() {
		List<PrinCode> emptyList = Collections.emptyList();
		when(repository.findAll()).thenReturn(emptyList);

		ApiResponseOutDto<List<PrinCodeOutDTO>> result = prinCodeService.getAllPrinCodes();

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Principal codes retrieved successfully", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(0, result.getData().size());

		verify(repository, times(1)).findAll();
	}
}