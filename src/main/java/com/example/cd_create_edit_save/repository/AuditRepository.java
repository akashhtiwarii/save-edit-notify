package com.example.cd_create_edit_save.repository;

import com.example.cd_create_edit_save.model.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Integer> {
}
