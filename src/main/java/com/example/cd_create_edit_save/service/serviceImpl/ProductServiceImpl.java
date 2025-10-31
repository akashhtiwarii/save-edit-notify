package com.example.cd_create_edit_save.service.serviceImpl;

import com.example.cd_create_edit_save.exception.InvalidRequestException;
import com.example.cd_create_edit_save.exception.ResourceNotFoundException;
import com.example.cd_create_edit_save.mapper.ProductMapper;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.model.entity.Product;
import com.example.cd_create_edit_save.repository.ProductRepository;
import com.example.cd_create_edit_save.service.ProductService;
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

    @Override
    @Transactional(readOnly = true)
    public ProductOutDto getProductById(String productId) {
        log.info("Fetching product with ID: {}", productId);

        if (productId == null || productId.trim().isEmpty()) {
            log.error("Invalid product ID provided: {}", productId);
            throw new InvalidRequestException("Product ID cannot be null or empty");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", productId);
                    return new ResourceNotFoundException(
                            String.format("Product not found with ID: %s", productId)
                    );
                });

        log.info("Successfully retrieved product with ID: {}", productId);

        return productMapper.toDto(product);
    }
}
