package com.example.cd_create_edit_save.repository;

import com.example.cd_create_edit_save.model.entity.FeeValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeValuesRepository extends JpaRepository<FeeValues, Long> {

    /**
     * Check if fee value exists for given fee type
     * @param feeValue the fee amount
     * @param feeType the fee type (ANNUAL or MONTHLY)
     * @return true if exists, false otherwise
     */
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END " +
            "FROM product.TBL_FEE_VALUES " +
            "WHERE FEE_VALUE = :feeValue AND FEE_TYPE = :feeType",
            nativeQuery = true)
    boolean existsByFeeValueAndFeeType(@Param("feeValue") Integer feeValue,
                                       @Param("feeType") String feeType);
}
