package com.example.cd_create_edit_save.service.serviceImpl;

import com.example.cd_create_edit_save.mapper.ProductMapper;
import com.example.cd_create_edit_save.model.dto.outDto.ProductResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.inDto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.inDto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductSummaryOutDTO;
import com.example.cd_create_edit_save.model.entity.Product;
import com.example.cd_create_edit_save.repository.ProductRepository;
import com.example.cd_create_edit_save.service.ProductService;
import com.example.cd_create_edit_save.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.cd_create_edit_save.constants.AppConstants.MAX_LIMIT;
import static com.example.cd_create_edit_save.constants.AppConstants.MIN_OFFSET;
import static com.example.cd_create_edit_save.constants.CommonConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;

    @Override
    public ApiResponseOutDto<List<ProductResponseOutDto>> getProducts(Long offset, Long limit) {
        log.info("Fetching products with offset={} and limit={}", offset, limit);

        try {
            if(offset == null) offset = MIN_OFFSET;
            if (limit == null) limit = MAX_LIMIT;

            List<Object[]> resultList = productRepository.getProducts(offset, limit);
            log.debug("Fetched {} raw records from repository", resultList.size());

            if (resultList.isEmpty()) {
                log.info("No products found for offset={} and limit={}", offset, limit);
                return ApiResponseOutDto.success(Collections.emptyList() , EMPTY_PRODUCT_LIST);
            }

            List<ProductResponseOutDto> products = resultList.stream()
                    .map(ProductResponseOutDto::fromEntity)
                    .collect(Collectors.toList());

            log.info("Successfully retrieved {} products", products.size());
            return ApiResponseOutDto.success(products , PRODUCT_LIST_RETRIEVED);

        } catch (Exception e) {
            log.error("Error retrieving products: {}", e.getMessage(), e);
            return ApiResponseOutDto.error(PRODUCT_LIST_ERROR + e.getMessage());
        }
    }

    @Override
    public ApiResponseOutDto<Map<String, Object>> getProductByParameters(String text, Double min_apr, Double max_apr, String status, Long offset, Long limit) {
        log.info("Fetching products by parameters: text={}, min_apr={}, max_apr={}, status={}, offset={}, limit={}",
                text, min_apr, max_apr, status, offset, limit);

        try {
            if (text != null && text.trim().isEmpty()) text = null;
            if (status != null && status.trim().isEmpty()) status = null;
            if (offset == null) offset = MIN_OFFSET;
            if (limit == null) limit = MAX_LIMIT;

            List<Object[]> resultList = productRepository.findProductsFiltered(text, status, min_apr, max_apr, limit, offset);
            log.debug("Fetched {} filtered records from repository", resultList.size());

            long totalCount = 0L;

            if (resultList != null && !resultList.isEmpty() && resultList.get(0)[8] != null) {
                totalCount = ((Number) resultList.get(0)[8]).longValue();
            }
            else {
                log.debug("Total count missing in result; running count query...");
                totalCount = ((Number) productRepository
                        .findProductsFiltered(text, status, min_apr, max_apr, MAX_LIMIT, MIN_OFFSET)
                        .get(0)[8]).longValue();
            }

            List<ProductResponseOutDto> products = resultList.stream()
                    .map(ProductResponseOutDto::fromEntity)
                    .collect(Collectors.toList());

            log.info("Filtered products retrieved: count={}, totalCount={}", products.size(), totalCount);

             return ApiResponseOutDto.success(Map.of("products", products, "totalCount", totalCount) , PRODUCT_LIST_RETRIEVED);

        } catch (Exception e) {
            log.error("Error retrieving filtered products: {}", e.getMessage(), e);
            return ApiResponseOutDto.error(PRODUCT_LIST_ERROR + e.getMessage());
        }
    }

    @Override
    public ByteArrayInputStream exportProductsToCsv() {
        log.info("Starting CSV export for all products...");

        try {
            List<Object[]> resultList = productRepository.getProducts(MIN_OFFSET, MAX_LIMIT);
            log.debug("Fetched {} records for CSV export", resultList.size());

            List<ProductResponseOutDto> products = resultList.stream()
                    .map(ProductResponseOutDto::fromEntity)
                    .collect(Collectors.toList());

            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                 CSVPrinter csvPrinter = new CSVPrinter(
                         new PrintWriter(out),
                         CSVFormat.DEFAULT.withHeader(
                                 "Product Code", "Product Name", "Status", "Fee Type",
                                 "Min Purchase APR", "Max Purchase APR", "Start Date", "End Date"
                         ))) {

                for (ProductResponseOutDto p : products) {
                    csvPrinter.printRecord(
                            p.getShortCode(),
                            p.getProductName(),
                            p.getStatus(),
                            p.getFee_type(),
                            p.getPurchase_apr_min(),
                            p.getPurchase_apr_max(),
                            p.getStartDate(),
                            p.getEndDate()
                    );
                }

                csvPrinter.flush();
                log.info("Successfully generated CSV file with {} records", products.size());
                return new ByteArrayInputStream(out.toByteArray());

            } catch (IOException e) {
                log.error("Error writing CSV: {}", e.getMessage(), e);
                throw new RuntimeException(CSV_ERROR + e.getMessage(), e);
            }

        } catch (Exception e) {
            log.error("Error generating CSV file: {}", e.getMessage(), e);
            throw new RuntimeException(CSV_ERROR + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public ProductOutDto createProduct(ProductCreateInDto requestDto, String createdBy) {
        log.info("Starting product creation for category: {}, fee: {}, rewards: {}",
                requestDto.getProductShtCd(), requestDto.getFeeTypeShtCd(), requestDto.getRewardsTypeShtCd());

        try {
            productValidator.validateProductCreateRequest(requestDto);

            String productId = generateProductId(requestDto.getProductShtCd(),
                    requestDto.getFeeTypeShtCd(), requestDto.getRewardsTypeShtCd());
            log.info("Generated product ID: {}", productId);

            Product product = productMapper.toEntity(requestDto, productId, createdBy);
            Product savedProduct = productRepository.save(product);
            log.info("Product saved successfully with ID: {}", productId);

            ProductOutDto response = productMapper.toDto(savedProduct);
            log.info("Product created successfully. Product ID: {}", productId);

            return response;

        } catch (Exception e) {
            log.error("Error during product creation: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public ProductOutDto updateProduct(String productId, ProductUpdateInDto requestDto, String updatedBy) {
        log.info("Starting product update for Product ID: {}", productId);

        try {
            Product existingProduct = productValidator.validateProductIdAndGetProduct(productId);
            log.info("Found existing product: {}", existingProduct.getProductId());

            productValidator.validateNonEditableFields(existingProduct, requestDto);

            productValidator.validateFieldChanges(existingProduct, requestDto);

            productValidator.validateProductUpdateRequest(requestDto);

            String newProductId = generateProductId(requestDto.getProductShtCd(),
                    requestDto.getFeeTypeShtCd(), requestDto.getRewardsTypeShtCd());
            log.info("Generated new product ID: {}", newProductId);

            Product newProduct = productMapper.toEntity(requestDto, newProductId, updatedBy);
            Product savedProduct = productRepository.save(newProduct);
            log.info("Product version saved successfully with ID: {}", newProductId);

            ProductOutDto response = productMapper.toDto(savedProduct);
            log.info("Product updated successfully. Old ID: {}, New ID: {}", productId, newProductId);

            return response;

        } catch (Exception e) {
            log.error("Error during product update: {}", e.getMessage(), e);
            throw e;
        }
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

    @Override
    @Transactional(readOnly = true)
    public ProductOutDto getProductById(String productId) {

        log.info("Fetching product with ID: {}", productId);

        Product product = productValidator.validateProductIdAndGetProduct(productId);

        log.info("Successfully retrieved product with ID: {}", productId);

        return productMapper.toDto(product);
    }

    @Override
    public ProductSummaryOutDTO getProductSummary() {
        log.info("Fetching product summary");
        try {
            long total = productRepository.count();
            long active = productRepository.countByStatus("ACTIVE");
            long pending = productRepository.countByStatus("PENDING_APPROVAL");
            long expired = productRepository.countByStatus("EXPIRED");

            return new ProductSummaryOutDTO(total, active, pending, expired);
        } catch (Exception e) {
            log.error("Error retrieving product summary: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving product summary", e);
        }
    }
}