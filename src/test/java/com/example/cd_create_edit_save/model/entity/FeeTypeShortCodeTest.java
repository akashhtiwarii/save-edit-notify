package com.example.cd_create_edit_save.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
	}

	@Test
	void testEqualsAndHashCode() {
		String shortCode = "FT";
		String feeType = "Processing Fee";

		FeeTypeShortCode feeTypeShortCode1 = setUpFeeTypeShortCode(shortCode, feeType);

		assertEquals(feeTypeShortCode1, feeTypeShortCode1);
		assertEquals(feeTypeShortCode1.hashCode(), feeTypeShortCode1.hashCode());
		assertNotEquals(feeTypeShortCode1, new Object());

		FeeTypeShortCode feeTypeShortCode2 = setUpFeeTypeShortCode(shortCode, feeType);
		assertEquals(feeTypeShortCode1, feeTypeShortCode2);
		assertEquals(feeTypeShortCode1.hashCode(), feeTypeShortCode2.hashCode());

		feeTypeShortCode2 = setUpFeeTypeShortCode("FX", feeType);
		assertNotEquals(feeTypeShortCode1, feeTypeShortCode2);
		assertNotEquals(feeTypeShortCode1.hashCode(), feeTypeShortCode2.hashCode());

		feeTypeShortCode2 = setUpFeeTypeShortCode(shortCode, "Transaction Fee");
		assertNotEquals(feeTypeShortCode1, feeTypeShortCode2);
		assertNotEquals(feeTypeShortCode1.hashCode(), feeTypeShortCode2.hashCode());

		feeTypeShortCode1 = new FeeTypeShortCode();
		feeTypeShortCode2 = new FeeTypeShortCode();
		assertEquals(feeTypeShortCode1, feeTypeShortCode2);
		assertEquals(feeTypeShortCode1.hashCode(), feeTypeShortCode2.hashCode());
	}

	private FeeTypeShortCode setUpFeeTypeShortCode(String shortCode, String feeType) {
		FeeTypeShortCode codeObj = new FeeTypeShortCode();
		codeObj.setFeeTypeShortCode(shortCode);
		codeObj.setFeeType(feeType);
		return codeObj;
	}
}
