package com.example.cd_create_edit_save.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cd_create_edit_save.model.entity.PrinCode;

public interface PrinCodeRepository extends JpaRepository<PrinCode, String> {
}
