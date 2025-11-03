package com.example.cd_create_edit_save.repository;

import com.example.cd_create_edit_save.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    /**
     * Finds the latest (highest sequence) product ID with the given prefix
     * Used for generating the next sequential product ID
     *
     * @param prefix The product ID prefix (e.g., "GOL-AF-CB-")
     * @return Optional containing the latest product ID, or empty if none found
     */
    @Query(value = "SELECT p.product_id FROM tbl_product p " +
            "WHERE p.product_id LIKE :prefix || '%' " +
            "ORDER BY p.product_id DESC LIMIT 1", nativeQuery = true)
    Optional<String> findLatestProductIdByPrefix(@Param("prefix") String prefix);
}
