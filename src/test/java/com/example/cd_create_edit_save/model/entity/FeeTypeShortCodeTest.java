package com.example.cd_create_edit_save.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class FeeTypeShortCodeTest {

	@Test
	void testSettersAndGetters() {
		FeeTypeShortCode feeTypeShortCode = new FeeTypeShortCode();

		assertNull(feeTypeShortCode.getFeeTypeShortCode());
		String shortCode = "FT";
		feeTypeShortCode.setFeeTypeShortCode(shortCode);
		assertEquals(shortCode, feeTypeShortCode.getFeeTypeShortCode());

		assertNull(feeTypeShortCode.getFeeType());
		String feeType = "Processing Fee";
		feeTypeShortCode.setFeeType(feeType);
		assertEquals(feeType, feeTypeShortCode.getFeeType());

		assertNull(feeTypeShortCode.getCreatedBy());
		String createdBy = "creatorUser";
		feeTypeShortCode.setCreatedBy(createdBy);
		assertEquals(createdBy, feeTypeShortCode.getCreatedBy());

		assertNull(feeTypeShortCode.getCreatedDatetime());
		LocalDateTime createdDatetime = LocalDateTime.now();
		feeTypeShortCode.setCreatedDatetime(createdDatetime);
		assertEquals(createdDatetime, feeTypeShortCode.getCreatedDatetime());

		assertNull(feeTypeShortCode.getUpdatedBy());
		String updatedBy = "updaterUser";
		feeTypeShortCode.setUpdatedBy(updatedBy);
		assertEquals(updatedBy, feeTypeShortCode.getUpdatedBy());

		assertNull(feeTypeShortCode.getUpdatedDatetime());
		LocalDateTime updatedDatetime = LocalDateTime.now();
		feeTypeShortCode.setUpdatedDatetime(updatedDatetime);
		assertEquals(updatedDatetime, feeTypeShortCode.getUpdatedDatetime());
	}

	@Test
	void testEqualsAndHashCode() {
		String shortCode = "FT";
		String feeType = "Processing Fee";
		String createdBy = "creator";
		LocalDateTime createdDatetime = LocalDateTime.of(2023, 5, 1, 10, 30);
		String updatedBy = "updater";
		LocalDateTime updatedDatetime = LocalDateTime.of(2023, 6, 1, 12, 0);

		FeeTypeShortCode feeTypeShortCode1 = setUpFeeTypeShortCode(shortCode, feeType, createdBy, createdDatetime,
				updatedBy, updatedDatetime);

		assertEquals(feeTypeShortCode1, feeTypeShortCode1);
		assertEquals(feeTypeShortCode1.hashCode(), feeTypeShortCode1.hashCode());
		assertNotEquals(feeTypeShortCode1, new Object());

		FeeTypeShortCode feeTypeShortCode2 = setUpFeeTypeShortCode(shortCode, feeType, createdBy, createdDatetime,
				updatedBy, updatedDatetime);
		assertEquals(feeTypeShortCode1, feeTypeShortCode2);
		assertEquals(feeTypeShortCode1.hashCode(), feeTypeShortCode2.hashCode());

		feeTypeShortCode2 = setUpFeeTypeShortCode("FX", feeType, createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(feeTypeShortCode1, feeTypeShortCode2);

		feeTypeShortCode2 = setUpFeeTypeShortCode(shortCode, "Transaction Fee", createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(feeTypeShortCode1, feeTypeShortCode2);

		feeTypeShortCode2 = setUpFeeTypeShortCode(shortCode, feeType, "differentUser", createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(feeTypeShortCode1, feeTypeShortCode2);

		feeTypeShortCode2 = setUpFeeTypeShortCode(shortCode, feeType, createdBy, createdDatetime, "otherUpdater",
				updatedDatetime);
		assertNotEquals(feeTypeShortCode1, feeTypeShortCode2);

		feeTypeShortCode1 = new FeeTypeShortCode();
		feeTypeShortCode2 = new FeeTypeShortCode();
		assertEquals(feeTypeShortCode1, feeTypeShortCode2);
		assertEquals(feeTypeShortCode1.hashCode(), feeTypeShortCode2.hashCode());
	}

	private FeeTypeShortCode setUpFeeTypeShortCode(String shortCode, String feeType, String createdBy,
			LocalDateTime createdDatetime, String updatedBy, LocalDateTime updatedDatetime) {
		FeeTypeShortCode codeObj = new FeeTypeShortCode();
		codeObj.setFeeTypeShortCode(shortCode);
		codeObj.setFeeType(feeType);
		codeObj.setCreatedBy(createdBy);
		codeObj.setCreatedDatetime(createdDatetime);
		codeObj.setUpdatedBy(updatedBy);
		codeObj.setUpdatedDatetime(updatedDatetime);
		return codeObj;
	}
}
