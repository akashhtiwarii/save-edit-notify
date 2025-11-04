package com.example.cd_create_edit_save.repository;

import com.example.cd_create_edit_save.model.entity.ProductShortCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductShortCodeRepository extends JpaRepository<ProductShortCode, String> {

	/**
	 * Checks whether a product short code exists in the database.
	 *
	 * @param productShortCode the product short code to check
	 * @return true if the product short code exists, false otherwise
	 */
	boolean existsByProductShortCode(String productShortCode);
}
