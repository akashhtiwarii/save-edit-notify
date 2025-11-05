package com.example.cd_create_edit_save.controller;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductSummaryOutDTO;
import com.example.cd_create_edit_save.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetProductById_Success() throws Exception {
        String productId = "PRD001";

        ProductOutDto mockProduct = ProductOutDto.builder()
                .productId(productId)
                .productShtCd("P01")
                .feeTypeShtCd("FT")
                .rewardsTypeShtCd("RW")
                .aprType("Fixed")
                .aprValueType("Percentage")
                .purchaseAprMin(BigDecimal.valueOf(10.5))
                .purchaseAprMax(BigDecimal.valueOf(15.0))
                .status("ACTIVE")
                .createdBy("admin")
                .createdDatetime(LocalDateTime.now())
                .build();

        ApiResponseOutDto<ProductOutDto> mockResponse = ApiResponseOutDto.<ProductOutDto>builder()
                .status("SUCCESS")
                .message("Product retrieved successfully")
                .data(mockProduct)
                .timestamp(Instant.now())
                .build();

        when(productService.getProductById(productId)).thenReturn(mockProduct);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Product retrieved successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId").value(productId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value("ACTIVE"))
                .andDo(MockMvcResultHandlers.print());

        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    void testGetProductById_ProductNotFound() throws Exception {

        String productId = "INVALID_ID";

        when(productService.getProductById(productId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Product retrieved successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").doesNotExist())
                .andDo(MockMvcResultHandlers.print());

        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    void testGetProductSummary_Success() {
        // Arrange
        ProductSummaryOutDTO mockSummary = new ProductSummaryOutDTO(100L, 60L, 30L, 10L);
        when(productService.getProductSummary()).thenReturn(mockSummary);

        // Act
        ResponseEntity<ApiResponseOutDto<ProductSummaryOutDTO>> response = productController.getProductSummary();

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("success", response.getBody().getStatus());
        assertEquals("Product summary retrieved successfully.", response.getBody().getMessage());
        assertEquals(mockSummary, response.getBody().getData());
        verify(productService, times(1)).getProductSummary();
    }

    @Test
    void testGetProductSummary_Exception() {
        // Arrange
        when(productService.getProductSummary()).thenThrow(new RuntimeException("DB Issue"));

        // Act
        ResponseEntity<ApiResponseOutDto<ProductSummaryOutDTO>> response = productController.getProductSummary();

        // Assert
        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("error", response.getBody().getStatus());
        assertTrue(response.getBody().getMessage().contains("DB Issue"));
        verify(productService, times(1)).getProductSummary();
    }

}

