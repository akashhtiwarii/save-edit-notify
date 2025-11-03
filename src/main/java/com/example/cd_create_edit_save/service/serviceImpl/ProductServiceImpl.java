package com.example.cd_create_edit_save.service.serviceImpl;

import com.example.cd_create_edit_save.exception.InvalidRequestException;
import com.example.cd_create_edit_save.exception.ResourceNotFoundException;
import com.example.cd_create_edit_save.mapper.ProductMapper;
import com.example.cd_create_edit_save.model.dto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductCreateOutDto;
import com.example.cd_create_edit_save.model.entity.Product;
import com.example.cd_create_edit_save.repository.ProductRepository;
import com.example.cd_create_edit_save.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private static final BigDecimal CASH_APR_MULTIPLIER = new BigDecimal("1.05");
    private static final BigDecimal TOLERANCE = new BigDecimal("0.01");

    @Override
    @Transactional
    public ProductCreateOutDto createProduct(ProductCreateInDto requestDto, String createdBy) {
        log.info("Starting product creation for category: {}, fee: {}, rewards: {}",
                requestDto.getProductShtCd(), requestDto.getFeeTypeShtCd(), requestDto.getRewardsTypeShtCd());

        long startTime = System.currentTimeMillis();

        try {
            // Perform all validations
            log.info("Validating product request");
            validateProductRequest(requestDto);

            // Generate Product ID
            log.info("Generating product ID");
            String productId = generateProductId(requestDto.getProductShtCd(),
                    requestDto.getFeeTypeShtCd(), requestDto.getRewardsTypeShtCd());
            log.info("Generated product ID: {}", productId);

            // Map DTO to Entity
            Product product = productMapper.toEntity(requestDto, productId, createdBy);

            // Save to database
            log.info("Saving product to database with ID: {}", productId);
            Product savedProduct = productRepository.save(product);
            log.info("Product saved successfully with ID: {}", productId);

            // Map Entity to Response DTO
            ProductCreateOutDto response = productMapper.toResponseDto(savedProduct);

            long endTime = System.currentTimeMillis();
            log.info("Product created successfully in {} ms. Product ID: {}", (endTime - startTime), productId);

            return response;

        } catch (InvalidRequestException e) {
            log.error("Validation failed during product creation: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during product creation: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create product: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public ProductCreateOutDto updateProduct(String productId, ProductUpdateInDto requestDto, String updatedBy) {
        log.info("Starting product update for Product ID: {}", productId);

        long startTime = System.currentTimeMillis();

        try {
            // 1. Find existing product
            log.info("Fetching existing product with ID: {}", productId);
            Product existingProduct = productRepository.findById(productId)
                    .orElseThrow(() -> {
                        log.error("Product not found with ID: {}", productId);
                        return new ResourceNotFoundException("Product not found with ID: " + productId);
                    });

            log.info("Found existing product: {}", existingProduct.getProductId());

            // 2. Validate non-editable fields haven't changed
            log.info("Validating non-editable fields");
            validateNonEditableFields(existingProduct, requestDto);

            // 3. Perform all editable field validations
            log.info("Validating product update request");
            validateProductUpdateRequest(requestDto);

            // 4. Generate new Product ID (increment sequence)
            log.info("Generating new product ID for updated version");
            String newProductId = generateProductId(requestDto.getProductShtCd(),
                    requestDto.getFeeTypeShtCd(), requestDto.getRewardsTypeShtCd());
            log.info("Generated new product ID: {}", newProductId);

            // 5. Map DTO to Entity (creates new product version)
            Product newProduct = productMapper.toEntity(requestDto, newProductId, updatedBy);

            // 6. Save new product version (old product remains as is - NO status change)
            log.info("Saving new product version with ID: {}", newProductId);
            Product savedProduct = productRepository.save(newProduct);
            log.info("Product version saved successfully with ID: {}", newProductId);

            // 7. Map Entity to Response DTO
            ProductCreateOutDto response = productMapper.toResponseDto(savedProduct);

            long endTime = System.currentTimeMillis();
            log.info("Product updated successfully in {} ms. Old ID: {}, New ID: {}",
                    (endTime - startTime), productId, newProductId);

            return response;

        } catch (InvalidRequestException | ResourceNotFoundException e) {
            log.error("Validation failed during product update: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during product update: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update product: " + e.getMessage(), e);
        }
    }

    private void validateNonEditableFields(Product existingProduct, ProductUpdateInDto requestDto) {
        log.info("Checking if non-editable fields have been modified");

        // Product Short Code cannot be changed
        if (!existingProduct.getProductShtCd().equals(requestDto.getProductShtCd())) {
            log.error("Attempt to change Product Short Code from {} to {}",
                    existingProduct.getProductShtCd(), requestDto.getProductShtCd());
            throw new InvalidRequestException(
                    "Product Short Code cannot be changed. Original: " + existingProduct.getProductShtCd() +
                            ", Attempted: " + requestDto.getProductShtCd()
            );
        }

        // Fee Type Short Code cannot be changed
        if (!existingProduct.getFeeTypeShtCd().equals(requestDto.getFeeTypeShtCd())) {
            log.error("Attempt to change Fee Type Short Code from {} to {}",
                    existingProduct.getFeeTypeShtCd(), requestDto.getFeeTypeShtCd());
            throw new InvalidRequestException(
                    "Fee Type Short Code cannot be changed. Original: " + existingProduct.getFeeTypeShtCd() +
                            ", Attempted: " + requestDto.getFeeTypeShtCd()
            );
        }

        // Rewards Type Short Code cannot be changed
        if (!existingProduct.getRewardsTypeShtCd().equals(requestDto.getRewardsTypeShtCd())) {
            log.error("Attempt to change Rewards Type Short Code from {} to {}",
                    existingProduct.getRewardsTypeShtCd(), requestDto.getRewardsTypeShtCd());
            throw new InvalidRequestException(
                    "Rewards Type Short Code cannot be changed. Original: " + existingProduct.getRewardsTypeShtCd() +
                            ", Attempted: " + requestDto.getRewardsTypeShtCd()
            );
        }

        log.info("Non-editable fields validation passed");
    }

    private void validateProductRequest(ProductCreateInDto dto) {
        log.info("Starting validation for product request");

        // 1. APR Type and Value Type Validation
        validateAprTypeAndValueType(dto.getAprType(), dto.getAprValueType());

        // 2. APR Min/Max Validation
        validateAprMinMax(dto.getAprValueType(), dto.getPurchaseAprMin(), dto.getPurchaseAprMax());

        // 3. Cash APR Verification
        verifyCashApr(dto.getPurchaseAprMin(), dto.getPurchaseAprMax(),
                dto.getCashAprMin(), dto.getCashAprMax());

        // 4. Credit Line Validation
        validateCreditLine(dto.getCreditLineMin(), dto.getCreditLineMax());

        // 5. Date Validation
        validateDates(dto.getStartDate(), dto.getEndDate());

        log.info("All validations passed successfully");
    }

    private void validateProductUpdateRequest(ProductUpdateInDto dto) {
        log.info("Starting validation for product update request");

        // 1. APR Type and Value Type Validation
        validateAprTypeAndValueType(dto.getAprType(), dto.getAprValueType());

        // 2. APR Min/Max Validation
        validateAprMinMax(dto.getAprValueType(), dto.getPurchaseAprMin(), dto.getPurchaseAprMax());

        // 3. Cash APR Verification
        verifyCashApr(dto.getPurchaseAprMin(), dto.getPurchaseAprMax(),
                dto.getCashAprMin(), dto.getCashAprMax());

        // 4. Credit Line Validation
        validateCreditLine(dto.getCreditLineMin(), dto.getCreditLineMax());

        // 5. Date Validation
        validateDates(dto.getStartDate(), dto.getEndDate());

        log.info("All validations passed successfully");
    }

    private void validateAprTypeAndValueType(String aprType, String aprValueType) {
        log.info("Validating APR type: {} and value type: {}", aprType, aprValueType);

        // APR_TYPE = FIXED → APR_VALUE_TYPE must be SPECIFIC
        if ("FIXED".equalsIgnoreCase(aprType)) {
            if (!"SPECIFIC".equalsIgnoreCase(aprValueType)) {
                log.error("APR type is FIXED but value type is not SPECIFIC: {}", aprValueType);
                throw new InvalidRequestException(
                        "When APR type is FIXED, APR value type must be SPECIFIC"
                );
            }
        }

        // APR_TYPE = VARIABLE → APR_VALUE_TYPE must be RANGE
        if ("VARIABLE".equalsIgnoreCase(aprType)) {
            if (!"RANGE".equalsIgnoreCase(aprValueType)) {
                log.error("APR type is VARIABLE but value type is not RANGE: {}", aprValueType);
                throw new InvalidRequestException(
                        "When APR type is VARIABLE, APR value type must be RANGE"
                );
            }
        }

        log.info("APR type and value type validation passed");
    }

    private void validateAprMinMax(String aprValueType, BigDecimal min, BigDecimal max) {
        log.info("Validating APR min/max values");

        // For SPECIFIC (FIXED), min must equal max
        if ("SPECIFIC".equalsIgnoreCase(aprValueType)) {
            if (min.compareTo(max) != 0) {
                log.error("APR value type is SPECIFIC but min ({}) != max ({})", min, max);
                throw new InvalidRequestException(
                        "For SPECIFIC APR value type, Purchase APR Min and Max must be equal"
                );
            }
        }

        // For RANGE (VARIABLE), max must be greater than min
        if ("RANGE".equalsIgnoreCase(aprValueType)) {
            if (min.compareTo(max) >= 0) {
                log.error("APR value type is RANGE but min ({}) >= max ({})", min, max);
                throw new InvalidRequestException(
                        "For RANGE APR value type, Purchase APR Max must be greater than Min"
                );
            }
        }

        log.info("APR min/max validation passed");
    }

    private void verifyCashApr(BigDecimal purchaseAprMin, BigDecimal purchaseAprMax,
                               BigDecimal cashAprMin, BigDecimal cashAprMax) {
        log.info("Verifying Cash APR calculation");

        // Verify Cash APR Min
        BigDecimal expectedCashAprMin = purchaseAprMin
                .multiply(CASH_APR_MULTIPLIER)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal diffMin = cashAprMin.subtract(expectedCashAprMin).abs();

        if (diffMin.compareTo(TOLERANCE) > 0) {
            log.error("Cash APR Min is incorrect. Expected: {}, Received: {}",
                    expectedCashAprMin, cashAprMin);
            throw new InvalidRequestException(
                    String.format("Cash APR Min is incorrect. Expected: %s (Purchase APR Min + 5%%), but received: %s",
                            expectedCashAprMin, cashAprMin)
            );
        }

        // Verify Cash APR Max
        BigDecimal expectedCashAprMax = purchaseAprMax
                .multiply(CASH_APR_MULTIPLIER)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal diffMax = cashAprMax.subtract(expectedCashAprMax).abs();

        if (diffMax.compareTo(TOLERANCE) > 0) {
            log.error("Cash APR Max is incorrect. Expected: {}, Received: {}",
                    expectedCashAprMax, cashAprMax);
            throw new InvalidRequestException(
                    String.format("Cash APR Max is incorrect. Expected: %s (Purchase APR Max + 5%%), but received: %s",
                            expectedCashAprMax, cashAprMax)
            );
        }

        log.info("Cash APR verification passed");
    }

    private void validateCreditLine(Integer min, Integer max) {
        log.info("Validating credit line min: {}, max: {}", min, max);

        if (min >= max) {
            log.error("Credit line validation failed: min ({}) >= max ({})", min, max);
            throw new InvalidRequestException(
                    "Credit line max must be greater than credit line min"
            );
        }

        log.info("Credit line validation passed");
    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Validating start date: {}, end date: {}", startDate, endDate);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekFromNow = now.plusWeeks(1);

        // Validate start date is at least 1 week from now
        if (startDate.isBefore(oneWeekFromNow)) {
            log.error("Start date ({}) is before one week from now ({})", startDate, oneWeekFromNow);
            throw new InvalidRequestException(
                    "Start date must be at least one week from today (" +
                            oneWeekFromNow.toLocalDate() + ")"
            );
        }

        // Validate end date is after start date
        if (endDate.isBefore(startDate) || endDate.isEqual(startDate)) {
            log.error("End date ({}) is not after start date ({})", endDate, startDate);
            throw new InvalidRequestException(
                    "End date must be after start date"
            );
        }

        log.info("Date validation passed");
    }

    private String generateProductId(String productShtCd, String feeTypeShtCd, String rewardsTypeShtCd) {
        // Format: PRODUCT-FEE-REWARDS-0000001
        String prefix = String.format("%s-%s-%s-",
                productShtCd,
                feeTypeShtCd,
                rewardsTypeShtCd
        );

        log.info("Searching for latest product with prefix: {}", prefix);

        String latestProductId = productRepository.findLatestProductIdByPrefix(prefix)
                .orElse(null);

        int nextSequence = 1;

        if (latestProductId != null) {
            log.info("Found latest product ID: {}", latestProductId);
            // Extract the last 7 digits and increment
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