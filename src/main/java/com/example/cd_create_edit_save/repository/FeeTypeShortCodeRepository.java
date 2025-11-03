package com.example.cd_create_edit_save.repository;

import com.example.cd_create_edit_save.model.entity.FeeTypeShortCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeTypeShortCodeRepository extends JpaRepository<FeeTypeShortCode, String> {
	
	/**
     * Checks whether a Fee Type Short Code exists in the database.
     *
     * @param feeTypeShortCode the fee type short code to check
     * @return true if the code exists, false otherwise
     */
    boolean existsByFeeTypeShortCode(String feeTypeShortCode);
}
