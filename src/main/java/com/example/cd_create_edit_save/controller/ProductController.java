package com.example.cd_create_edit_save.controller;


import com.example.cd_create_edit_save.model.dto.ProductRequestInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductResponseOutDto;
import com.example.cd_create_edit_save.constants.AppConstants;
import com.example.cd_create_edit_save.model.dto.inDto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.inDto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.ProductCreateInDto;

import com.example.cd_create_edit_save.model.dto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductSummaryOutDTO;
import com.example.cd_create_edit_save.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.time.Instant;

import static com.example.cd_create_edit_save.constants.AppConstants.GET_PRODUCTS;
import static com.example.cd_create_edit_save.constants.AppConstants.EXPORT_PRODUCTS;
import static com.example.cd_create_edit_save.constants.AppConstants.GET_PRODUCTS_BY_PARAMETER;


/**
 * {@code ProductController} is a REST controller responsible for handling product-related operations
 * such as creation, update, and retrieval by product ID.
 * <p>
 * It provides endpoints under the base path defined in {@link AppConstants#PRODUCT_API_BASE_PATH}.
 * Each operation returns a structured API response wrapped in {@link ApiResponseOutDto}.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *     <li>Accept and validate product creation and update requests.</li>
 *     <li>Delegate business logic to {@link ProductService}.</li>
 *     <li>Return standardized success or error responses.</li>
 * </ul>
 *
 * <p><b>Annotations:</b></p>
 * <ul>
 *     <li>{@link RestController} - Marks this class as a Spring REST controller.</li>
 *     <li>{@link RequestMapping} - Defines the base URL for all endpoints.</li>
 *     <li>{@link RequiredArgsConstructor} - Automatically generates a constructor for final fields.</li>
 *     <li>{@link Slf4j} - Enables logging via the SLF4J API.</li>
 * </ul>
 *
 */
@RestController
@RequestMapping(AppConstants.PRODUCT_API_BASE_PATH)
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    /**
     * Service layer dependency responsible for handling business logic
     * related to product creation, updating, and retrieval.
     */
    private final ProductService productService;

    /**\
     * Get Product List .
     * @param offset
     * @param limit
     * @return List of productresponseout dto.
     */
    @GetMapping(GET_PRODUCTS)
    public ResponseEntity<ApiResponseOutDto<List<ProductResponseOutDto>>> getProducts(@RequestParam(required = false ) Long offset , @RequestParam(required = false ) Long limit) {
        ApiResponseOutDto<List<ProductResponseOutDto>> products = productService.getProducts(offset, limit);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    /**
     * Get product list filtered by parameters.
     * @param request productrequest dto.
     * @return list or productresponseout dto.
     */
    @GetMapping(GET_PRODUCTS_BY_PARAMETER)
    public ResponseEntity<ApiResponseOutDto<Map<String, Object>>> getProductsByParameters(
            @Valid @RequestBody ProductRequestInDto request
    ) {
        ApiResponseOutDto<Map<String , Object>> products = productService.getProductByParameters(request.getText(),
                request.getMin_apr(), request.getMax_apr(), request.getStatus(),
                request.getOffset(), request.getLimit());
        return new ResponseEntity<>(products, HttpStatus.OK);

    }


    /**
     * Get csv for the product list.
     * @return file.
     */
    @GetMapping(EXPORT_PRODUCTS)
    public ResponseEntity<InputStreamResource> exportProductsToCsv() {
        InputStreamResource file = new InputStreamResource(productService.exportProductsToCsv());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }



    @PostMapping
    public ResponseEntity<ApiResponseOutDto<ProductOutDto>> createProduct(
            @Valid @RequestBody ProductCreateInDto requestDto,
            @RequestHeader(value = "X-Created-By", required = false, defaultValue = "SYSTEM") String createdBy) {

        log.info("Received product creation request from user: {}", createdBy);

        ProductOutDto responseData = productService.createProduct(requestDto, createdBy);

        ApiResponseOutDto<ProductOutDto> response = ApiResponseOutDto.<ProductOutDto>builder()
                .status("success")
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
     * @param productId  the unique identifier of the product to be updated.
     * @param requestDto the {@link ProductUpdateInDto} containing updated product details.
     * @param updatedBy  the identifier (user or system) who initiated the update request.
     *                   Defaults to "SYSTEM" if not provided.
     * @return a {@link ResponseEntity} containing {@link ApiResponseOutDto} with
     *         updated product details and HTTP status {@code 200 (OK)}.
     *
     * @apiNote This endpoint validates input fields using {@link Valid}.
     * @see ProductService#updateProduct(String, ProductUpdateInDto, String)
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponseOutDto<ProductOutDto>> updateProduct(
            @PathVariable String productId,
            @Valid @RequestBody ProductUpdateInDto requestDto,
            @RequestHeader(value = "X-Updated-By", required = false, defaultValue = "SYSTEM") String updatedBy) {

        log.info("Received product update request for Product ID: {} from user: {}", productId, updatedBy);

        ProductOutDto responseData = productService.updateProduct(productId, requestDto, updatedBy);

        ApiResponseOutDto<ProductOutDto> response = ApiResponseOutDto.<ProductOutDto>builder()
                .status("success")
                .message("Product updated successfully. Old ID: " + productId + ", New ID: " + responseData.getProductId())
                .data(responseData)
                .timestamp(Instant.now())
                .build();

        log.info("Product update successful. Old ID: {}, New ID: {}", productId, responseData.getProductId());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Retrieves a product by its unique ID.
     *
     * @param productId the unique identifier of the product to be retrieved.
     * @return a {@link ResponseEntity} containing {@link ApiResponseOutDto} with
     *         the product details and HTTP status {@code 200 (OK)}.
     *
     * @see ProductService#getProductById(String)
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponseOutDto<ProductOutDto>> getProductById(
            @PathVariable String productId) {

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

    @GetMapping("/summary")
    public ResponseEntity<ApiResponseOutDto<ProductSummaryOutDTO>> getProductSummary() {
        log.info("Request to get Product Summary");
        try {
            ProductSummaryOutDTO summary = productService.getProductSummary();

            ApiResponseOutDto<ProductSummaryOutDTO> response = ApiResponseOutDto.<ProductSummaryOutDTO>builder()
                    .status("success")
                    .message("Product summary retrieved successfully.")
                    .data(summary)
                    .timestamp(Instant.now())
                    .build();

            log.info("Successfully fetched Product Summary");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error retrieving Product Summary: {}", e.getMessage(), e);
            ApiResponseOutDto<ProductSummaryOutDTO> errorResponse = ApiResponseOutDto.<ProductSummaryOutDTO>builder()
                    .status("error")
                    .message("Failed to retrieve product summary: " + e.getMessage())
                    .timestamp(Instant.now())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
