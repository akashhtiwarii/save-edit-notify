package com.example.cd_create_edit_save.controller;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("api/products/")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    /**
     * Get product by ID
     *
     * @param productId the product ID
     * @return ResponseEntity containing product details
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponseOutDto<ProductOutDto>> getProductById(
            @PathVariable String productId) {

        log.info("Received request to fetch product with ID: {}", productId);

        ProductOutDto productOutDto = productService.getProductById(productId);

        ApiResponseOutDto<ProductOutDto> response = ApiResponseOutDto.<ProductOutDto>builder()
                .status("success")
                .message("Product retrieved successfully")
                .data(productOutDto)
                .timestamp(Instant.now())
                .build();

        log.info("Successfully processed request for product ID: {}", productId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}