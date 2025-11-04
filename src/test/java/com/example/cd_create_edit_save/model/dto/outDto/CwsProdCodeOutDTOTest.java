package com.example.cd_create_edit_save.model.dto.outDto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CwsProdCodeOutDTOTest {

	@Test
	void testSettersAndGetters() {
		CwsProdCodeOutDTO cwsProdCodeOutDTO = new CwsProdCodeOutDTO();

		assertNull(cwsProdCodeOutDTO.getCwsProdCode());
		String cwsProdCode = "CWS001";
		cwsProdCodeOutDTO.setCwsProdCode(cwsProdCode);
		assertEquals(cwsProdCode, cwsProdCodeOutDTO.getCwsProdCode());

		assertNull(cwsProdCodeOutDTO.getDescription());
		String description = "CWS Product Type A";
		cwsProdCodeOutDTO.setDescription(description);
		assertEquals(description, cwsProdCodeOutDTO.getDescription());

		assertNull(cwsProdCodeOutDTO.getStatus());
		String status = "ACTIVE";
		cwsProdCodeOutDTO.setStatus(status);
		assertEquals(status, cwsProdCodeOutDTO.getStatus());
	}

	@Test
	void testEqualsAndHashCode() {
		String cwsProdCode = "CWS001";
		String description = "CWS Product Type A";
		String status = "ACTIVE";

		CwsProdCodeOutDTO cwsProd1 = setUpCwsProdCodeOutDTO(cwsProdCode, description, status);

		assertEquals(cwsProd1, cwsProd1);
		assertEquals(cwsProd1.hashCode(), cwsProd1.hashCode());

		assertNotEquals(cwsProd1, new Object());

		CwsProdCodeOutDTO cwsProd2 = setUpCwsProdCodeOutDTO(cwsProdCode, description, status);
		assertEquals(cwsProd1, cwsProd2);
		assertEquals(cwsProd1.hashCode(), cwsProd2.hashCode());

		cwsProd2 = setUpCwsProdCodeOutDTO("CWS002", description, status);
		assertNotEquals(cwsProd1, cwsProd2);
		assertNotEquals(cwsProd1.hashCode(), cwsProd2.hashCode());

		cwsProd2 = setUpCwsProdCodeOutDTO(cwsProdCode, "CWS Product Type B", status);
		assertNotEquals(cwsProd1, cwsProd2);
		assertNotEquals(cwsProd1.hashCode(), cwsProd2.hashCode());

		cwsProd2 = setUpCwsProdCodeOutDTO(cwsProdCode, description, "INACTIVE");
		assertNotEquals(cwsProd1, cwsProd2);
		assertNotEquals(cwsProd1.hashCode(), cwsProd2.hashCode());

		cwsProd1 = new CwsProdCodeOutDTO();
		cwsProd2 = new CwsProdCodeOutDTO();
		assertEquals(cwsProd1, cwsProd2);
		assertEquals(cwsProd1.hashCode(), cwsProd2.hashCode());
	}

	@Test
	void testBuilder() {
		String cwsProdCode = "CWS001";
		String description = "CWS Product Type A";
		String status = "ACTIVE";

		CwsProdCodeOutDTO cwsProdCodeOutDTO = CwsProdCodeOutDTO.builder().cwsProdCode(cwsProdCode)
				.description(description).status(status).build();

		assertEquals(cwsProdCode, cwsProdCodeOutDTO.getCwsProdCode());
		assertEquals(description, cwsProdCodeOutDTO.getDescription());
		assertEquals(status, cwsProdCodeOutDTO.getStatus());
	}

	@Test
	void testAllArgsConstructor() {
		String cwsProdCode = "CWS001";
		String description = "CWS Product Type A";
		String status = "ACTIVE";

		CwsProdCodeOutDTO cwsProdCodeOutDTO = new CwsProdCodeOutDTO(cwsProdCode, description, status);

		assertEquals(cwsProdCode, cwsProdCodeOutDTO.getCwsProdCode());
		assertEquals(description, cwsProdCodeOutDTO.getDescription());
		assertEquals(status, cwsProdCodeOutDTO.getStatus());
	}

	@Test
	void testNoArgsConstructor() {
		CwsProdCodeOutDTO cwsProdCodeOutDTO = new CwsProdCodeOutDTO();

		assertNull(cwsProdCodeOutDTO.getCwsProdCode());
		assertNull(cwsProdCodeOutDTO.getDescription());
		assertNull(cwsProdCodeOutDTO.getStatus());
	}

	private CwsProdCodeOutDTO setUpCwsProdCodeOutDTO(String cwsProdCode, String description, String status) {
		CwsProdCodeOutDTO cwsProdCodeOutDTO = new CwsProdCodeOutDTO();
		cwsProdCodeOutDTO.setCwsProdCode(cwsProdCode);
		cwsProdCodeOutDTO.setDescription(description);
		cwsProdCodeOutDTO.setStatus(status);
		return cwsProdCodeOutDTO;
	}
}
