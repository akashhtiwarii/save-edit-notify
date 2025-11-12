package com.example.cd_create_edit_save.model.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

	@Test
	void testSettersAndGetters() {
		User user = new User();

		Integer userId = 1;
		String firstName = "John";
		String lastName = "Doe";
		String username = "johndoe";
		String email = "john.doe@example.com";
		String role = "ADMIN";
		String password = "encrypted123";
		String status = "ACTIVE";
		String createdBy = "System";
		LocalDateTime createdDatetime = LocalDateTime.of(2024, 1, 1, 12, 0);
		String updatedBy = "Admin";
		LocalDateTime updatedDatetime = LocalDateTime.of(2024, 2, 1, 12, 0);

		// Set values
		user.setUserId(userId);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setRole(role);
		user.setPassword(password);
		user.setStatus(status);
		user.setCreatedBy(createdBy);
		user.setCreatedDatetime(createdDatetime);
		user.setUpdatedBy(updatedBy);
		user.setUpdatedDatetime(updatedDatetime);

		// Verify getters
		assertEquals(userId, user.getUserId());
		assertEquals(firstName, user.getFirstName());
		assertEquals(lastName, user.getLastName());
		assertEquals(username, user.getUsername());
		assertEquals(email, user.getEmail());
		assertEquals(role, user.getRole());
		assertEquals(password, user.getPassword());
		assertEquals(status, user.getStatus());
		assertEquals(createdBy, user.getCreatedBy());
		assertEquals(createdDatetime, user.getCreatedDatetime());
		assertEquals(updatedBy, user.getUpdatedBy());
		assertEquals(updatedDatetime, user.getUpdatedDatetime());
	}

	@Test
	void testDefaultStatus() {
		User user = new User();
		// Lombok @Builder.Default ensures default value is ACTIVE
		assertEquals("ACTIVE", user.getStatus());
	}

	@Test
	void testPreUpdate() throws InterruptedException {
		User user = new User();
		user.setUpdatedDatetime(LocalDateTime.now());
		LocalDateTime beforeUpdate = user.getUpdatedDatetime();

		Thread.sleep(10); // ensure timestamp difference
		user.preUpdate();

		assertNotNull(user.getUpdatedDatetime());
		assertTrue(user.getUpdatedDatetime().isAfter(beforeUpdate));
	}

	@Test
	void testBuilderAndToString() {
		LocalDateTime now = LocalDateTime.now();

		User user = User.builder()
				.userId(1)
				.firstName("John")
				.lastName("Doe")
				.username("johndoe")
				.email("john.doe@example.com")
				.role("ADMIN")
				.password("encrypted123")
				.status("ACTIVE")
				.createdBy("System")
				.createdDatetime(now)
				.updatedBy("Admin")
				.updatedDatetime(now)
				.build();

		assertEquals(1, user.getUserId());
		assertEquals("John", user.getFirstName());
		assertEquals("Doe", user.getLastName());
		assertEquals("johndoe", user.getUsername());
		assertEquals("john.doe@example.com", user.getEmail());
		assertEquals("ADMIN", user.getRole());
		assertEquals("encrypted123", user.getPassword());
		assertEquals("ACTIVE", user.getStatus());
		assertEquals("System", user.getCreatedBy());
		assertEquals("Admin", user.getUpdatedBy());
		assertEquals(now, user.getCreatedDatetime());
		assertEquals(now, user.getUpdatedDatetime());

		// Lombok-generated toString should contain identifying fields
		String toString = user.toString();
		assertNotNull(toString);
		assertTrue(toString.contains("John"));
		assertTrue(toString.contains("johndoe"));
	}

	@Test
	void testEqualsAndHashCode() {
		LocalDateTime now = LocalDateTime.now();

		User user1 = createSampleUser(1, "John", "Doe", "johndoe", "john.doe@example.com", "ADMIN", now);
		User user2 = createSampleUser(1, "John", "Doe", "johndoe", "john.doe@example.com", "ADMIN", now);

		assertEquals(user1, user2);
		assertEquals(user1.hashCode(), user2.hashCode());

		// Different userId
		user2.setUserId(2);
		assertNotEquals(user1, user2);

		// Different username
		user2 = createSampleUser(1, "John", "Doe", "janedoe", "john.doe@example.com", "ADMIN", now);
		assertNotEquals(user1, user2);

		// Different email
		user2 = createSampleUser(1, "John", "Doe", "johndoe", "different@example.com", "ADMIN", now);
		assertNotEquals(user1, user2);

		// Type and null checks
		assertNotEquals(user1, null);
		assertNotEquals(user1, new Object());

		// Empty objects equality
		User empty1 = new User();
		User empty2 = new User();
		assertEquals(empty1, empty2);
		assertEquals(empty1.hashCode(), empty2.hashCode());
	}

	private User createSampleUser(Integer id, String firstName, String lastName, String username, String email,
								  String role, LocalDateTime now) {
		return User.builder()
				.userId(id)
				.firstName(firstName)
				.lastName(lastName)
				.username(username)
				.email(email)
				.role(role)
				.password("pass")
				.status("ACTIVE")
				.createdBy("System")
				.createdDatetime(now)
				.updatedBy("Admin")
				.updatedDatetime(now)
				.build();
	}
}
