package com.example.cd_create_edit_save.repository;

import com.example.cd_create_edit_save.model.entity.Audit;
import com.example.cd_create_edit_save.model.entity.FeeTypeShortCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeTypeShortCodeRepository extends JpaRepository<FeeTypeShortCode, String> {
}
