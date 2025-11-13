package com.example.cd_create_edit_save.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

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

		assertNull(chaCode.getCreatedBy());
		String createdBy = "adminUser";
		chaCode.setCreatedBy(createdBy);
		assertEquals(createdBy, chaCode.getCreatedBy());

		assertNull(chaCode.getCreatedDatetime());
		LocalDateTime createdDatetime = LocalDateTime.now();
		chaCode.setCreatedDatetime(createdDatetime);
		assertEquals(createdDatetime, chaCode.getCreatedDatetime());

		assertNull(chaCode.getUpdatedBy());
		String updatedBy = "updateUser";
		chaCode.setUpdatedBy(updatedBy);
		assertEquals(updatedBy, chaCode.getUpdatedBy());

		assertNull(chaCode.getUpdatedDatetime());
		LocalDateTime updatedDatetime = LocalDateTime.now();
		chaCode.setUpdatedDatetime(updatedDatetime);
		assertEquals(updatedDatetime, chaCode.getUpdatedDatetime());
	}

	@Test
	void testEqualsAndHashCode() {
		String code = "CH001";
		String description = "Channel Code Description";
		String status = "ACTIVE";
		String createdBy = "creator";
		LocalDateTime createdDatetime = LocalDateTime.of(2023, 5, 1, 10, 30);
		String updatedBy = "updater";
		LocalDateTime updatedDatetime = LocalDateTime.of(2023, 6, 1, 12, 0);

		ChaCode chaCode1 = setUpChaCode(code, description, status, createdBy, createdDatetime, updatedBy,
				updatedDatetime);

		assertEquals(chaCode1, chaCode1);
		assertEquals(chaCode1.hashCode(), chaCode1.hashCode());
		assertNotEquals(chaCode1, new Object());

		ChaCode chaCode2 = setUpChaCode(code, description, status, createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertEquals(chaCode1, chaCode2);
		assertEquals(chaCode1.hashCode(), chaCode2.hashCode());

		chaCode2 = setUpChaCode("CH002", description, status, createdBy, createdDatetime, updatedBy, updatedDatetime);
		assertNotEquals(chaCode1, chaCode2);

		chaCode2 = setUpChaCode(code, "Different Description", status, createdBy, createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(chaCode1, chaCode2);

		chaCode2 = setUpChaCode(code, description, "INACTIVE", createdBy, createdDatetime, updatedBy, updatedDatetime);
		assertNotEquals(chaCode1, chaCode2);

		chaCode2 = setUpChaCode(code, description, status, "differentUser", createdDatetime, updatedBy,
				updatedDatetime);
		assertNotEquals(chaCode1, chaCode2);

		chaCode2 = setUpChaCode(code, description, status, createdBy, createdDatetime, "otherUpdater", updatedDatetime);
		assertNotEquals(chaCode1, chaCode2);

		chaCode1 = new ChaCode();
		chaCode2 = new ChaCode();
		assertEquals(chaCode1, chaCode2);
		assertEquals(chaCode1.hashCode(), chaCode2.hashCode());
	}

	private ChaCode setUpChaCode(String chaCode, String description, String status, String createdBy,
			LocalDateTime createdDatetime, String updatedBy, LocalDateTime updatedDatetime) {
		ChaCode codeObj = new ChaCode();
		codeObj.setChaCode(chaCode);
		codeObj.setDescription(description);
		codeObj.setStatus(status);
		codeObj.setCreatedBy(createdBy);
		codeObj.setCreatedDatetime(createdDatetime);
		codeObj.setUpdatedBy(updatedBy);
		codeObj.setUpdatedDatetime(updatedDatetime);
		return codeObj;
	}
}
