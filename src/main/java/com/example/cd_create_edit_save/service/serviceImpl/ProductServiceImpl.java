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
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductShortCodeRepository productShortCodeRepository;

    @Override
    public ApiResponseOutDto<List<ProductOutDto>> getProducts(Long offset , Long limit) {
//
        if (offset == null) offset = 0L;
        if (limit == null) limit = Long.MAX_VALUE;
        List<Object[]> resultList = productRepository.getProducts(offset, limit);

        List<ProductOutDto> products = resultList.stream()
                .map(obj -> ProductOutDto.builder()
                        .shortCode((String) obj[0])
                        .productName((String) obj[1])
                        .fee_type((String) obj[2])
                        .status((String) obj[3])
                        .startDate(obj[4] != null ? ((Timestamp) obj[4]).toLocalDateTime() : null)
                        .endDate(obj[5] != null ? ((Timestamp) obj[5]).toLocalDateTime() : null)
                        .min_apr(obj[6] != null ? ((Number) obj[6]).doubleValue() : null)
                        .max_apr(obj[7] != null ? ((Number) obj[7]).doubleValue() : null)
                        .build())
                .collect(Collectors.toList());


        return ApiResponseOutDto.success(products);
    }

    @Override
    public ApiResponseOutDto<List<ProductOutDto>> getProductByParameters(String text, Double min_apr, Double max_apr, String status , long offset , long limit)  {

        if (text != null && text.trim().isEmpty()) text = null;
        if (status != null && status.trim().isEmpty()) status = null;

        List<Object[]> resultList = productRepository.findProductsFiltered(text, status, min_apr, max_apr , limit ,offset);

        List<ProductOutDto> products = resultList.stream()
                .map(obj -> ProductOutDto.builder()
                        .shortCode((String) obj[0])
                        .productName((String) obj[1])
                        .fee_type((String) obj[2])
                        .status((String) obj[3])
                        .startDate(obj[4] != null ? ((Timestamp) obj[4]).toLocalDateTime() : null)
                        .endDate(obj[5] != null ? ((Timestamp) obj[5]).toLocalDateTime() : null)
                        .min_apr(obj[6] != null ? ((Number) obj[6]).doubleValue() : null)
                        .max_apr(obj[7] != null ? ((Number) obj[7]).doubleValue() : null)
                        .build())
                .collect(Collectors.toList());

        products.forEach(System.out::println);

        return ApiResponseOutDto.success(products);
    }




     @Override
     public ByteArrayInputStream exportProductsToCsv() {
        List<Object[]> resultList = productRepository.getProducts(0L, Long.MAX_VALUE);
        List<ProductOutDto> products = resultList.stream()
                .map(obj -> ProductOutDto.builder()
                        .shortCode((String) obj[0])
                        .productName((String) obj[1])
                        .fee_type((String) obj[2])
                        .status((String) obj[3])
                        .startDate(obj[4] != null ? ((Timestamp) obj[4]).toLocalDateTime() : null)
                        .endDate(obj[5] != null ? ((Timestamp) obj[5]).toLocalDateTime() : null)
                        .min_apr(obj[6] != null ? ((Number) obj[6]).doubleValue() : null)
                        .max_apr(obj[7] != null ? ((Number) obj[7]).doubleValue() : null)
                        .build())
                .collect(Collectors.toList());


        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(
                     new PrintWriter(out),
                     CSVFormat.DEFAULT.withHeader("Product Code", "Product Name", "Status", "APR Type"))) {

            for (ProductOutDto p : products) {
                csvPrinter.printRecord(
                        p.getShortCode(),
                        p.getStatus(),
                        p.getProductName(),
                        p.getFee_type(),
                        p.getMin_apr(),
                        p.getMax_apr(),
                        p.getStartDate(),
                        p.getEndDate()
                );
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error generating CSV file: " + e.getMessage());
        }
    }

}
