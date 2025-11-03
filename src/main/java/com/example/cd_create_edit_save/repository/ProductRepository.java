package com.example.cd_create_edit_save.repository;

import com.example.cd_create_edit_save.model.entity.Product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Product entity. Provides CRUD operations for product
 * management.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	/**
	 * Checks if a product exists with the given product ID.
	 *
	 * @param productId The product ID to check
	 * @return true if product exists, false otherwise
	 */
	boolean existsByProductId(String productId);

	/**
	 * Finds a product by its product ID.
	 *
	 * @param productId The product ID to search for
	 * @return Optional containing the product if found
	 */
	Optional<Product> findByProductId(String productId);
	
    /**
     * Counts the number of products with a given prefix combination
     * This is used to determine the next sequence number for product ID generation
     * 
     * @param prefix The prefix combination.
     * @return Count of products with the given prefix
     */
    @Query("SELECT COUNT(p) FROM Product p " +
           "WHERE p.productId LIKE CONCAT(:prefix, '-%')")
    long countByFinalProductIdPrefix(@Param("prefix") String prefix);
}