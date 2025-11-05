package com.example.cd_create_edit_save.service;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductResponseOutDto;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import com.example.cd_create_edit_save.model.dto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;

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
    ProductOutDto createProduct(ProductCreateInDto requestDto, String createdBy);

    ApiResponseOutDto<List<ProductResponseOutDto>> getProducts(Long offset , Long Limit);

    ProductOutDto updateProduct(String productId, ProductUpdateInDto requestDto, String updatedBy);

    ApiResponseOutDto<Map<String, Object>> getProductByParameters(String text, Double min_apr, Double max_apr, String status, Long offset, Long limit);

    ProductOutDto getProductById(String productId);
    ByteArrayInputStream exportProductsToCsv();
}
