package com.example.cd_create_edit_save.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ChaCodeTest {

	@Test
	void testSettersAndGetters() {
		ChaCode chaCode = new ChaCode();

		assertNull(chaCode.getChaCode());
		String code = "CH001";
		chaCode.setChaCode(code);
		assertEquals(code, chaCode.getChaCode());

		assertNull(chaCode.getDescription());
		String description = "Channel Code Description";
		chaCode.setDescription(description);
		assertEquals(description, chaCode.getDescription());

		assertNull(chaCode.getStatus());
		String status = "ACTIVE";
		chaCode.setStatus(status);
		assertEquals(status, chaCode.getStatus());
	}

	@Test
	void testEqualsAndHashCode() {
		String code = "CH001";
		String description = "Channel Code Description";
		String status = "ACTIVE";

		ChaCode chaCode1 = setUpChaCode(code, description, status);

		assertEquals(chaCode1, chaCode1);
		assertEquals(chaCode1.hashCode(), chaCode1.hashCode());
		assertNotEquals(chaCode1, new Object());

		ChaCode chaCode2 = setUpChaCode(code, description, status);
		assertEquals(chaCode1, chaCode2);
		assertEquals(chaCode1.hashCode(), chaCode2.hashCode());

		chaCode2 = setUpChaCode("CH002", description, status);
		assertNotEquals(chaCode1, chaCode2);
		assertNotEquals(chaCode1.hashCode(), chaCode2.hashCode());

		chaCode2 = setUpChaCode(code, "Different Description", status);
		assertNotEquals(chaCode1, chaCode2);
		assertNotEquals(chaCode1.hashCode(), chaCode2.hashCode());

		chaCode2 = setUpChaCode(code, description, "INACTIVE");
		assertNotEquals(chaCode1, chaCode2);
		assertNotEquals(chaCode1.hashCode(), chaCode2.hashCode());

		chaCode1 = new ChaCode();
		chaCode2 = new ChaCode();
		assertEquals(chaCode1, chaCode2);
		assertEquals(chaCode1.hashCode(), chaCode2.hashCode());
	}

	private ChaCode setUpChaCode(String chaCode, String description, String status) {
		ChaCode codeObj = new ChaCode();
		codeObj.setChaCode(chaCode);
		codeObj.setDescription(description);
		codeObj.setStatus(status);
		return codeObj;
	}
}
