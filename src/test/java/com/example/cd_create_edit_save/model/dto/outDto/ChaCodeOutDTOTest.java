package com.example.cd_create_edit_save.model.dto.outDto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChaCodeOutDTOTest {

	@Test
	void testSettersAndGetters() {
		ChaCodeOutDTO chaCodeOutDTO = new ChaCodeOutDTO();

		assertNull(chaCodeOutDTO.getChaCode());
		String chaCode = "CH001";
		chaCodeOutDTO.setChaCode(chaCode);
		assertEquals(chaCode, chaCodeOutDTO.getChaCode());

		assertNull(chaCodeOutDTO.getDescription());
		String description = "Online Channel";
		chaCodeOutDTO.setDescription(description);
		assertEquals(description, chaCodeOutDTO.getDescription());

		assertNull(chaCodeOutDTO.getStatus());
		String status = "ACTIVE";
		chaCodeOutDTO.setStatus(status);
		assertEquals(status, chaCodeOutDTO.getStatus());
	}

	@Test
	void testEqualsAndHashCode() {
		String chaCode = "CH001";
		String description = "Online Channel";
		String status = "ACTIVE";

		ChaCodeOutDTO chaCode1 = setUpChaCodeOutDTO(chaCode, description, status);

		assertEquals(chaCode1, chaCode1);
		assertEquals(chaCode1.hashCode(), chaCode1.hashCode());

		assertNotEquals(chaCode1, new Object());

		ChaCodeOutDTO chaCode2 = setUpChaCodeOutDTO(chaCode, description, status);
		assertEquals(chaCode1, chaCode2);
		assertEquals(chaCode1.hashCode(), chaCode2.hashCode());

		chaCode2 = setUpChaCodeOutDTO("CH002", description, status);
		assertNotEquals(chaCode1, chaCode2);
		assertNotEquals(chaCode1.hashCode(), chaCode2.hashCode());

		chaCode2 = setUpChaCodeOutDTO(chaCode, "Offline Channel", status);
		assertNotEquals(chaCode1, chaCode2);
		assertNotEquals(chaCode1.hashCode(), chaCode2.hashCode());

		chaCode2 = setUpChaCodeOutDTO(chaCode, description, "INACTIVE");
		assertNotEquals(chaCode1, chaCode2);
		assertNotEquals(chaCode1.hashCode(), chaCode2.hashCode());

		chaCode1 = new ChaCodeOutDTO();
		chaCode2 = new ChaCodeOutDTO();
		assertEquals(chaCode1, chaCode2);
		assertEquals(chaCode1.hashCode(), chaCode2.hashCode());
	}

	@Test
	void testBuilder() {
		String chaCode = "CH001";
		String description = "Online Channel";
		String status = "ACTIVE";

		ChaCodeOutDTO chaCodeOutDTO = ChaCodeOutDTO.builder().chaCode(chaCode).description(description).status(status)
				.build();

		assertEquals(chaCode, chaCodeOutDTO.getChaCode());
		assertEquals(description, chaCodeOutDTO.getDescription());
		assertEquals(status, chaCodeOutDTO.getStatus());
	}

	@Test
	void testAllArgsConstructor() {
		String chaCode = "CH001";
		String description = "Online Channel";
		String status = "ACTIVE";

		ChaCodeOutDTO chaCodeOutDTO = new ChaCodeOutDTO(chaCode, description, status);

		assertEquals(chaCode, chaCodeOutDTO.getChaCode());
		assertEquals(description, chaCodeOutDTO.getDescription());
		assertEquals(status, chaCodeOutDTO.getStatus());
	}

	@Test
	void testNoArgsConstructor() {
		ChaCodeOutDTO chaCodeOutDTO = new ChaCodeOutDTO();

		assertNull(chaCodeOutDTO.getChaCode());
		assertNull(chaCodeOutDTO.getDescription());
		assertNull(chaCodeOutDTO.getStatus());
	}

	private ChaCodeOutDTO setUpChaCodeOutDTO(String chaCode, String description, String status) {
		ChaCodeOutDTO chaCodeOutDTO = new ChaCodeOutDTO();
		chaCodeOutDTO.setChaCode(chaCode);
		chaCodeOutDTO.setDescription(description);
		chaCodeOutDTO.setStatus(status);
		return chaCodeOutDTO;
	}
}
