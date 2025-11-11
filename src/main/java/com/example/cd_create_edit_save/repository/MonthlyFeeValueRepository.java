package com.example.cd_create_edit_save.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cd_create_edit_save.model.entity.MonthlyFeeValue;

/**
 * Repository interface for {@link MonthlyFeeValue} entity.
 * 
 * @author Krishna
 * @version 1.0
 */
@Repository
public interface MonthlyFeeValueRepository extends JpaRepository<MonthlyFeeValue, Long> {

}