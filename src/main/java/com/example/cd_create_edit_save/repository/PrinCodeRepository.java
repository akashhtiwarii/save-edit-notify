package com.example.cd_create_edit_save.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cd_create_edit_save.model.entity.PrinCode;

public interface PrinCodeRepository extends JpaRepository<PrinCode, String> {

	/**
	 * Checks whether a principal code exists in the database.
	 *
	 * @param prinCode the principal code to check
	 * @return true if the code exists, false otherwise
	 */
	boolean existsByPrinCode(String prinCode);
}
