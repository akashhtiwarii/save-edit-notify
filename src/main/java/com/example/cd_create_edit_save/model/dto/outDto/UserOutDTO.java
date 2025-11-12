package com.example.cd_create_edit_save.model.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for User information output. Used to transfer user data
 * between layers without exposing sensitive information.
 * 
 * @author Krishna
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOutDTO {

	/**
	 * Unique identifier of the user.
	 */
	private Integer userId;

	/**
	 * First name of the user.
	 */
	private String name;

	/**
	 * Role assigned to the user (e.g., ADMIN, USER).
	 */
	private String role;

    /**
     * User name of the user.
     */
    private String userName;

    /**
     * Email of the user.
     */
    private String email;
}