package com.example.cd_create_edit_save.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cd_create_edit_save.model.entity.FeeValues;

/**
 * Repository interface for {@link FeeValue} entity.
 * 
 * @author Krishna
 * @version 1.0
 */
@Repository
public interface FeeValueRepository extends JpaRepository<FeeValues, Long> {

	/**
	 * Finds all fee values by fee type.
	 * 
	 * @param feeType the type of fee (ANNUAL or MONTHLY)
	 * @return a list of fee values matching the specified fee type
	 */
	List<FeeValues> findByFeeType(String feeType);
}