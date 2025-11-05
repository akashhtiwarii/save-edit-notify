package com.example.cd_create_edit_save.model.dto.outDto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PrinCodeOutDTOTest {

	@Test
	void testSettersAndGetters() {
		PrinCodeOutDTO prinCodeOutDTO = new PrinCodeOutDTO();

		assertNull(prinCodeOutDTO.getPrinCode());
		String prinCode = "PRIN001";
		prinCodeOutDTO.setPrinCode(prinCode);
		assertEquals(prinCode, prinCodeOutDTO.getPrinCode());

		assertNull(prinCodeOutDTO.getDescription());
		String description = "Principal Code Type A";
		prinCodeOutDTO.setDescription(description);
		assertEquals(description, prinCodeOutDTO.getDescription());

		assertNull(prinCodeOutDTO.getStatus());
		String status = "ACTIVE";
		prinCodeOutDTO.setStatus(status);
		assertEquals(status, prinCodeOutDTO.getStatus());
	}

	@Test
	void testEqualsAndHashCode() {
		String prinCode = "PRIN001";
		String description = "Principal Code Type A";
		String status = "ACTIVE";

		PrinCodeOutDTO prinCode1 = setUpPrinCodeOutDTO(prinCode, description, status);

		assertEquals(prinCode1, prinCode1);
		assertEquals(prinCode1.hashCode(), prinCode1.hashCode());

		assertNotEquals(prinCode1, new Object());

		PrinCodeOutDTO prinCode2 = setUpPrinCodeOutDTO(prinCode, description, status);
		assertEquals(prinCode1, prinCode2);
		assertEquals(prinCode1.hashCode(), prinCode2.hashCode());

		prinCode2 = setUpPrinCodeOutDTO("PRIN002", description, status);
		assertNotEquals(prinCode1, prinCode2);
		assertNotEquals(prinCode1.hashCode(), prinCode2.hashCode());

		prinCode2 = setUpPrinCodeOutDTO(prinCode, "Principal Code Type B", status);
		assertNotEquals(prinCode1, prinCode2);
		assertNotEquals(prinCode1.hashCode(), prinCode2.hashCode());

		prinCode2 = setUpPrinCodeOutDTO(prinCode, description, "INACTIVE");
		assertNotEquals(prinCode1, prinCode2);
		assertNotEquals(prinCode1.hashCode(), prinCode2.hashCode());

		prinCode1 = new PrinCodeOutDTO();
		prinCode2 = new PrinCodeOutDTO();
		assertEquals(prinCode1, prinCode2);
		assertEquals(prinCode1.hashCode(), prinCode2.hashCode());
	}

	@Test
	void testBuilder() {
		String prinCode = "PRIN001";
		String description = "Principal Code Type A";
		String status = "ACTIVE";

		PrinCodeOutDTO prinCodeOutDTO = PrinCodeOutDTO.builder().prinCode(prinCode).description(description)
				.status(status).build();

		assertEquals(prinCode, prinCodeOutDTO.getPrinCode());
		assertEquals(description, prinCodeOutDTO.getDescription());
		assertEquals(status, prinCodeOutDTO.getStatus());
	}

	@Test
	void testAllArgsConstructor() {
		String prinCode = "PRIN001";
		String description = "Principal Code Type A";
		String status = "ACTIVE";

		PrinCodeOutDTO prinCodeOutDTO = new PrinCodeOutDTO(prinCode, description, status);

		assertEquals(prinCode, prinCodeOutDTO.getPrinCode());
		assertEquals(description, prinCodeOutDTO.getDescription());
		assertEquals(status, prinCodeOutDTO.getStatus());
	}

	@Test
	void testNoArgsConstructor() {
		PrinCodeOutDTO prinCodeOutDTO = new PrinCodeOutDTO();

		assertNull(prinCodeOutDTO.getPrinCode());
		assertNull(prinCodeOutDTO.getDescription());
		assertNull(prinCodeOutDTO.getStatus());
	}

	private PrinCodeOutDTO setUpPrinCodeOutDTO(String prinCode, String description, String status) {
		PrinCodeOutDTO prinCodeOutDTO = new PrinCodeOutDTO();
		prinCodeOutDTO.setPrinCode(prinCode);
		prinCodeOutDTO.setDescription(description);
		prinCodeOutDTO.setStatus(status);
		return prinCodeOutDTO;
	}
}