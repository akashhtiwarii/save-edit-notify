package com.example.cd_create_edit_save.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
	}

	@Test
	void testEqualsAndHashCode() {
		String shortCode = "PRD";
		String product = "Credit Card";

		ProductShortCode productShortCode1 = setUpProductShortCode(shortCode, product);

		assertEquals(productShortCode1, productShortCode1);
		assertEquals(productShortCode1.hashCode(), productShortCode1.hashCode());
		assertNotEquals(productShortCode1, new Object());

		ProductShortCode productShortCode2 = setUpProductShortCode(shortCode, product);
		assertEquals(productShortCode1, productShortCode2);
		assertEquals(productShortCode1.hashCode(), productShortCode2.hashCode());

		productShortCode2 = setUpProductShortCode("P01", product);
		assertNotEquals(productShortCode1, productShortCode2);
		assertNotEquals(productShortCode1.hashCode(), productShortCode2.hashCode());

		productShortCode2 = setUpProductShortCode(shortCode, "Loan");
		assertNotEquals(productShortCode1, productShortCode2);
		assertNotEquals(productShortCode1.hashCode(), productShortCode2.hashCode());

		productShortCode1 = new ProductShortCode();
		productShortCode2 = new ProductShortCode();
		assertEquals(productShortCode1, productShortCode2);
		assertEquals(productShortCode1.hashCode(), productShortCode2.hashCode());
	}

	private ProductShortCode setUpProductShortCode(String shortCode, String product) {
		ProductShortCode codeObj = new ProductShortCode();
		codeObj.setProductShortCode(shortCode);
		codeObj.setProduct(product);
		return codeObj;
	}
}
