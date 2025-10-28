package com.example.cd_create_edit_save.repository;

import com.example.cd_create_edit_save.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
