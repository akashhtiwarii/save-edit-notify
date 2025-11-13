package com.example.cd_create_edit_save.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

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

		assertNull(cwsProdCode.getCreatedBy());
		String createdBy = "creatorUser";
		cwsProdCode.setCreatedBy(createdBy);
		assertEquals(createdBy, cwsProdCode.getCreatedBy());

		assertNull(cwsProdCode.getCreatedDatetime());
		LocalDateTime createdDatetime = LocalDateTime.now();
		cwsProdCode.setCreatedDatetime(createdDatetime);
		assertEquals(createdDatetime, cwsProdCode.getCreatedDatetime());

		assertNull(cwsProdCode.getUpdatedBy());
		String updatedBy = "updaterUser";
		cwsProdCode.setUpdatedBy(updatedBy);
		assertEquals(updatedBy, cwsProdCode.getUpdatedBy());

		assertNull(cwsProdCode.getUpdatedDatetime());
		LocalDateTime updatedDatetime = LocalDateTime.now();
		cwsProdCode.setUpdatedDatetime(updatedDatetime);
		assertEquals(updatedDatetime, cwsProdCode.getUpdatedDatetime());
	}

	@Test
	void testEqualsAndHashCode() {
		String code = "CWS001";
		String description = "CWS Product Code Description";
		String status = "ACTIVE";
		String createdBy = "creator";
		LocalDateTime createdDatetime = LocalDateTime.of(2023, 5, 1, 10, 30);
		String updatedBy = "updater";
		LocalDateTime updatedDatetime = LocalDateTime.of(2023, 6, 1, 12, 0);

		CwsProdCode cwsProdCode1 = setUpCwsProdCode(code, description, status, createdBy, createdDatetime, updatedBy,
				updatedDatetime);

		assertEquals(cwsProdCode1, cwsProdCode1);
		assertEquals(cwsProdCode1.hashCode(), cwsProdCode1.hashCode());
		assertNotEquals(cwsProdCode1, new Object());

		CwsProdCode cwsProdCode2 = setUpCwsProdCode(code, description, status, createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertEquals(cwsProdCode1, cwsProdCode2);
		assertEquals(cwsProdCode1.hashCode(), cwsProdCode2.hashCode());

		cwsProdCode2 = setUpCwsProdCode("CWS002", description, status, createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(cwsProdCode1, cwsProdCode2);

		cwsProdCode2 = setUpCwsProdCode(code, "Different Description", status, createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(cwsProdCode1, cwsProdCode2);

		cwsProdCode2 = setUpCwsProdCode(code, description, "INACTIVE", createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(cwsProdCode1, cwsProdCode2);

		cwsProdCode2 = setUpCwsProdCode(code, description, status, "otherCreator", createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(cwsProdCode1, cwsProdCode2);

		cwsProdCode2 = setUpCwsProdCode(code, description, status, createdBy, createdDatetime, "anotherUpdater",
				updatedDatetime);
		assertNotEquals(cwsProdCode1, cwsProdCode2);

		cwsProdCode1 = new CwsProdCode();
		cwsProdCode2 = new CwsProdCode();
		assertEquals(cwsProdCode1, cwsProdCode2);
		assertEquals(cwsProdCode1.hashCode(), cwsProdCode2.hashCode());
	}

	private CwsProdCode setUpCwsProdCode(String cwsProdCode, String description, String status, String createdBy,
			LocalDateTime createdDatetime, String updatedBy, LocalDateTime updatedDatetime) {
		CwsProdCode codeObj = new CwsProdCode();
		codeObj.setCwsProdCode(cwsProdCode);
		codeObj.setDescription(description);
		codeObj.setStatus(status);
		codeObj.setCreatedBy(createdBy);
		codeObj.setCreatedDatetime(createdDatetime);
		codeObj.setUpdatedBy(updatedBy);
		codeObj.setUpdatedDatetime(updatedDatetime);
		return codeObj;
	}
}
