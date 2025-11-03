package com.example.cd_create_edit_save.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrinCodeTest {

	@Test
	void testSettersAndGetters() {
		PrinCode prinCode = new PrinCode();

		assertNull(prinCode.getPrinCode());
		String code = "PR001";
		prinCode.setPrinCode(code);
		assertEquals(code, prinCode.getPrinCode());

		assertNull(prinCode.getDescription());
		String description = "Principal Code Description";
		prinCode.setDescription(description);
		assertEquals(description, prinCode.getDescription());

		assertNull(prinCode.getStatus());
		String status = "ACTIVE";
		prinCode.setStatus(status);
		assertEquals(status, prinCode.getStatus());
	}

	@Test
	void testEqualsAndHashCode() {
		String code = "PR001";
		String description = "Principal Code Description";
		String status = "ACTIVE";

		PrinCode prinCode1 = setUpPrinCode(code, description, status);

		assertEquals(prinCode1, prinCode1);
		assertEquals(prinCode1.hashCode(), prinCode1.hashCode());
		assertNotEquals(prinCode1, new Object());

		PrinCode prinCode2 = setUpPrinCode(code, description, status);
		assertEquals(prinCode1, prinCode2);
		assertEquals(prinCode1.hashCode(), prinCode2.hashCode());

		prinCode2 = setUpPrinCode("PR002", description, status);
		assertNotEquals(prinCode1, prinCode2);
		assertNotEquals(prinCode1.hashCode(), prinCode2.hashCode());

		prinCode2 = setUpPrinCode(code, "Different Description", status);
		assertNotEquals(prinCode1, prinCode2);
		assertNotEquals(prinCode1.hashCode(), prinCode2.hashCode());

		prinCode2 = setUpPrinCode(code, description, "INACTIVE");
		assertNotEquals(prinCode1, prinCode2);
		assertNotEquals(prinCode1.hashCode(), prinCode2.hashCode());

		prinCode1 = new PrinCode();
		prinCode2 = new PrinCode();
		assertEquals(prinCode1, prinCode2);
		assertEquals(prinCode1.hashCode(), prinCode2.hashCode());
	}

	private PrinCode setUpPrinCode(String prinCode, String description, String status) {
		PrinCode codeObj = new PrinCode();
		codeObj.setPrinCode(prinCode);
		codeObj.setDescription(description);
		codeObj.setStatus(status);
		return codeObj;
	}
}
