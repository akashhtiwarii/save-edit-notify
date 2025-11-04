package com.example.cd_create_edit_save.service.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.RewardsTypeShortCodeOutDTO;
import com.example.cd_create_edit_save.model.entity.RewardsTypeShortCode;
import com.example.cd_create_edit_save.repository.RewardsTypeShortCodeRepository;

import java.util.*;

class RewardsTypeShortCodeServiceImplTest {

	@Mock
	private RewardsTypeShortCodeRepository rewardsTypeShortCodeRepository;

	@InjectMocks
	private RewardsTypeShortCodeServiceImpl rewardsTypeShortCodeService;

	public RewardsTypeShortCodeServiceImplTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllRewardsTypeShortCodes() {

		RewardsTypeShortCode rewardsTypeShortCode1 = new RewardsTypeShortCode();
		rewardsTypeShortCode1.setRewardsTypeShortCode("RTS001");
		rewardsTypeShortCode1.setRewardsType("Cashback");

		RewardsTypeShortCode rewardsTypeShortCode2 = new RewardsTypeShortCode();
		rewardsTypeShortCode2.setRewardsTypeShortCode("RTS002");
		rewardsTypeShortCode2.setRewardsType("Points");

		List<RewardsTypeShortCode> rewardsTypeShortCodes = Arrays.asList(rewardsTypeShortCode1, rewardsTypeShortCode2);

		when(rewardsTypeShortCodeRepository.findAll()).thenReturn(rewardsTypeShortCodes);

		ApiResponseOutDto<List<RewardsTypeShortCodeOutDTO>> result = rewardsTypeShortCodeService
				.getAllRewardsTypeShortCodes();

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Rewards type short codes retrieved successfully", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(2, result.getData().size());

		assertEquals("RTS001", result.getData().get(0).getRewardsTypeShortCode());
		assertEquals("Cashback", result.getData().get(0).getRewardsType());

		assertEquals("RTS002", result.getData().get(1).getRewardsTypeShortCode());
		assertEquals("Points", result.getData().get(1).getRewardsType());

		assertNotNull(result.getTimestamp());

		verify(rewardsTypeShortCodeRepository, times(1)).findAll();
	}

	@Test
	void testGetAllRewardsTypeShortCodes_EmptyList() {
		List<RewardsTypeShortCode> emptyList = Collections.emptyList();
		when(rewardsTypeShortCodeRepository.findAll()).thenReturn(emptyList);

		ApiResponseOutDto<List<RewardsTypeShortCodeOutDTO>> result = rewardsTypeShortCodeService
				.getAllRewardsTypeShortCodes();

		assertNotNull(result);
		assertEquals("SUCCESS", result.getStatus());
		assertEquals("Rewards type short codes retrieved successfully", result.getMessage());
		assertNotNull(result.getData());
		assertEquals(0, result.getData().size());

		verify(rewardsTypeShortCodeRepository, times(1)).findAll();
	}
}