package com.example.cd_create_edit_save.model.dto.outDto;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class FeeValueOutDTOTest {

	@Test
	void testSettersAndGetters() {
		FeeValueOutDTO feeValueOutDTO = new FeeValueOutDTO();

		assertNull(feeValueOutDTO.getDescription());
		String description = "Monthly Maintenance Fee";
		feeValueOutDTO.setDescription(description);
		assertEquals(description, feeValueOutDTO.getDescription());

		assertNull(feeValueOutDTO.getFeeValue());
		BigDecimal feeValue = new BigDecimal("25.50");
		feeValueOutDTO.setFeeValue(feeValue);
		assertEquals(feeValue, feeValueOutDTO.getFeeValue());

		assertNull(feeValueOutDTO.getFeeType());
		feeValueOutDTO.setFeeType("MONTHLY");
		assertEquals("MONTHLY", feeValueOutDTO.getFeeType());
	}

	@Test
	void testEqualsAndHashCode() {
		String description = "Monthly Maintenance Fee";
		BigDecimal feeValue = new BigDecimal("25.50");
		String feeType = "MONTHLY";

		FeeValueOutDTO fee1 = setUpFeeValueOutDTO(description, feeValue, feeType);

		assertEquals(fee1, fee1);
		assertEquals(fee1.hashCode(), fee1.hashCode());

		assertNotEquals(fee1, new Object());

		FeeValueOutDTO fee2 = setUpFeeValueOutDTO(description, feeValue, feeType);
		assertEquals(fee1, fee2);
		assertEquals(fee1.hashCode(), fee2.hashCode());

		fee2 = setUpFeeValueOutDTO("Annual Service Fee", feeValue, feeType);
		assertNotEquals(fee1, fee2);
		assertNotEquals(fee1.hashCode(), fee2.hashCode());

		fee2 = setUpFeeValueOutDTO(description, new BigDecimal("50.00"), feeType);
		assertNotEquals(fee1, fee2);
		assertNotEquals(fee1.hashCode(), fee2.hashCode());

		fee2 = setUpFeeValueOutDTO(description, feeValue, "ANNUAL");
		assertNotEquals(fee1, fee2);
		assertNotEquals(fee1.hashCode(), fee2.hashCode());

		fee1 = new FeeValueOutDTO();
		fee2 = new FeeValueOutDTO();
		assertEquals(fee1, fee2);
		assertEquals(fee1.hashCode(), fee2.hashCode());
	}

	@Test
	void testBuilder() {
		String description = "Monthly Maintenance Fee";
		BigDecimal feeValue = new BigDecimal("25.50");
		String feeType = "MONTHLY";

		FeeValueOutDTO feeValueOutDTO = FeeValueOutDTO.builder().description(description).feeValue(feeValue)
				.feeType(feeType).build();

		assertEquals(description, feeValueOutDTO.getDescription());
		assertEquals(feeValue, feeValueOutDTO.getFeeValue());
		assertEquals(feeType, feeValueOutDTO.getFeeType());
	}

	@Test
	void testAllArgsConstructor() {
		String description = "Monthly Maintenance Fee";
		BigDecimal feeValue = new BigDecimal("25.50");
		String feeType = "MONTHLY";

		FeeValueOutDTO feeValueOutDTO = new FeeValueOutDTO(description, feeValue, feeType);

		assertEquals(description, feeValueOutDTO.getDescription());
		assertEquals(feeValue, feeValueOutDTO.getFeeValue());
		assertEquals(feeType, feeValueOutDTO.getFeeType());
	}

	@Test
	void testNoArgsConstructor() {
		FeeValueOutDTO feeValueOutDTO = new FeeValueOutDTO();

		assertNull(feeValueOutDTO.getDescription());
		assertNull(feeValueOutDTO.getFeeValue());
		assertNull(feeValueOutDTO.getFeeType());
	}

	private FeeValueOutDTO setUpFeeValueOutDTO(String description, BigDecimal feeValue, String feeType) {
		FeeValueOutDTO feeValueOutDTO = new FeeValueOutDTO();
		feeValueOutDTO.setDescription(description);
		feeValueOutDTO.setFeeValue(feeValue);
		feeValueOutDTO.setFeeType(feeType);
		return feeValueOutDTO;
	}
}