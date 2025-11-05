package com.example.cd_create_edit_save.service.serviceImpl;

import com.example.cd_create_edit_save.exception.InvalidRequestException;
import com.example.cd_create_edit_save.exception.ResourceNotFoundException;
import com.example.cd_create_edit_save.mapper.ProductMapper;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductSummaryOutDTO;
import com.example.cd_create_edit_save.model.entity.Product;
import com.example.cd_create_edit_save.repository.ProductRepository;
import com.example.cd_create_edit_save.validator.ProductValidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductValidator productValidator;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductOutDto productOutDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setProductId("PRD-001");
        product.setProductShtCd("ABC");
        product.setFeeTypeShtCd("FT");
        product.setRewardsTypeShtCd("RW");
        product.setStatus("ACTIVE");

        productOutDto = new ProductOutDto();
        productOutDto.setProductId("PRD-001");
        productOutDto.setProductShtCd("ABC");
        productOutDto.setStatus("ACTIVE");
    }

    @Test
    void testGetProductById_Success() {
        String productId = "PRD-001";

        when(productValidator.validateProductIdAndGetProduct(productId)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productOutDto);

        ProductOutDto result = productService.getProductById(productId);

        assertNotNull(result);
        assertEquals("PRD-001", result.getProductId());
        assertEquals("ABC", result.getProductShtCd());
        assertEquals("ACTIVE", result.getStatus());

        verify(productValidator, times(1)).validateProductIdAndGetProduct(productId);
        verify(productMapper, times(1)).toDto(product);
    }

    @Test
    void testGetProductById_InvalidRequestException() {
        String invalidProductId = "";

        when(productValidator.validateProductIdAndGetProduct(invalidProductId))
                .thenThrow(new InvalidRequestException("Product ID cannot be null or empty"));

        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.getProductById(invalidProductId)
        );

        assertEquals("Product ID cannot be null or empty", exception.getMessage());
        verify(productValidator, times(1)).validateProductIdAndGetProduct(invalidProductId);
        verify(productMapper, never()).toDto(any());
    }

    @Test
    void testGetProductById_ResourceNotFoundException() {
        String missingProductId = "PRD-999";

        when(productValidator.validateProductIdAndGetProduct(missingProductId))
                .thenThrow(new ResourceNotFoundException("Product not found with ID: " + missingProductId));

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.getProductById(missingProductId)
        );

        assertEquals("Product not found with ID: PRD-999", exception.getMessage());
        verify(productValidator, times(1)).validateProductIdAndGetProduct(missingProductId);
        verify(productMapper, never()).toDto(any());
    }
    @Test
    void testGetProductSummary_Success() {
        // Arrange
        when(productRepository.count()).thenReturn(100L);
        when(productRepository.countByStatus("ACTIVE")).thenReturn(60L);
        when(productRepository.countByStatus("PENDING_APPROVAL")).thenReturn(30L);
        when(productRepository.countByStatus("EXPIRED")).thenReturn(10L);

        // Act
        ProductSummaryOutDTO summary = productService.getProductSummary();

        // Assert
        assertNotNull(summary);
        assertEquals(100L, summary.getTotalProducts());
        assertEquals(60L, summary.getActiveProducts());
        assertEquals(30L, summary.getPendingApproval());
        assertEquals(10L, summary.getExpiredProducts());
        verify(productRepository, times(1)).count();
        verify(productRepository, times(1)).countByStatus("ACTIVE");
    }

    @Test
    void testGetProductSummary_Exception() {
        // Arrange
        when(productRepository.count()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.getProductSummary());
        assertTrue(exception.getMessage().contains("Error retrieving product summary"));
    }

}