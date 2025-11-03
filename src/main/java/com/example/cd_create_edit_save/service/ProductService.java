package com.example.cd_create_edit_save.service;

import com.example.cd_create_edit_save.model.dto.in.ProductInDTO;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;

/**
 * Service interface for Product operations.
 * Defines business logic contract for managing products.
 */
public interface ProductService {

    /**
     * Creates a new product in the system.
     * Validates the product data and ensures no duplicate product IDs exist.
     *
     * @param productInDTO The product data to create
     * @return ApiResponseOutDto containing the created product details with status and timestamp
     */
    ApiResponseOutDto<Void> createProduct(ProductInDTO productInDTO);
    
    ProductOutDto getProductById(String productId);

}
