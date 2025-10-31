package com.example.cd_create_edit_save.repository;

import com.example.cd_create_edit_save.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(value = "SELECT p.product_id FROM tbl_product p " +
            "WHERE p.product_id LIKE :prefix || '%' " +
            "ORDER BY p.product_id DESC LIMIT 1", nativeQuery = true)
    Optional<String> findLatestProductIdByPrefix(@Param("prefix") String prefix);
}
