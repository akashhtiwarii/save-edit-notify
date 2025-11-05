package com.example.cd_create_edit_save.model.dto.outDto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserOutDTOTest {

	@Test
	void testSettersAndGetters() {
		UserOutDTO userOutDTO = new UserOutDTO();

		assertNull(userOutDTO.getUserId());
		Integer userId = 123;
		userOutDTO.setUserId(userId);
		assertEquals(userId, userOutDTO.getUserId());

		assertNull(userOutDTO.getName());
		String name = "John Doe";
		userOutDTO.setName(name);
		assertEquals(name, userOutDTO.getName());

		assertNull(userOutDTO.getRole());
		String role = "ADMIN";
		userOutDTO.setRole(role);
		assertEquals(role, userOutDTO.getRole());
	}

	@Test
	void testEqualsAndHashCode() {
		Integer userId = 123;
		String name = "John Doe";
		String role = "ADMIN";

		UserOutDTO user1 = setUpUserOutDTO(userId, name, role);

		assertEquals(user1, user1);
		assertEquals(user1.hashCode(), user1.hashCode());

		assertNotEquals(user1, new Object());

		UserOutDTO user2 = setUpUserOutDTO(userId, name, role);
		assertEquals(user1, user2);
		assertEquals(user1.hashCode(), user2.hashCode());

		user2 = setUpUserOutDTO(456, name, role);
		assertNotEquals(user1, user2);
		assertNotEquals(user1.hashCode(), user2.hashCode());

		user2 = setUpUserOutDTO(userId, "Jane Smith", role);
		assertNotEquals(user1, user2);
		assertNotEquals(user1.hashCode(), user2.hashCode());

		user2 = setUpUserOutDTO(userId, name, "USER");
		assertNotEquals(user1, user2);
		assertNotEquals(user1.hashCode(), user2.hashCode());

		user1 = new UserOutDTO();
		user2 = new UserOutDTO();
		assertEquals(user1, user2);
		assertEquals(user1.hashCode(), user2.hashCode());
	}

	@Test
	void testBuilder() {
		Integer userId = 123;
		String name = "John Doe";
		String role = "ADMIN";

		UserOutDTO userOutDTO = UserOutDTO.builder().userId(userId).name(name).role(role).build();

		assertEquals(userId, userOutDTO.getUserId());
		assertEquals(name, userOutDTO.getName());
		assertEquals(role, userOutDTO.getRole());
	}

	@Test
	void testAllArgsConstructor() {
		Integer userId = 123;
		String name = "John Doe";
		String role = "ADMIN";

		UserOutDTO userOutDTO = new UserOutDTO(userId, name, role);

		assertEquals(userId, userOutDTO.getUserId());
		assertEquals(name, userOutDTO.getName());
		assertEquals(role, userOutDTO.getRole());
	}

	@Test
	void testNoArgsConstructor() {
		UserOutDTO userOutDTO = new UserOutDTO();

		assertNull(userOutDTO.getUserId());
		assertNull(userOutDTO.getName());
		assertNull(userOutDTO.getRole());
	}

	private UserOutDTO setUpUserOutDTO(Integer userId, String name, String role) {
		UserOutDTO userOutDTO = new UserOutDTO();
		userOutDTO.setUserId(userId);
		userOutDTO.setName(name);
		userOutDTO.setRole(role);
		return userOutDTO;
	}
}