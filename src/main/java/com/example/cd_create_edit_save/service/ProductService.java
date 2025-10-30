package com.example.cd_create_edit_save.service;

import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;

public interface ProductService {
    ProductOutDto getProductById(String productId);
}
