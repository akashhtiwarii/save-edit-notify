package com.example.cd_create_edit_save.controller;

import com.example.cd_create_edit_save.model.dto.ProductRequestInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductResponseOutDto;
import com.example.cd_create_edit_save.constants.AppConstants;
import com.example.cd_create_edit_save.model.dto.inDto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.inDto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductSummaryOutDTO;
import com.example.cd_create_edit_save.service.ProductService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.time.Instant;

import static com.example.cd_create_edit_save.constants.AppConstants.GET_PRODUCTS;
import static com.example.cd_create_edit_save.constants.AppConstants.EXPORT_PRODUCTS;
import static com.example.cd_create_edit_save.constants.AppConstants.GET_PRODUCTS_BY_PARAMETER;

/**
 * REST controller that manages product-related operations such as
 * creation, update, retrieval, filtering, and export.
 *
 * <p>All endpoints are available under {@link AppConstants#PRODUCT_API_BASE_PATH}.</p>
 *
 * <p>Responsibilities:</p>
 * <ul>
 *     <li>Handle incoming product API requests.</li>
 *     <li>Delegate business logic to {@link ProductService}.</li>
 *     <li>Return standardized API responses using {@link ApiResponseOutDto}.</li>
 * </ul>
 */
@RestController
@RequestMapping(AppConstants.PRODUCT_API_BASE_PATH)
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    /** Service layer responsible for product business logic. */
    private final ProductService productService;

    /**
     * Retrieves a paginated list of all products.
     *
     * @param offset optional pagination offset
     * @param limit  optional pagination limit
     * @return list of products wrapped in {@link ApiResponseOutDto}
     */
    @GetMapping(GET_PRODUCTS)
    public ResponseEntity<ApiResponseOutDto<List<ProductResponseOutDto>>> getProducts(
            @RequestParam(required = false) Long offset,
            @RequestParam(required = false) Long limit) {

        ApiResponseOutDto<List<ProductResponseOutDto>> products = productService.getProducts(offset, limit);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Retrieves products filtered by provided parameters.
     *
     * @param request the filtering parameters such as APR range, status, etc.
     * @return filtered list of products wrapped in {@link ApiResponseOutDto}
     */
    @PostMapping(GET_PRODUCTS_BY_PARAMETER)
    public ResponseEntity<ApiResponseOutDto<Map<String, Object>>> getProductsByParameters(
            @Valid @RequestBody ProductRequestInDto request) {

        ApiResponseOutDto<Map<String, Object>> products = productService.getProductByParameters(
                request.getText(), request.getMin_apr(), request.getMax_apr(),
                request.getStatus(), request.getOffset(), request.getLimit());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Exports all products to a CSV file for download.
     *
     * @return the CSV file as an {@link InputStreamResource}
     */
    @GetMapping(EXPORT_PRODUCTS)
    public ResponseEntity<InputStreamResource> exportProductsToCsv() {
        InputStreamResource file = new InputStreamResource(productService.exportProductsToCsv());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }

    /**
     * Creates a new product record.
     *
     * @param requestDto the product creation request data
     * @param createdBy  the user or system initiating the creation
     * @return details of the created product wrapped in {@link ApiResponseOutDto}
     */
    @PostMapping
    public ResponseEntity<ApiResponseOutDto<ProductOutDto>> createProduct(
            @Valid @RequestBody ProductCreateInDto requestDto,
            @RequestHeader(value = "X-Created-By", required = false, defaultValue = "SYSTEM") String createdBy) {

        log.info("Received product creation request from user: {}", createdBy);

        ProductOutDto responseData = productService.createProduct(requestDto, createdBy);

        ApiResponseOutDto<ProductOutDto> response = ApiResponseOutDto.<ProductOutDto>builder()
                .status("SUCCESS")
                .message("Product created successfully with ID: " + responseData.getProductId())
                .data(responseData)
                .timestamp(Instant.now())
                .build();

        log.info("Product creation successful. Product ID: {}", responseData.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates an existing product identified by its ID.
     *
     * @param productId  the ID of the product to be updated
     * @param requestDto the updated product details
     * @param updatedBy  the user or system initiating the update
     * @return updated product details wrapped in {@link ApiResponseOutDto}
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponseOutDto<ProductOutDto>> updateProduct(
            @PathVariable String productId,
            @Valid @RequestBody ProductUpdateInDto requestDto,
            @RequestHeader(value = "X-Updated-By", required = false, defaultValue = "SYSTEM") String updatedBy) {

        log.info("Received product update request for Product ID: {} from user: {}", productId, updatedBy);

        ProductOutDto responseData = productService.updateProduct(productId, requestDto, updatedBy);

        ApiResponseOutDto<ProductOutDto> response = ApiResponseOutDto.<ProductOutDto>builder()
                .status("SUCCESS")
                .message("Product updated successfully. Old ID: " + productId + ", New ID: " + responseData.getProductId())
                .data(responseData)
                .timestamp(Instant.now())
                .build();

        log.info("Product update successful. Old ID: {}, New ID: {}", productId, responseData.getProductId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Retrieves product details by product ID.
     *
     * @param productId the unique product identifier
     * @return product details wrapped in {@link ApiResponseOutDto}
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponseOutDto<ProductOutDto>> getProductById(@PathVariable String productId) {
        log.info("Received request to fetch product with ID: {}", productId);

        ProductOutDto productOutDto = productService.getProductById(productId);

        ApiResponseOutDto<ProductOutDto> response = ApiResponseOutDto.<ProductOutDto>builder()
                .status("SUCCESS")
                .message("Product retrieved successfully")
                .data(productOutDto)
                .timestamp(Instant.now())
                .build();

        log.info("Successfully processed request for product ID: {}", productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves summarized statistics or insights for all products.
     *
     * @return summary details wrapped in {@link ApiResponseOutDto}
     */
    @GetMapping("/summary")
    public ResponseEntity<ApiResponseOutDto<ProductSummaryOutDTO>> getProductSummary() {
        log.info("Request to get Product Summary");
        try {
            ProductSummaryOutDTO summary = productService.getProductSummary();

            ApiResponseOutDto<ProductSummaryOutDTO> response = ApiResponseOutDto.<ProductSummaryOutDTO>builder()
                    .status("SUCCESS")
                    .message("Product summary retrieved successfully.")
                    .data(summary)
                    .timestamp(Instant.now())
                    .build();

            log.info("Successfully fetched Product Summary");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error retrieving Product Summary: {}", e.getMessage(), e);

            ApiResponseOutDto<ProductSummaryOutDTO> errorResponse = ApiResponseOutDto.<ProductSummaryOutDTO>builder()
                    .status("FAILURE")
                    .message("Failed to retrieve product summary: " + e.getMessage())
                    .timestamp(Instant.now())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
