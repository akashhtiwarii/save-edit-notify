package com.example.cd_create_edit_save.model.dto.outDto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FeeTypeShortCodeOutDTOTest {

	@Test
	void testSettersAndGetters() {
		FeeTypeShortCodeOutDTO feeTypeShortCodeOutDTO = new FeeTypeShortCodeOutDTO();

		assertNull(feeTypeShortCodeOutDTO.getFeeTypeShortCode());
		String feeTypeShortCode = "FT";
		feeTypeShortCodeOutDTO.setFeeTypeShortCode(feeTypeShortCode);
		assertEquals(feeTypeShortCode, feeTypeShortCodeOutDTO.getFeeTypeShortCode());

		assertNull(feeTypeShortCodeOutDTO.getFeeType());
		String feeType = "Processing Fee";
		feeTypeShortCodeOutDTO.setFeeType(feeType);
		assertEquals(feeType, feeTypeShortCodeOutDTO.getFeeType());
	}

	@Test
	void testEqualsAndHashCode() {
		String feeTypeShortCode = "FT";
		String feeType = "Processing Fee";

		FeeTypeShortCodeOutDTO feeType1 = setUpFeeTypeShortCodeOutDTO(feeTypeShortCode, feeType);

		assertEquals(feeType1, feeType1);
		assertEquals(feeType1.hashCode(), feeType1.hashCode());

		assertNotEquals(feeType1, new Object());

		FeeTypeShortCodeOutDTO feeType2 = setUpFeeTypeShortCodeOutDTO(feeTypeShortCode, feeType);
		assertEquals(feeType1, feeType2);
		assertEquals(feeType1.hashCode(), feeType2.hashCode());

		feeType2 = setUpFeeTypeShortCodeOutDTO("SF", feeType);
		assertNotEquals(feeType1, feeType2);
		assertNotEquals(feeType1.hashCode(), feeType2.hashCode());

		feeType2 = setUpFeeTypeShortCodeOutDTO(feeTypeShortCode, "Service Fee");
		assertNotEquals(feeType1, feeType2);
		assertNotEquals(feeType1.hashCode(), feeType2.hashCode());

		feeType1 = new FeeTypeShortCodeOutDTO();
		feeType2 = new FeeTypeShortCodeOutDTO();
		assertEquals(feeType1, feeType2);
		assertEquals(feeType1.hashCode(), feeType2.hashCode());
	}

	@Test
	void testBuilder() {
		String feeTypeShortCode = "FT";
		String feeType = "Processing Fee";

		FeeTypeShortCodeOutDTO feeTypeShortCodeOutDTO = FeeTypeShortCodeOutDTO.builder()
				.feeTypeShortCode(feeTypeShortCode).feeType(feeType).build();

		assertEquals(feeTypeShortCode, feeTypeShortCodeOutDTO.getFeeTypeShortCode());
		assertEquals(feeType, feeTypeShortCodeOutDTO.getFeeType());
	}

	@Test
	void testAllArgsConstructor() {
		String feeTypeShortCode = "FT";
		String feeType = "Processing Fee";

		FeeTypeShortCodeOutDTO feeTypeShortCodeOutDTO = new FeeTypeShortCodeOutDTO(feeTypeShortCode, feeType);

		assertEquals(feeTypeShortCode, feeTypeShortCodeOutDTO.getFeeTypeShortCode());
		assertEquals(feeType, feeTypeShortCodeOutDTO.getFeeType());
	}

	@Test
	void testNoArgsConstructor() {
		FeeTypeShortCodeOutDTO feeTypeShortCodeOutDTO = new FeeTypeShortCodeOutDTO();

		assertNull(feeTypeShortCodeOutDTO.getFeeTypeShortCode());
		assertNull(feeTypeShortCodeOutDTO.getFeeType());
	}

	private FeeTypeShortCodeOutDTO setUpFeeTypeShortCodeOutDTO(String feeTypeShortCode, String feeType) {
		FeeTypeShortCodeOutDTO feeTypeShortCodeOutDTO = new FeeTypeShortCodeOutDTO();
		feeTypeShortCodeOutDTO.setFeeTypeShortCode(feeTypeShortCode);
		feeTypeShortCodeOutDTO.setFeeType(feeType);
		return feeTypeShortCodeOutDTO;
	}
}