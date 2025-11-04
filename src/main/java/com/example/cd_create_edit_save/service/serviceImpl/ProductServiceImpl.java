package com.example.cd_create_edit_save.service.serviceImpl;

import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.ProductOutDto;
import com.example.cd_create_edit_save.repository.ProductRepository;
import com.example.cd_create_edit_save.repository.ProductShortCodeRepository;
import com.example.cd_create_edit_save.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ApiResponseOutDto<List<ProductOutDto>> getProducts(Long offset, Long limit) {
        log.info("Fetching products with offset={} and limit={}", offset, limit);

        try {
            if (limit == null) limit = Long.MAX_VALUE;

            List<Object[]> resultList = productRepository.getProducts(offset, limit);
            log.debug("Fetched {} raw records from repository", resultList.size());

            if (resultList.isEmpty()) {
                log.info("No products found for offset={} and limit={}", offset, limit);
                return ApiResponseOutDto.success(Collections.emptyList());
            }

            List<ProductOutDto> products = resultList.stream()
                    .map(ProductOutDto::fromEntity)
                    .collect(Collectors.toList());
            log.debug("Mapped {} records to ProductOutDto", products.size());

            ApiResponseOutDto<List<ProductOutDto>> response = ApiResponseOutDto.<List<ProductOutDto>>builder()
                    .status("success")
                    .message("Products retrieved successfully")
                    .data(products)
                    .timestamp(Instant.now())
                    .build();

            log.info("Successfully retrieved {} products", products.size());
            return response;

        } catch (Exception e) {
            log.error("Error retrieving products: {}", e.getMessage(), e);
            return ApiResponseOutDto.<List<ProductOutDto>>builder()
                    .status("error")
                    .message("Error retrieving products: " + e.getMessage())
                    .data(Collections.emptyList())
                    .timestamp(Instant.now())
                    .build();
        }
    }

    @Override
    public ApiResponseOutDto<Map<String, Object>> getProductByParameters(String text, Double min_apr, Double max_apr, String status, Long offset, Long limit) {
        log.info("Fetching products by parameters: text={}, min_apr={}, max_apr={}, status={}, offset={}, limit={}",
                text, min_apr, max_apr, status, offset, limit);

        try {
            if (text != null && text.trim().isEmpty()) text = null;
            if (status != null && status.trim().isEmpty()) status = null;
            if (offset == null) offset = 0L;
            if (limit == null) limit = Long.MAX_VALUE;

            List<Object[]> resultList = productRepository.findProductsFiltered(text, status, min_apr, max_apr, limit, offset);
            log.debug("Fetched {} filtered records from repository", resultList.size());

            long totalCount = 0L;
            if (resultList != null && !resultList.isEmpty() && resultList.get(0)[8] != null) {
                totalCount = ((Number) resultList.get(0)[8]).longValue();
            } else {
                log.debug("Total count missing in result; running count query...");
                totalCount = ((Number) productRepository
                        .findProductsFiltered(text, status, min_apr, max_apr, Long.MAX_VALUE, 0L)
                        .get(0)[8]).longValue();
            }

            List<ProductOutDto> products = resultList.stream()
                    .map(ProductOutDto::fromEntity)
                    .collect(Collectors.toList());

            log.info("Filtered products retrieved: count={}, totalCount={}", products.size(), totalCount);

            return ApiResponseOutDto.<Map<String, Object>>builder()
                    .status("success")
                    .message("Filtered products retrieved successfully. Total: " + totalCount)
                    .data(Map.of("products", products, "totalCount", totalCount))
                    .timestamp(Instant.now())
                    .build();

        } catch (Exception e) {
            log.error("Error retrieving filtered products: {}", e.getMessage(), e);
            return ApiResponseOutDto.<Map<String, Object>>builder()
                    .status("error")
                    .message("Error retrieving filtered products: " + e.getMessage())
                    .data(Collections.emptyMap())
                    .timestamp(Instant.now())
                    .build();
        }
    }

    @Override
    public ByteArrayInputStream exportProductsToCsv() {
        log.info("Starting CSV export for all products...");

        try {
            List<Object[]> resultList = productRepository.getProducts(0L, Long.MAX_VALUE);
            log.debug("Fetched {} records for CSV export", resultList.size());

            List<ProductOutDto> products = resultList.stream()
                    .map(ProductOutDto::fromEntity)
                    .collect(Collectors.toList());

            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                 CSVPrinter csvPrinter = new CSVPrinter(
                         new PrintWriter(out),
                         CSVFormat.DEFAULT.withHeader(
                                 "Product Code", "Product Name", "Status", "Fee Type",
                                 "Min APR", "Max APR", "Start Date", "End Date"
                         ))) {

                for (ProductOutDto p : products) {
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
                throw new RuntimeException("Error writing CSV: " + e.getMessage(), e);
            }

        } catch (Exception e) {
            log.error("Error generating CSV file: {}", e.getMessage(), e);
            throw new RuntimeException("Error generating CSV file: " + e.getMessage(), e);
        }
    }
}



