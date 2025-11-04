package com.example.cd_create_edit_save.model.dto.outDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductShortCodeOutDTOTest {

	@Test
	void testSettersAndGetters() {
		ProductShortCodeOutDTO productShortCodeOutDTO = new ProductShortCodeOutDTO();

		assertNull(productShortCodeOutDTO.getProductShortCode());
		String productShortCode = "PS01";
		productShortCodeOutDTO.setProductShortCode(productShortCode);
		assertEquals(productShortCode, productShortCodeOutDTO.getProductShortCode());

		assertNull(productShortCodeOutDTO.getProduct());
		String product = "Savings Account";
		productShortCodeOutDTO.setProduct(product);
		assertEquals(product, productShortCodeOutDTO.getProduct());
	}

	@Test
	void testEqualsAndHashCode() {
		String productShortCode = "PS01";
		String product = "Savings Account";

		ProductShortCodeOutDTO product1 = setUpProductShortCodeOutDTO(productShortCode, product);

		assertEquals(product1, product1);
		assertEquals(product1.hashCode(), product1.hashCode());

		assertNotEquals(product1, new Object());

		ProductShortCodeOutDTO product2 = setUpProductShortCodeOutDTO(productShortCode, product);
		assertEquals(product1, product2);
		assertEquals(product1.hashCode(), product2.hashCode());

		product2 = setUpProductShortCodeOutDTO("PS02", product);
		assertNotEquals(product1, product2);
		assertNotEquals(product1.hashCode(), product2.hashCode());

		product2 = setUpProductShortCodeOutDTO(productShortCode, "Current Account");
		assertNotEquals(product1, product2);
		assertNotEquals(product1.hashCode(), product2.hashCode());

		product1 = new ProductShortCodeOutDTO();
		product2 = new ProductShortCodeOutDTO();
		assertEquals(product1, product2);
		assertEquals(product1.hashCode(), product2.hashCode());
	}

	@Test
	void testBuilder() {
		String productShortCode = "PS01";
		String product = "Savings Account";

		ProductShortCodeOutDTO productShortCodeOutDTO = ProductShortCodeOutDTO.builder()
				.productShortCode(productShortCode).product(product).build();

		assertEquals(productShortCode, productShortCodeOutDTO.getProductShortCode());
		assertEquals(product, productShortCodeOutDTO.getProduct());
	}

	@Test
	void testAllArgsConstructor() {
		String productShortCode = "PS01";
		String product = "Savings Account";

		ProductShortCodeOutDTO productShortCodeOutDTO = new ProductShortCodeOutDTO(productShortCode, product);

		assertEquals(productShortCode, productShortCodeOutDTO.getProductShortCode());
		assertEquals(product, productShortCodeOutDTO.getProduct());
	}

	@Test
	void testNoArgsConstructor() {
		ProductShortCodeOutDTO productShortCodeOutDTO = new ProductShortCodeOutDTO();

		assertNull(productShortCodeOutDTO.getProductShortCode());
		assertNull(productShortCodeOutDTO.getProduct());
	}

	private ProductShortCodeOutDTO setUpProductShortCodeOutDTO(String productShortCode, String product) {
		ProductShortCodeOutDTO productShortCodeOutDTO = new ProductShortCodeOutDTO();
		productShortCodeOutDTO.setProductShortCode(productShortCode);
		productShortCodeOutDTO.setProduct(product);
		return productShortCodeOutDTO;
	}
}