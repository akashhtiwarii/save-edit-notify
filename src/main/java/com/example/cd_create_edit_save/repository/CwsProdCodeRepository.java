package com.example.cd_create_edit_save.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cd_create_edit_save.model.entity.CwsProdCode;

/**
 * Repository interface for CwsProdCode entity. Provides CRUD operations for CWS
 * product codes.
 */
@Repository
public interface CwsProdCodeRepository extends JpaRepository<CwsProdCode, String> {
	
	/**
     * Checks whether a CWS product code exists in the database.
     *
     * @param cwsProdCode the CWS product code to check
     * @return true if the product code exists, false otherwise
     */
    boolean existsByCwsProdCode(String cwsProdCode);
}