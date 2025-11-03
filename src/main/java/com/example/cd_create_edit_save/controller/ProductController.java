package com.example.cd_create_edit_save.controller;


import com.example.cd_create_edit_save.model.dto.ProductCreateInDto;

import com.example.cd_create_edit_save.model.dto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductCreateOutDto;
import com.example.cd_create_edit_save.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/products/create")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponseOutDto<ProductCreateOutDto>> createProduct(
            @Valid @RequestBody ProductCreateInDto requestDto,
            @RequestHeader(value = "X-Created-By", required = false, defaultValue = "SYSTEM") String createdBy) {

        log.info("Received product creation request from user: {}", createdBy);

        ProductCreateOutDto responseData = productService.createProduct(requestDto, createdBy);

        ApiResponseOutDto<ProductCreateOutDto> response = ApiResponseOutDto.<ProductCreateOutDto>builder()
                .status("success")
                .message("Product created successfully with ID: " + responseData.getProductId())
                .data(responseData)
                .timestamp(Instant.now())
                .build();

        log.info("Product creation successful. Product ID: {}", responseData.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponseOutDto<ProductCreateOutDto>> updateProduct(
            @PathVariable String productId,
            @Valid @RequestBody ProductUpdateInDto requestDto,
            @RequestHeader(value = "X-Updated-By", required = false, defaultValue = "SYSTEM") String updatedBy) {

        log.info("Received product update request for Product ID: {} from user: {}", productId, updatedBy);


        ProductCreateOutDto responseData = productService.updateProduct(productId, requestDto, updatedBy);

        ApiResponseOutDto<ProductCreateOutDto> response = ApiResponseOutDto.<ProductCreateOutDto>builder()
                .status("success")
                .message("Product updated successfully. Old ID: " + productId + ", New ID: " + responseData.getProductId())
                .data(responseData)
                .timestamp(Instant.now())
                .build();

        log.info("Product update successful. Old ID: {}, New ID: {}", productId, responseData.getProductId());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}