package com.example.cd_create_edit_save.service;

import com.example.cd_create_edit_save.model.dto.inDto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.inDto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductSummaryOutDTO;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

/**
 * Service interface defining operations related to product management.
 *
 * <p>This interface provides methods for creating, updating, retrieving, and exporting
 * product details. Implementations of this service handle business logic and
 * interaction with the data layer.</p>
 */
public interface ProductService {

    /**
     * Creates a new product with an auto-generated product ID.
     *
     * @param requestDto the product creation request data
     * @param createdBy  the username or identifier of the user creating the product
     * @return {@link ProductOutDto} containing details of the created product
     * @throws com.example.cd_create_edit_save.exception.InvalidRequestException if validation fails
     */
    ProductOutDto createProduct(ProductCreateInDto requestDto, String createdBy);

    /**
     * Retrieves a paginated list of products.
     *
     * @param offset the starting point for pagination
     * @param limit  the maximum number of records to return
     * @return {@link ApiResponseOutDto} containing a list of {@link ProductResponseOutDto}
     */
    ApiResponseOutDto<List<ProductResponseOutDto>> getProducts(Long offset, Long limit);

    /**
     * Updates an existing product identified by its ID.
     *
     * @param productId the unique identifier of the product to update
     * @param requestDto the product update request data
     * @param updatedBy the username or identifier of the user updating the product
     * @return {@link ProductOutDto} containing the updated product details
     */
    ProductOutDto updateProduct(String productId, ProductUpdateInDto requestDto, String updatedBy);

    /**
     * Retrieves products based on given filter parameters such as APR, status, and search text.
     *
     * @param text     a search keyword for filtering product names or codes
     * @param min_apr  minimum APR value for filtering
     * @param max_apr  maximum APR value for filtering
     * @param status   product status filter (e.g., ACTIVE, INACTIVE)
     * @param offset   pagination offset
     * @param limit    pagination limit
     * @return {@link ApiResponseOutDto} containing filtered product data
     */
    ApiResponseOutDto<Map<String, Object>> getProductByParameters(
            String text, Double min_apr, Double max_apr, String status, Long offset, Long limit);

    /**
     * Retrieves a product by its unique ID.
     *
     * @param productId the unique identifier of the product
     * @return {@link ProductOutDto} containing product details
     */
    ProductOutDto getProductById(String productId);

    /**
     * Exports all products to a CSV format.
     *
     * @return {@link ByteArrayInputStream} representing the CSV data stream
     */
    ByteArrayInputStream exportProductsToCsv();

    /**
     * Retrieves a summary of product-related statistics or metrics.
     *
     * @return {@link ProductSummaryOutDTO} containing summarized product information
     */
    ProductSummaryOutDTO getProductSummary();
}
