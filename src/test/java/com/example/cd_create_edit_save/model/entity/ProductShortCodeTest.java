package com.example.cd_create_edit_save.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class ProductShortCodeTest {

	@Test
	void testSettersAndGetters() {
		ProductShortCode productShortCode = new ProductShortCode();

		assertNull(productShortCode.getProductShortCode());
		String shortCode = "PRD";
		productShortCode.setProductShortCode(shortCode);
		assertEquals(shortCode, productShortCode.getProductShortCode());

		assertNull(productShortCode.getProduct());
		String product = "Credit Card";
		productShortCode.setProduct(product);
		assertEquals(product, productShortCode.getProduct());

		assertNull(productShortCode.getCreatedBy());
		String createdBy = "creatorUser";
		productShortCode.setCreatedBy(createdBy);
		assertEquals(createdBy, productShortCode.getCreatedBy());

		assertNull(productShortCode.getCreatedDatetime());
		LocalDateTime createdDatetime = LocalDateTime.now();
		productShortCode.setCreatedDatetime(createdDatetime);
		assertEquals(createdDatetime, productShortCode.getCreatedDatetime());

		assertNull(productShortCode.getUpdatedBy());
		String updatedBy = "updaterUser";
		productShortCode.setUpdatedBy(updatedBy);
		assertEquals(updatedBy, productShortCode.getUpdatedBy());

		assertNull(productShortCode.getUpdatedDatetime());
		LocalDateTime updatedDatetime = LocalDateTime.now();
		productShortCode.setUpdatedDatetime(updatedDatetime);
		assertEquals(updatedDatetime, productShortCode.getUpdatedDatetime());
	}

	@Test
	void testEqualsAndHashCode() {
		String shortCode = "PRD";
		String product = "Credit Card";
		String createdBy = "creator";
		LocalDateTime createdDatetime = LocalDateTime.of(2023, 5, 1, 10, 30);
		String updatedBy = "updater";
		LocalDateTime updatedDatetime = LocalDateTime.of(2023, 6, 1, 12, 0);

		ProductShortCode productShortCode1 = setUpProductShortCode(shortCode, product, createdBy, createdDatetime,
				updatedBy, updatedDatetime);

		assertEquals(productShortCode1, productShortCode1);
		assertEquals(productShortCode1.hashCode(), productShortCode1.hashCode());
		assertNotEquals(productShortCode1, new Object());

		ProductShortCode productShortCode2 = setUpProductShortCode(shortCode, product, createdBy, createdDatetime,
				updatedBy, updatedDatetime);
		assertEquals(productShortCode1, productShortCode2);
		assertEquals(productShortCode1.hashCode(), productShortCode2.hashCode());

		productShortCode2 = setUpProductShortCode("P01", product, createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(productShortCode1, productShortCode2);

		productShortCode2 = setUpProductShortCode(shortCode, "Loan", createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(productShortCode1, productShortCode2);

		productShortCode2 = setUpProductShortCode(shortCode, product, "differentUser", createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(productShortCode1, productShortCode2);

		productShortCode2 = setUpProductShortCode(shortCode, product, createdBy, createdDatetime, "otherUpdater",
				updatedDatetime);
		assertNotEquals(productShortCode1, productShortCode2);

		productShortCode1 = new ProductShortCode();
		productShortCode2 = new ProductShortCode();
		assertEquals(productShortCode1, productShortCode2);
		assertEquals(productShortCode1.hashCode(), productShortCode2.hashCode());
	}

	private ProductShortCode setUpProductShortCode(String shortCode, String product, String createdBy,
			LocalDateTime createdDatetime, String updatedBy, LocalDateTime updatedDatetime) {
		ProductShortCode codeObj = new ProductShortCode();
		codeObj.setProductShortCode(shortCode);
		codeObj.setProduct(product);
		codeObj.setCreatedBy(createdBy);
		codeObj.setCreatedDatetime(createdDatetime);
		codeObj.setUpdatedBy(updatedBy);
		codeObj.setUpdatedDatetime(updatedDatetime);
		return codeObj;
	}
}
