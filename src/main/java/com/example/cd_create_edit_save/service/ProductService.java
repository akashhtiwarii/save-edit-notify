package com.example.cd_create_edit_save.service;

import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.ProductOutDto;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

public interface ProductService {

ApiResponseOutDto<List<ProductOutDto>> getProducts(Long offset , Long Limit);

    ApiResponseOutDto<Map<String, Object>> getProductByParameters(String text, Double min_apr, Double max_apr, String status, Long offset, Long limit);

    ByteArrayInputStream exportProductsToCsv();
}
