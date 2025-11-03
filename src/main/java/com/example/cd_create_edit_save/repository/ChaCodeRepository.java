package com.example.cd_create_edit_save.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cd_create_edit_save.model.entity.ChaCode;

/**
 * Repository interface for ChaCode entity. Provides database operations for
 * channel codes.
 *
 * @author Krishna
 * @version 1.0
 */
@Repository
public interface ChaCodeRepository extends JpaRepository<ChaCode, String> {

	/**
     * Checks whether a channel code exists in the database.
     *
     * @param chaCode the channel code to check
     * @return true if the channel code exists, false otherwise
     */
    boolean existsByChaCode(String chaCode);
}