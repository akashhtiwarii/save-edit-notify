package com.example.cd_create_edit_save.service;

import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.ProductOutDto;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ProductService {

ApiResponseOutDto<List<ProductOutDto>> getProducts(Long offset , Long Limit);

    ApiResponseOutDto<List<ProductOutDto>> getProductByParameters(String text, Double min_apr, Double max_apr, String status , long offset , long limit);

    ByteArrayInputStream exportProductsToCsv();
}
