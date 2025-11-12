package com.example.cd_create_edit_save.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class FeeValueTest {

	@Test
	void testSettersAndGetters() {
		FeeValue feeValue = new FeeValue();

		assertNull(feeValue.getId());
		Long id = 1L;
		feeValue.setId(id);
		assertEquals(id, feeValue.getId());

		assertNull(feeValue.getDescription());
		String description = "Monthly Maintenance Fee";
		feeValue.setDescription(description);
		assertEquals(description, feeValue.getDescription());

		assertNull(feeValue.getFeeType());
		String feeType = "MONTHLY";
		feeValue.setFeeType(feeType);
		assertEquals(feeType, feeValue.getFeeType());

		assertNull(feeValue.getFeeValue());
		BigDecimal feeAmount = new BigDecimal("25.50");
		feeValue.setFeeValue(feeAmount);
		assertEquals(feeAmount, feeValue.getFeeValue());

		assertNull(feeValue.getCreatedBy());
		String createdBy = "admin";
		feeValue.setCreatedBy(createdBy);
		assertEquals(createdBy, feeValue.getCreatedBy());

		assertNull(feeValue.getCreatedDatetime());
		LocalDateTime createdDatetime = LocalDateTime.now();
		feeValue.setCreatedDatetime(createdDatetime);
		assertEquals(createdDatetime, feeValue.getCreatedDatetime());

		assertNull(feeValue.getUpdatedBy());
		String updatedBy = "admin";
		feeValue.setUpdatedBy(updatedBy);
		assertEquals(updatedBy, feeValue.getUpdatedBy());

		assertNull(feeValue.getUpdatedDatetime());
		LocalDateTime updatedDatetime = LocalDateTime.now();
		feeValue.setUpdatedDatetime(updatedDatetime);
		assertEquals(updatedDatetime, feeValue.getUpdatedDatetime());
	}

	@Test
	void testEqualsAndHashCode() {
		Long id = 1L;
		String description = "Monthly Maintenance Fee";
		String feeType = "MONTHLY";
		BigDecimal feeAmount = new BigDecimal("25.50");
		String createdBy = "admin";
		LocalDateTime createdDatetime = LocalDateTime.now();
		String updatedBy = "admin";
		LocalDateTime updatedDatetime = LocalDateTime.now();

		FeeValue feeValue1 = setUpFeeValue(id, description, feeType, feeAmount, createdBy, createdDatetime, updatedBy,
				updatedDatetime);

		assertEquals(feeValue1, feeValue1);
		assertEquals(feeValue1.hashCode(), feeValue1.hashCode());
		assertNotEquals(feeValue1, new Object());

		FeeValue feeValue2 = setUpFeeValue(id, description, feeType, feeAmount, createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertEquals(feeValue1, feeValue2);
		assertEquals(feeValue1.hashCode(), feeValue2.hashCode());

		feeValue2 = setUpFeeValue(2L, description, feeType, feeAmount, createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(feeValue1, feeValue2);
		assertNotEquals(feeValue1.hashCode(), feeValue2.hashCode());

		feeValue2 = setUpFeeValue(id, "Annual Service Fee", feeType, feeAmount, createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(feeValue1, feeValue2);
		assertNotEquals(feeValue1.hashCode(), feeValue2.hashCode());

		feeValue2 = setUpFeeValue(id, description, "ANNUAL", feeAmount, createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(feeValue1, feeValue2);
		assertNotEquals(feeValue1.hashCode(), feeValue2.hashCode());

		feeValue2 = setUpFeeValue(id, description, feeType, new BigDecimal("50.00"), createdBy, createdDatetime,
				updatedBy, updatedDatetime);
		assertNotEquals(feeValue1, feeValue2);
		assertNotEquals(feeValue1.hashCode(), feeValue2.hashCode());

		feeValue2 = setUpFeeValue(id, description, feeType, feeAmount, "user", createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(feeValue1, feeValue2);
		assertNotEquals(feeValue1.hashCode(), feeValue2.hashCode());

		feeValue2 = setUpFeeValue(id, description, feeType, feeAmount, createdBy, LocalDateTime.now().plusDays(1),
				updatedBy, updatedDatetime);
		assertNotEquals(feeValue1, feeValue2);
		assertNotEquals(feeValue1.hashCode(), feeValue2.hashCode());

		feeValue2 = setUpFeeValue(id, description, feeType, feeAmount, createdBy, createdDatetime, "user",
				updatedDatetime);
		assertNotEquals(feeValue1, feeValue2);
		assertNotEquals(feeValue1.hashCode(), feeValue2.hashCode());

		feeValue2 = setUpFeeValue(id, description, feeType, feeAmount, createdBy, createdDatetime, updatedBy,
				LocalDateTime.now().plusDays(1));
		assertNotEquals(feeValue1, feeValue2);
		assertNotEquals(feeValue1.hashCode(), feeValue2.hashCode());

		feeValue1 = new FeeValue();
		feeValue2 = new FeeValue();
		assertEquals(feeValue1, feeValue2);
		assertEquals(feeValue1.hashCode(), feeValue2.hashCode());
	}

	@Test
	void testBuilder() {
		Long id = 1L;
		String description = "Monthly Maintenance Fee";
		String feeType = "MONTHLY";
		BigDecimal feeAmount = new BigDecimal("25.50");
		String createdBy = "admin";
		LocalDateTime createdDatetime = LocalDateTime.now();
		String updatedBy = "admin";
		LocalDateTime updatedDatetime = LocalDateTime.now();

		FeeValue feeValue = FeeValue.builder().id(id).description(description).feeType(feeType).feeValue(feeAmount)
				.createdBy(createdBy).createdDatetime(createdDatetime).updatedBy(updatedBy)
				.updatedDatetime(updatedDatetime).build();

		assertEquals(id, feeValue.getId());
		assertEquals(description, feeValue.getDescription());
		assertEquals(feeType, feeValue.getFeeType());
		assertEquals(feeAmount, feeValue.getFeeValue());
		assertEquals(createdBy, feeValue.getCreatedBy());
		assertEquals(createdDatetime, feeValue.getCreatedDatetime());
		assertEquals(updatedBy, feeValue.getUpdatedBy());
		assertEquals(updatedDatetime, feeValue.getUpdatedDatetime());
	}

	@Test
	void testAllArgsConstructor() {
		Long id = 1L;
		String description = "Monthly Maintenance Fee";
		String feeType = "MONTHLY";
		BigDecimal feeAmount = new BigDecimal("25.50");
		String createdBy = "admin";
		LocalDateTime createdDatetime = LocalDateTime.now();
		String updatedBy = "admin";
		LocalDateTime updatedDatetime = LocalDateTime.now();

		FeeValue feeValue = new FeeValue(id, description, feeType, feeAmount, createdBy, createdDatetime, updatedBy,
				updatedDatetime);

		assertEquals(id, feeValue.getId());
		assertEquals(description, feeValue.getDescription());
		assertEquals(feeType, feeValue.getFeeType());
		assertEquals(feeAmount, feeValue.getFeeValue());
		assertEquals(createdBy, feeValue.getCreatedBy());
		assertEquals(createdDatetime, feeValue.getCreatedDatetime());
		assertEquals(updatedBy, feeValue.getUpdatedBy());
		assertEquals(updatedDatetime, feeValue.getUpdatedDatetime());
	}

	@Test
	void testNoArgsConstructor() {
		FeeValue feeValue = new FeeValue();

		assertNull(feeValue.getId());
		assertNull(feeValue.getDescription());
		assertNull(feeValue.getFeeType());
		assertNull(feeValue.getFeeValue());
		assertNull(feeValue.getCreatedBy());
		assertNull(feeValue.getCreatedDatetime());
		assertNull(feeValue.getUpdatedBy());
		assertNull(feeValue.getUpdatedDatetime());
	}

	private FeeValue setUpFeeValue(Long id, String description, String feeType, BigDecimal feeAmount, String createdBy,
			LocalDateTime createdDatetime, String updatedBy, LocalDateTime updatedDatetime) {
		FeeValue feeValue = new FeeValue();
		feeValue.setId(id);
		feeValue.setDescription(description);
		feeValue.setFeeType(feeType);
		feeValue.setFeeValue(feeAmount);
		feeValue.setCreatedBy(createdBy);
		feeValue.setCreatedDatetime(createdDatetime);
		feeValue.setUpdatedBy(updatedBy);
		feeValue.setUpdatedDatetime(updatedDatetime);
		return feeValue;
	}
}
