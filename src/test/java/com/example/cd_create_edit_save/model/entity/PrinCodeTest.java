package com.example.cd_create_edit_save.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

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

		assertNull(prinCode.getCreatedBy());
		String createdBy = "creatorUser";
		prinCode.setCreatedBy(createdBy);
		assertEquals(createdBy, prinCode.getCreatedBy());

		assertNull(prinCode.getCreatedDatetime());
		LocalDateTime createdDatetime = LocalDateTime.now();
		prinCode.setCreatedDatetime(createdDatetime);
		assertEquals(createdDatetime, prinCode.getCreatedDatetime());

		assertNull(prinCode.getUpdatedBy());
		String updatedBy = "updaterUser";
		prinCode.setUpdatedBy(updatedBy);
		assertEquals(updatedBy, prinCode.getUpdatedBy());

		assertNull(prinCode.getUpdatedDatetime());
		LocalDateTime updatedDatetime = LocalDateTime.now();
		prinCode.setUpdatedDatetime(updatedDatetime);
		assertEquals(updatedDatetime, prinCode.getUpdatedDatetime());
	}

	@Test
	void testEqualsAndHashCode() {
		String code = "PR001";
		String description = "Principal Code Description";
		String status = "ACTIVE";
		String createdBy = "creator";
		LocalDateTime createdDatetime = LocalDateTime.of(2023, 5, 1, 10, 30);
		String updatedBy = "updater";
		LocalDateTime updatedDatetime = LocalDateTime.of(2023, 6, 1, 12, 0);

		PrinCode prinCode1 = setUpPrinCode(code, description, status, createdBy, createdDatetime, updatedBy,
				updatedDatetime);

		assertEquals(prinCode1, prinCode1);
		assertEquals(prinCode1.hashCode(), prinCode1.hashCode());
		assertNotEquals(prinCode1, new Object());

		PrinCode prinCode2 = setUpPrinCode(code, description, status, createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertEquals(prinCode1, prinCode2);
		assertEquals(prinCode1.hashCode(), prinCode2.hashCode());

		prinCode2 = setUpPrinCode("PR002", description, status, createdBy, createdDatetime, updatedBy, updatedDatetime);
		assertNotEquals(prinCode1, prinCode2);

		prinCode2 = setUpPrinCode(code, "Different Description", status, createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(prinCode1, prinCode2);

		prinCode2 = setUpPrinCode(code, description, "INACTIVE", createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(prinCode1, prinCode2);

		prinCode2 = setUpPrinCode(code, description, status, "differentUser", createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(prinCode1, prinCode2);

		prinCode2 = setUpPrinCode(code, description, status, createdBy, createdDatetime, "otherUpdater",
				updatedDatetime);
		assertNotEquals(prinCode1, prinCode2);

		prinCode1 = new PrinCode();
		prinCode2 = new PrinCode();
		assertEquals(prinCode1, prinCode2);
		assertEquals(prinCode1.hashCode(), prinCode2.hashCode());
	}

	private PrinCode setUpPrinCode(String prinCode, String description, String status, String createdBy,
			LocalDateTime createdDatetime, String updatedBy, LocalDateTime updatedDatetime) {
		PrinCode codeObj = new PrinCode();
		codeObj.setPrinCode(prinCode);
		codeObj.setDescription(description);
		codeObj.setStatus(status);
		codeObj.setCreatedBy(createdBy);
		codeObj.setCreatedDatetime(createdDatetime);
		codeObj.setUpdatedBy(updatedBy);
		codeObj.setUpdatedDatetime(updatedDatetime);
		return codeObj;
	}
}
