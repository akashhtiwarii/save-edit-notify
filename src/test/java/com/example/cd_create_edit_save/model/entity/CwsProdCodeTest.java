package com.example.cd_create_edit_save.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CwsProdCodeTest {

	@Test
	void testSettersAndGetters() {
		CwsProdCode cwsProdCode = new CwsProdCode();

		assertNull(cwsProdCode.getCwsProdCode());
		String code = "CWS001";
		cwsProdCode.setCwsProdCode(code);
		assertEquals(code, cwsProdCode.getCwsProdCode());

		assertNull(cwsProdCode.getDescription());
		String description = "CWS Product Code Description";
		cwsProdCode.setDescription(description);
		assertEquals(description, cwsProdCode.getDescription());

		assertNull(cwsProdCode.getStatus());
		String status = "ACTIVE";
		cwsProdCode.setStatus(status);
		assertEquals(status, cwsProdCode.getStatus());
	}

	@Test
	void testEqualsAndHashCode() {
		String code = "CWS001";
		String description = "CWS Product Code Description";
		String status = "ACTIVE";

		CwsProdCode cwsProdCode1 = setUpCwsProdCode(code, description, status);

		assertEquals(cwsProdCode1, cwsProdCode1);
		assertEquals(cwsProdCode1.hashCode(), cwsProdCode1.hashCode());
		assertNotEquals(cwsProdCode1, new Object());

		CwsProdCode cwsProdCode2 = setUpCwsProdCode(code, description, status);
		assertEquals(cwsProdCode1, cwsProdCode2);
		assertEquals(cwsProdCode1.hashCode(), cwsProdCode2.hashCode());

		cwsProdCode2 = setUpCwsProdCode("CWS002", description, status);
		assertNotEquals(cwsProdCode1, cwsProdCode2);
		assertNotEquals(cwsProdCode1.hashCode(), cwsProdCode2.hashCode());

		cwsProdCode2 = setUpCwsProdCode(code, "Different Description", status);
		assertNotEquals(cwsProdCode1, cwsProdCode2);
		assertNotEquals(cwsProdCode1.hashCode(), cwsProdCode2.hashCode());

		cwsProdCode2 = setUpCwsProdCode(code, description, "INACTIVE");
		assertNotEquals(cwsProdCode1, cwsProdCode2);
		assertNotEquals(cwsProdCode1.hashCode(), cwsProdCode2.hashCode());

		cwsProdCode1 = new CwsProdCode();
		cwsProdCode2 = new CwsProdCode();
		assertEquals(cwsProdCode1, cwsProdCode2);
		assertEquals(cwsProdCode1.hashCode(), cwsProdCode2.hashCode());
	}

	private CwsProdCode setUpCwsProdCode(String cwsProdCode, String description, String status) {
		CwsProdCode codeObj = new CwsProdCode();
		codeObj.setCwsProdCode(cwsProdCode);
		codeObj.setDescription(description);
		codeObj.setStatus(status);
		return codeObj;
	}
}
