package com.example.cd_create_edit_save.model.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Product Short Code.
 *
 * @author Krishna
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductShortCodeOutDTO {

	/**
	 * The short code identifier for the product.
	 */
	private String productShortCode;

	/**
	 * The full name of the product.
	 */
	private String product;
}