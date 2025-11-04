package com.example.cd_create_edit_save.service.serviceImpl;

import com.example.cd_create_edit_save.exception.ResourceNotFoundException;
import com.example.cd_create_edit_save.mapper.ProductMapper;
import com.example.cd_create_edit_save.model.dto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductCreateOutDto;
import com.example.cd_create_edit_save.model.entity.Product;
import com.example.cd_create_edit_save.repository.ProductRepository;
import com.example.cd_create_edit_save.service.ProductService;
import com.example.cd_create_edit_save.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductValidator productRequestValidator;

    @Override
    @Transactional
    public ProductCreateOutDto createProduct(ProductCreateInDto requestDto, String createdBy) {
        log.info("Starting product creation for category: {}, fee: {}, rewards: {}",
                requestDto.getProductShtCd(), requestDto.getFeeTypeShtCd(), requestDto.getRewardsTypeShtCd());

        productRequestValidator.validateProductCreateRequest(requestDto);

        String productId = generateProductId(requestDto.getProductShtCd(),
                requestDto.getFeeTypeShtCd(), requestDto.getRewardsTypeShtCd());
        log.info("Generated product ID: {}", productId);

        Product product = productMapper.toEntity(requestDto, productId, createdBy);
        Product savedProduct = productRepository.save(product);
        log.info("Product saved successfully with ID: {}", productId);

        ProductCreateOutDto response = productMapper.toResponseDto(savedProduct);
        log.info("Product created successfully. Product ID: {}", productId);

        return response;
    }

    @Override
    @Transactional
    public ProductCreateOutDto updateProduct(String productId, ProductUpdateInDto requestDto, String updatedBy) {
        log.info("Starting product update for Product ID: {}", productId);

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", productId);
                    return new ResourceNotFoundException("Product not found with ID: " + productId);
                });
        log.info("Found existing product: {}", existingProduct.getProductId());

        productRequestValidator.validateNonEditableFields(existingProduct, requestDto);

        productRequestValidator.validateProductUpdateRequest(requestDto);

        String newProductId = generateProductId(requestDto.getProductShtCd(),
                requestDto.getFeeTypeShtCd(), requestDto.getRewardsTypeShtCd());
        log.info("Generated new product ID: {}", newProductId);

        Product newProduct = productMapper.toEntity(requestDto, newProductId, updatedBy);
        Product savedProduct = productRepository.save(newProduct);
        log.info("Product version saved successfully with ID: {}", newProductId);

        ProductCreateOutDto response = productMapper.toResponseDto(savedProduct);
        log.info("Product updated successfully. Old ID: {}, New ID: {}", productId, newProductId);

        return response;
    }

    /**
     * Generate product ID in format: PRODUCT-FEE-REWARDS-0000001
     */
    private String generateProductId(String productShtCd, String feeTypeShtCd, String rewardsTypeShtCd) {
        String prefix = String.format("%s-%s-%s-", productShtCd, feeTypeShtCd, rewardsTypeShtCd);
        log.info("Searching for latest product with prefix: {}", prefix);

        String latestProductId = productRepository.findLatestProductIdByPrefix(prefix).orElse(null);

        int nextSequence = 1;

        if (latestProductId != null) {
            log.info("Found latest product ID: {}", latestProductId);
            String sequencePart = latestProductId.substring(latestProductId.length() - 7);
            try {
                nextSequence = Integer.parseInt(sequencePart) + 1;
                log.info("Extracted sequence: {}, next sequence: {}", sequencePart, nextSequence);
            } catch (NumberFormatException e) {
                log.warn("Unable to parse sequence from product ID: {}. Using sequence 1", latestProductId);
                nextSequence = 1;
            }
        } else {
            log.info("No existing product found with prefix: {}. Starting with sequence 1", prefix);
        }

        String generatedProductId = prefix + String.format("%07d", nextSequence);
        log.info("Generated product ID: {}", generatedProductId);

        return generatedProductId;
    }
}