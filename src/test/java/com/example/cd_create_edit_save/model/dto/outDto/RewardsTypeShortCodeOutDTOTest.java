package com.example.cd_create_edit_save.model.dto.outDto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RewardsTypeShortCodeOutDTOTest {

	@Test
	void testSettersAndGetters() {
		RewardsTypeShortCodeOutDTO rewardsTypeShortCodeOutDTO = new RewardsTypeShortCodeOutDTO();

		assertNull(rewardsTypeShortCodeOutDTO.getRewardsTypeShortCode());
		String rewardsTypeShortCode = "RT01";
		rewardsTypeShortCodeOutDTO.setRewardsTypeShortCode(rewardsTypeShortCode);
		assertEquals(rewardsTypeShortCode, rewardsTypeShortCodeOutDTO.getRewardsTypeShortCode());

		assertNull(rewardsTypeShortCodeOutDTO.getRewardsType());
		String rewardsType = "Cashback";
		rewardsTypeShortCodeOutDTO.setRewardsType(rewardsType);
		assertEquals(rewardsType, rewardsTypeShortCodeOutDTO.getRewardsType());
	}

	@Test
	void testEqualsAndHashCode() {
		String rewardsTypeShortCode = "RT01";
		String rewardsType = "Cashback";

		RewardsTypeShortCodeOutDTO rewards1 = setUpRewardsTypeShortCodeOutDTO(rewardsTypeShortCode, rewardsType);

		assertEquals(rewards1, rewards1);
		assertEquals(rewards1.hashCode(), rewards1.hashCode());

		assertNotEquals(rewards1, new Object());

		RewardsTypeShortCodeOutDTO rewards2 = setUpRewardsTypeShortCodeOutDTO(rewardsTypeShortCode, rewardsType);
		assertEquals(rewards1, rewards2);
		assertEquals(rewards1.hashCode(), rewards2.hashCode());

		rewards2 = setUpRewardsTypeShortCodeOutDTO("RT02", rewardsType);
		assertNotEquals(rewards1, rewards2);
		assertNotEquals(rewards1.hashCode(), rewards2.hashCode());

		rewards2 = setUpRewardsTypeShortCodeOutDTO(rewardsTypeShortCode, "Points");
		assertNotEquals(rewards1, rewards2);
		assertNotEquals(rewards1.hashCode(), rewards2.hashCode());

		rewards1 = new RewardsTypeShortCodeOutDTO();
		rewards2 = new RewardsTypeShortCodeOutDTO();
		assertEquals(rewards1, rewards2);
		assertEquals(rewards1.hashCode(), rewards2.hashCode());
	}

	@Test
	void testBuilder() {
		String rewardsTypeShortCode = "RT01";
		String rewardsType = "Cashback";

		RewardsTypeShortCodeOutDTO rewardsTypeShortCodeOutDTO = RewardsTypeShortCodeOutDTO.builder()
				.rewardsTypeShortCode(rewardsTypeShortCode).rewardsType(rewardsType).build();

		assertEquals(rewardsTypeShortCode, rewardsTypeShortCodeOutDTO.getRewardsTypeShortCode());
		assertEquals(rewardsType, rewardsTypeShortCodeOutDTO.getRewardsType());
	}

	@Test
	void testAllArgsConstructor() {
		String rewardsTypeShortCode = "RT01";
		String rewardsType = "Cashback";

		RewardsTypeShortCodeOutDTO rewardsTypeShortCodeOutDTO = new RewardsTypeShortCodeOutDTO(rewardsTypeShortCode,
				rewardsType);

		assertEquals(rewardsTypeShortCode, rewardsTypeShortCodeOutDTO.getRewardsTypeShortCode());
		assertEquals(rewardsType, rewardsTypeShortCodeOutDTO.getRewardsType());
	}

	@Test
	void testNoArgsConstructor() {
		RewardsTypeShortCodeOutDTO rewardsTypeShortCodeOutDTO = new RewardsTypeShortCodeOutDTO();

		assertNull(rewardsTypeShortCodeOutDTO.getRewardsTypeShortCode());
		assertNull(rewardsTypeShortCodeOutDTO.getRewardsType());
	}

	private RewardsTypeShortCodeOutDTO setUpRewardsTypeShortCodeOutDTO(String rewardsTypeShortCode,
			String rewardsType) {
		RewardsTypeShortCodeOutDTO rewardsTypeShortCodeOutDTO = new RewardsTypeShortCodeOutDTO();
		rewardsTypeShortCodeOutDTO.setRewardsTypeShortCode(rewardsTypeShortCode);
		rewardsTypeShortCodeOutDTO.setRewardsType(rewardsType);
		return rewardsTypeShortCodeOutDTO;
	}
}