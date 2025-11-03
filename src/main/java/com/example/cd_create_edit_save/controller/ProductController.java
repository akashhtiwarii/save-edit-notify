package com.example.cd_create_edit_save.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.cd_create_edit_save.constants.AppConstants;
import com.example.cd_create_edit_save.model.dto.in.ProductInDTO;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.service.ProductService;
import com.example.cd_create_edit_save.validator.ProductValidatorService;

import jakarta.validation.Valid;

/**
 * REST Controller for Product operations. Provides endpoints for managing and
 * creating products.
 */
@RestController
@RequestMapping(AppConstants.API_V1_PRODUCTS)
@RequiredArgsConstructor
@Slf4j
@Validated
public class ProductController {

	private final ProductService productService;
	private final ProductValidatorService productValidator;

	/**
	 * Creates a new product. Validates the input data and creates a product in the
	 * system.
	 *
	 * @param productInDTO The product data to create (validated)
	 * @return ResponseEntity containing ApiResponseOutDto with created product
	 *         details and HTTP 201 status
	 */
	@PostMapping
	public ResponseEntity<ApiResponseOutDto<Void>> createProduct(@Valid @RequestBody ProductInDTO productInDTO) {

		log.info("Received request to create product with ID: {}", productInDTO.getProductShtCd());
		productValidator.validateProduct(productInDTO);
		ApiResponseOutDto<Void> response = productService.createProduct(productInDTO);

		log.info("Successfully processed product creation for ID: {}", productInDTO.getProductShtCd());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * Get product by ID
	 *
	 * @param productId the product ID
	 * @return ResponseEntity containing product details
	 */
	@GetMapping("/{productId}")
	public ResponseEntity<ApiResponseOutDto<ProductOutDto>> getProductById(@PathVariable String productId) {

		log.info("Received request to fetch product with ID: {}", productId);

		ProductOutDto productOutDto = productService.getProductById(productId);

		ApiResponseOutDto<ProductOutDto> response = ApiResponseOutDto.<ProductOutDto>builder().status("success")
				.message("Product retrieved successfully").data(productOutDto).timestamp(Instant.now()).build();

		log.info("Successfully processed request for product ID: {}", productId);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}