package com.example.cd_create_edit_save.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
	}

	@Test
	void testEqualsAndHashCode() {
		String shortCode = "RT";
		String rewardsType = "Cashback";

		RewardsTypeShortCode rewardsTypeShortCode1 = setUpRewardsTypeShortCode(shortCode, rewardsType);

		assertEquals(rewardsTypeShortCode1, rewardsTypeShortCode1);
		assertEquals(rewardsTypeShortCode1.hashCode(), rewardsTypeShortCode1.hashCode());
		assertNotEquals(rewardsTypeShortCode1, new Object());

		RewardsTypeShortCode rewardsTypeShortCode2 = setUpRewardsTypeShortCode(shortCode, rewardsType);
		assertEquals(rewardsTypeShortCode1, rewardsTypeShortCode2);
		assertEquals(rewardsTypeShortCode1.hashCode(), rewardsTypeShortCode2.hashCode());

		rewardsTypeShortCode2 = setUpRewardsTypeShortCode("RX", rewardsType);
		assertNotEquals(rewardsTypeShortCode1, rewardsTypeShortCode2);
		assertNotEquals(rewardsTypeShortCode1.hashCode(), rewardsTypeShortCode2.hashCode());

		rewardsTypeShortCode2 = setUpRewardsTypeShortCode(shortCode, "Reward Points");
		assertNotEquals(rewardsTypeShortCode1, rewardsTypeShortCode2);
		assertNotEquals(rewardsTypeShortCode1.hashCode(), rewardsTypeShortCode2.hashCode());

		rewardsTypeShortCode1 = new RewardsTypeShortCode();
		rewardsTypeShortCode2 = new RewardsTypeShortCode();
		assertEquals(rewardsTypeShortCode1, rewardsTypeShortCode2);
		assertEquals(rewardsTypeShortCode1.hashCode(), rewardsTypeShortCode2.hashCode());
	}

	private RewardsTypeShortCode setUpRewardsTypeShortCode(String shortCode, String rewardsType) {
		RewardsTypeShortCode codeObj = new RewardsTypeShortCode();
		codeObj.setRewardsTypeShortCode(shortCode);
		codeObj.setRewardsType(rewardsType);
		return codeObj;
	}
}
