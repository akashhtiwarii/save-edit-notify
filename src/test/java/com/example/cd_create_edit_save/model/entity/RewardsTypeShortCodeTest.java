package com.example.cd_create_edit_save.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class RewardsTypeShortCodeTest {

	@Test
	void testSettersAndGetters() {
		RewardsTypeShortCode rewardsTypeShortCode = new RewardsTypeShortCode();

		assertNull(rewardsTypeShortCode.getRewardsTypeShortCode());
		String shortCode = "RT";
		rewardsTypeShortCode.setRewardsTypeShortCode(shortCode);
		assertEquals(shortCode, rewardsTypeShortCode.getRewardsTypeShortCode());

		assertNull(rewardsTypeShortCode.getRewardsType());
		String rewardsType = "Cashback";
		rewardsTypeShortCode.setRewardsType(rewardsType);
		assertEquals(rewardsType, rewardsTypeShortCode.getRewardsType());

		assertNull(rewardsTypeShortCode.getCreatedBy());
		String createdBy = "creatorUser";
		rewardsTypeShortCode.setCreatedBy(createdBy);
		assertEquals(createdBy, rewardsTypeShortCode.getCreatedBy());

		assertNull(rewardsTypeShortCode.getCreatedDatetime());
		LocalDateTime createdDatetime = LocalDateTime.now();
		rewardsTypeShortCode.setCreatedDatetime(createdDatetime);
		assertEquals(createdDatetime, rewardsTypeShortCode.getCreatedDatetime());

		assertNull(rewardsTypeShortCode.getUpdatedBy());
		String updatedBy = "updaterUser";
		rewardsTypeShortCode.setUpdatedBy(updatedBy);
		assertEquals(updatedBy, rewardsTypeShortCode.getUpdatedBy());

		assertNull(rewardsTypeShortCode.getUpdatedDatetime());
		LocalDateTime updatedDatetime = LocalDateTime.now();
		rewardsTypeShortCode.setUpdatedDatetime(updatedDatetime);
		assertEquals(updatedDatetime, rewardsTypeShortCode.getUpdatedDatetime());
	}

	@Test
	void testEqualsAndHashCode() {
		String shortCode = "RT";
		String rewardsType = "Cashback";
		String createdBy = "creator";
		LocalDateTime createdDatetime = LocalDateTime.of(2023, 5, 1, 10, 30);
		String updatedBy = "updater";
		LocalDateTime updatedDatetime = LocalDateTime.of(2023, 6, 1, 12, 0);

		RewardsTypeShortCode rewardsTypeShortCode1 = setUpRewardsTypeShortCode(shortCode, rewardsType, createdBy,
				createdDatetime, updatedBy, updatedDatetime);

		assertEquals(rewardsTypeShortCode1, rewardsTypeShortCode1);
		assertEquals(rewardsTypeShortCode1.hashCode(), rewardsTypeShortCode1.hashCode());
		assertNotEquals(rewardsTypeShortCode1, new Object());

		RewardsTypeShortCode rewardsTypeShortCode2 = setUpRewardsTypeShortCode(shortCode, rewardsType, createdBy,
				createdDatetime, updatedBy, updatedDatetime);
		assertEquals(rewardsTypeShortCode1, rewardsTypeShortCode2);
		assertEquals(rewardsTypeShortCode1.hashCode(), rewardsTypeShortCode2.hashCode());

		rewardsTypeShortCode2 = setUpRewardsTypeShortCode("RX", rewardsType, createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(rewardsTypeShortCode1, rewardsTypeShortCode2);

		rewardsTypeShortCode2 = setUpRewardsTypeShortCode(shortCode, "Reward Points", createdBy, createdDatetime,
				updatedBy, updatedDatetime);
		assertNotEquals(rewardsTypeShortCode1, rewardsTypeShortCode2);

		rewardsTypeShortCode2 = setUpRewardsTypeShortCode(shortCode, rewardsType, "differentUser", createdDatetime,
				updatedBy, updatedDatetime);
		assertNotEquals(rewardsTypeShortCode1, rewardsTypeShortCode2);

		rewardsTypeShortCode2 = setUpRewardsTypeShortCode(shortCode, rewardsType, createdBy, createdDatetime,
				"otherUpdater", updatedDatetime);
		assertNotEquals(rewardsTypeShortCode1, rewardsTypeShortCode2);

		rewardsTypeShortCode1 = new RewardsTypeShortCode();
		rewardsTypeShortCode2 = new RewardsTypeShortCode();
		assertEquals(rewardsTypeShortCode1, rewardsTypeShortCode2);
		assertEquals(rewardsTypeShortCode1.hashCode(), rewardsTypeShortCode2.hashCode());
	}

	private RewardsTypeShortCode setUpRewardsTypeShortCode(String shortCode, String rewardsType, String createdBy,
			LocalDateTime createdDatetime, String updatedBy, LocalDateTime updatedDatetime) {
		RewardsTypeShortCode codeObj = new RewardsTypeShortCode();
		codeObj.setRewardsTypeShortCode(shortCode);
		codeObj.setRewardsType(rewardsType);
		codeObj.setCreatedBy(createdBy);
		codeObj.setCreatedDatetime(createdDatetime);
		codeObj.setUpdatedBy(updatedBy);
		codeObj.setUpdatedDatetime(updatedDatetime);
		return codeObj;
	}
}
