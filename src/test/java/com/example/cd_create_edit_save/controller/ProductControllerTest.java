package com.example.cd_create_edit_save.controller;

import com.example.cd_create_edit_save.model.dto.ProductRequestInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductSummaryOutDTO;
import com.example.cd_create_edit_save.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

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

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{productId}", productId)
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

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{productId}", productId)
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
        assertEquals("SUCCESS", response.getBody().getStatus());
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
        assertEquals("FAILURE", response.getBody().getStatus());
        assertTrue(response.getBody().getMessage().contains("DB Issue"));
        verify(productService, times(1)).getProductSummary();
    }


    @Test
    void testGetProducts_Success() throws Exception {
        List<ProductResponseOutDto> mockProducts = new ArrayList<>();
        ProductResponseOutDto product = new ProductResponseOutDto();
        product.setProductName("Gold Product");
        product.setStatus("ACTIVE");
        mockProducts.add(product);

        ApiResponseOutDto<List<ProductResponseOutDto>> mockResponse = ApiResponseOutDto.<List<ProductResponseOutDto>>builder()
                .status("SUCCESS")
                .message("Products retrieved successfully")
                .data(mockProducts)
                .timestamp(Instant.now())
                .build();

        when(productService.getProducts(0L, 10L)).thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/products")
                        .param("offset", "0")
                        .param("limit", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].productName").value("Gold Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].status").value("ACTIVE"))
                .andDo(MockMvcResultHandlers.print());

        verify(productService, times(1)).getProducts(0L, 10L);
    }


    @Test
    void testGetProductsByParameters_Success() throws Exception {
        ProductRequestInDto request = new ProductRequestInDto();
        request.setText("Gold");
        request.setMin_apr(10.0);
        request.setMax_apr(20.0);
        request.setStatus("ACTIVE");
        request.setOffset(0L);
        request.setLimit(5L);

        Map<String, Object> mockData = new HashMap<>();
        mockData.put("totalCount", 1);
        mockData.put("products", List.of(Map.of("productName", "Gold Product", "status", "ACTIVE")));

        ApiResponseOutDto<Map<String, Object>> mockResponse = ApiResponseOutDto.<Map<String, Object>>builder()
                .status("SUCCESS")
                .message("Filtered products retrieved successfully")
                .data(mockData)
                .timestamp(Instant.now())
                .build();

        when(productService.getProductByParameters(
                eq("Gold"), eq(10.0), eq(20.0),
                eq("ACTIVE"), eq(0L), eq(5L))
        ).thenReturn(mockResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/products-by-parameter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.products[0].productName").value("Gold Product"))
                .andDo(MockMvcResultHandlers.print());

        verify(productService, times(1)).getProductByParameters(
                eq("Gold"), eq(10.0), eq(20.0),
                eq("ACTIVE"), eq(0L), eq(5L)
        );
    }


    @Test
    void testExportProductsToCsv_Success() throws Exception {
        // Mock CSV content as InputStream
        String csvContent = "Product Name,Status\nGold Product,ACTIVE";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());

        when(productService.exportProductsToCsv()).thenReturn(inputStream);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/export-products")
                        .accept("text/csv"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.csv"))
                .andExpect(MockMvcResultMatchers.content().contentType("text/csv"))
                .andDo(MockMvcResultHandlers.print());

        verify(productService, times(1)).exportProductsToCsv();
    }



    @Test
    void testGetProducts_EmptyList_ReturnsEmptyData() throws Exception {
        ApiResponseOutDto<List<ProductResponseOutDto>> emptyResponse = ApiResponseOutDto.<List<ProductResponseOutDto>>builder()
                .status("SUCCESS")
                .message("No products found")
                .data(Collections.emptyList())
                .timestamp(Instant.now())
                .build();

        when(productService.getProducts(0L, 10L)).thenReturn(emptyResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/products")
                        .param("offset", "0")
                        .param("limit", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No products found"))
                .andDo(MockMvcResultHandlers.print());

        verify(productService, times(1)).getProducts(0L, 10L);
    }

    @Test
    void testGetProductsByParameters_InvalidRequest_ValidationFailure() throws Exception {
        ProductRequestInDto invalidRequest = new ProductRequestInDto();
        invalidRequest.setText("X".repeat(120)); // exceeds 100-character limit

        mockMvc.perform(MockMvcRequestBuilders.post("/api/products/products-by-parameter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }




}

