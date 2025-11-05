package com.example.cd_create_edit_save.model.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class UserTest {

	@Test
	void testSettersAndGetters() {
		User user = new User();

		assertNull(user.getUserId());
		Integer id = 1;
		user.setUserId(id);
		assertEquals(id, user.getUserId());

		assertNull(user.getFirstName());
		String firstName = "John";
		user.setFirstName(firstName);
		assertEquals(firstName, user.getFirstName());

		assertNull(user.getLastName());
		String lastName = "Doe";
		user.setLastName(lastName);
		assertEquals(lastName, user.getLastName());

		assertNull(user.getUsername());
		String username = "johndoe";
		user.setUsername(username);
		assertEquals(username, user.getUsername());

		assertNull(user.getEmail());
		String email = "john.doe@example.com";
		user.setEmail(email);
		assertEquals(email, user.getEmail());

		assertNull(user.getRole());
		String role = "ADMIN";
		user.setRole(role);
		assertEquals(role, user.getRole());

		assertNull(user.getPassword());
		String password = "encrypted123";
		user.setPassword(password);
		assertEquals(password, user.getPassword());

		assertEquals("INACTIVE", user.getStatus());
		String status = "ACTIVE";
		user.setStatus(status);
		assertEquals(status, user.getStatus());

		assertNotNull(user.getCreatedAt());
		LocalDateTime createdAt = LocalDateTime.of(2024, 1, 1, 12, 0);
		user.setCreatedAt(createdAt);
		assertEquals(createdAt, user.getCreatedAt());

		assertNotNull(user.getUpdatedAt());
		LocalDateTime updatedAt = LocalDateTime.of(2024, 2, 1, 12, 0);
		user.setUpdatedAt(updatedAt);
		assertEquals(updatedAt, user.getUpdatedAt());
	}

	@Test
	void testPreUpdate() {
		User user = new User();
		LocalDateTime beforeUpdate = user.getUpdatedAt();

		try {
			Thread.sleep(10);
		} catch (InterruptedException ignored) {
		}

		user.preUpdate();
		assertTrue(user.getUpdatedAt().isAfter(beforeUpdate));
	}

	@Test
	void testEqualsAndHashCode() {
		Integer id = 1;
		String firstName = "John";
		String lastName = "Doe";
		String username = "johndoe";
		String email = "john.doe@example.com";
		String role = "ADMIN";
		String password = "encrypted123";
		String status = "ACTIVE";
		LocalDateTime createdAt = LocalDateTime.now();
		LocalDateTime updatedAt = LocalDateTime.now();

		User user1 = setUpUser(id, firstName, lastName, username, email, role, password, status, createdAt, updatedAt);

		assertEquals(user1, user1);
		assertEquals(user1.hashCode(), user1.hashCode());
		assertNotEquals(user1, new Object());

		User user2 = setUpUser(id, firstName, lastName, username, email, role, password, status, createdAt, updatedAt);
		assertEquals(user1, user2);
		assertEquals(user1.hashCode(), user2.hashCode());

		user2 = setUpUser(2, firstName, lastName, username, email, role, password, status, createdAt, updatedAt);
		assertNotEquals(user1, user2);
		assertNotEquals(user1.hashCode(), user2.hashCode());

		user2 = setUpUser(id, firstName, lastName, "janedoe", email, role, password, status, createdAt, updatedAt);
		assertNotEquals(user1, user2);
		assertNotEquals(user1.hashCode(), user2.hashCode());

		user2 = setUpUser(id, firstName, lastName, username, "different@example.com", role, password, status, createdAt,
				updatedAt);
		assertNotEquals(user1, user2);
		assertNotEquals(user1.hashCode(), user2.hashCode());

		user1 = new User();
		user2 = new User();
		assertEquals(user1, user2);
		assertEquals(user1.hashCode(), user2.hashCode());
	}

	private User setUpUser(Integer id, String firstName, String lastName, String username, String email, String role,
			String password, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
		User user = new User();
		user.setUserId(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setRole(role);
		user.setPassword(password);
		user.setStatus(status);
		user.setCreatedAt(createdAt);
		user.setUpdatedAt(updatedAt);
		return user;
	}
}
