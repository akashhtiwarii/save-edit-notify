package com.example.cd_create_edit_save.service;

import com.example.cd_create_edit_save.model.dto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductCreateOutDto;

/**
 * Service interface for Product management operations
 */
public interface ProductService {

    /**
     * Creates a new product with auto-generated product ID
     *
     * @param requestDto The product creation request data
     * @param createdBy The username of the user creating the product
     * @return ProductCreateOutDto containing the created product details
     * @throws com.example.cd_create_edit_save.exception.InvalidRequestException if validation fails
     */
    ProductCreateOutDto createProduct(ProductCreateInDto requestDto, String createdBy);

    ProductCreateOutDto updateProduct(String productId, ProductUpdateInDto requestDto, String updatedBy);
}