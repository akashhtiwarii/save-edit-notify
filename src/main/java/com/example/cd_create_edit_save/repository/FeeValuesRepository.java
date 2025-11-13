package com.example.cd_create_edit_save.repository;

import com.example.cd_create_edit_save.model.entity.FeeValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FeeValuesRepository extends JpaRepository<FeeValues, Long> {

    /**
     * Check if fee value exists for given fee type
     * @param feeValue the fee amount
     * @param feeType the fee type (ANNUAL or MONTHLY)
     * @return true if exists, false otherwise
     */
    /**
     * Check if fee value exists for given fee type
     * @param feeValue the fee amount
     * @param feeType the fee type (ANNUAL or MONTHLY)
     * @return true if exists, false otherwise
     */
    boolean existsByFeeValueAndFeeType(BigDecimal feeValue, String feeType);
}

