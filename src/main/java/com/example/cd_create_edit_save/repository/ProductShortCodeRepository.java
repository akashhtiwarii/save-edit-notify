package com.example.cd_create_edit_save.repository;

import com.example.cd_create_edit_save.model.entity.ProductShortCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductShortCodeRepository extends JpaRepository<ProductShortCode, String> {
}
