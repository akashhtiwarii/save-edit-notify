package com.example.cd_create_edit_save.service;

import com.example.cd_create_edit_save.model.dto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.ProductCreateOutDto;

public interface ProductService {
    ProductCreateOutDto createProduct(ProductCreateInDto requestDto, String createdBy);
}