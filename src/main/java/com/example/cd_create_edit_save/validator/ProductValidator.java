package com.example.cd_create_edit_save.validator;

import com.example.cd_create_edit_save.exception.InvalidRequestException;
import com.example.cd_create_edit_save.exception.ResourceNotFoundException;
import com.example.cd_create_edit_save.model.entity.Product;
import com.example.cd_create_edit_save.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductValidator {
    private final ProductRepository productRepository;

    public Product validateProductIdAndGetProduct(String productId) {
        log.info("Validating product ID: {}", productId);
        if (productId == null || productId.trim().isEmpty()) {
            log.error("Invalid product ID: {}", productId);
            throw new InvalidRequestException("Product ID cannot be null or empty");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", productId);
                    return new ResourceNotFoundException("Product not found with ID: " + productId);
                });

        log.info("Product validation successful for ID: {}", productId);
        return product;
    }

}
