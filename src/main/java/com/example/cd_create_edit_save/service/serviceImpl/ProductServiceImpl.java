package com.example.cd_create_edit_save.service.serviceImpl;

import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.ProductOutDto;
import com.example.cd_create_edit_save.repository.ProductRepository;
import com.example.cd_create_edit_save.repository.ProductShortCodeRepository;
import com.example.cd_create_edit_save.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductShortCodeRepository productShortCodeRepository;

    @Override
    public ApiResponseOutDto<List<ProductOutDto>> getProducts(Long offset, Long limit) {
        try {
            if (offset == null) offset = 0L;
            if (limit == null) limit = Long.MAX_VALUE;

            List<Object[]> resultList = productRepository.getProducts(offset, limit);

            List<ProductOutDto> products = resultList.stream()
                    .map(ProductOutDto::fromEntity)
                    .collect(Collectors.toList());

            return ApiResponseOutDto.<List<ProductOutDto>>builder()
                    .status("success")
                    .message("Products retrieved successfully")
                    .data(products)
                    .timestamp(Instant.now())
                    .build();

        } catch (Exception e) {
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
        try {
            if (text != null && text.trim().isEmpty()) text = null;
            if (status != null && status.trim().isEmpty()) status = null;
            if (offset == null) offset = 0L;
            if (limit == null) limit = Long.MAX_VALUE;
            List<Object[]> resultList = productRepository.findProductsFiltered(text, status, min_apr, max_apr, limit, offset);
            long totalCount = 0L;

            if (resultList != null && !resultList.isEmpty() && resultList.get(0)[8] != null) {
                totalCount = ((Number) resultList.get(0)[8]).longValue();
            } else {
                totalCount = ((Number) productRepository.findProductsFiltered(text, status, min_apr, max_apr ,Long.MAX_VALUE , 0L).get(0)[8]).longValue();
            }

            List<ProductOutDto> products = resultList.stream()
                    .map(ProductOutDto::fromEntity)
                    .collect(Collectors.toList());

            System.out.println("Total Count: " + totalCount);
            products.forEach(System.out::println);



            return ApiResponseOutDto.<Map<String , Object>>builder()
                    .status("success")
                    .message("Filtered products retrieved successfully. Total: " + totalCount)
                    .data(Map.of("products", products, "totalCount", totalCount))
                    .timestamp(Instant.now())
                    .build();

        } catch (Exception e) {
            return ApiResponseOutDto.<Map<String , Object>>builder()
                    .status("error")
                    .message("Error retrieving filtered products: " + e.getMessage())
                    .data(Collections.emptyMap())
                    .timestamp(Instant.now())
                    .build();
        }
    }

    @Override
    public ByteArrayInputStream exportProductsToCsv() {
        try {
            List<Object[]> resultList = productRepository.getProducts(0L, Long.MAX_VALUE);
            List<ProductOutDto> products = resultList.stream()
                    .map(ProductOutDto::fromEntity)
                    .collect(Collectors.toList());

            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                 CSVPrinter csvPrinter = new CSVPrinter(
                         new PrintWriter(out),
                         CSVFormat.DEFAULT.withHeader(
                                 "Product Code", "Product Name", "Status", "Fee Type", "Min APR", "Max APR", "Start Date", "End Date"
                         ))) {

                for (ProductOutDto p : products) {
                    csvPrinter.printRecord(
                            p.getShortCode(),
                            p.getProductName(),
                            p.getStatus(),
                            p.getFee_type(),
                            p.getMin_apr(),
                            p.getMax_apr(),
                            p.getStartDate(),
                            p.getEndDate()
                    );
                }

                csvPrinter.flush();
                return new ByteArrayInputStream(out.toByteArray());

            } catch (IOException e) {
                throw new RuntimeException("Error writing CSV: " + e.getMessage(), e);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error generating CSV file: " + e.getMessage(), e);
        }
    }

}
