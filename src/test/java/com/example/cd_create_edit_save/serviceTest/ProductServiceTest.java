package com.example.cd_create_edit_save.serviceTest;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductResponseOutDto;
import com.example.cd_create_edit_save.repository.ProductRepository;
import com.example.cd_create_edit_save.repository.ProductShortCodeRepository;
import com.example.cd_create_edit_save.service.serviceImpl.ProductServiceImpl;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import static com.example.cd_create_edit_save.constants.CommonConstants.CSV_ERROR;
import static com.example.cd_create_edit_save.constants.CommonConstants.PRODUCT_LIST_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private List<Object[]> mockProductList;
    private List<Object[]> getproductList;
    private Object[] mockData ;
    Object[] mock ;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockProductList = new ArrayList<>(); // ✅ initialize before adding
     getproductList = new ArrayList<>();
        mockData = new Object[]{
                1L, "P001", "Gold Product", "ACTIVE", "FIXED", 10.0, 25.0,
                new Date(), 6L // totalCount (index 9 actually)
        };

         mock = new Object[]{
                 "P001", "Gold Product",  "FIXED", "ACTIVE",
                LocalDateTime.now(),LocalDateTime.now() , 10.0, 25.0, // totalCount (index 9 actually)
        };
        mockProductList.add(mockData);
        getproductList.add(mock);
        System.out.println("Setup complete with mock data.");
    }

    // -------------------------------------------------------
    // ✅ TEST 1: getProducts() — success
    // -------------------------------------------------------
    //resultList is null ,
    @Test
    void testGetProducts_Success() {

        when(productRepository.getProducts(anyLong(), anyLong())).thenReturn(mockProductList);

        try (MockedStatic<ProductResponseOutDto> mocked = mockStatic(ProductResponseOutDto.class)) {
            ProductResponseOutDto dto = new ProductResponseOutDto();
            dto.setProductName("Gold Product");
            dto.setStatus("ACTIVE");

            when(ProductResponseOutDto.fromEntity(any(Object[].class))).thenReturn(dto);

            ApiResponseOutDto<List<ProductResponseOutDto>> response = productService.getProducts(0L, 10L);

            assertThat(response.getStatus()).isEqualTo("success");
            assertThat(response.getData()).isNotEmpty();
            assertThat(response.getData().get(0).getProductName()).isEqualTo("Gold Product");

            verify(productRepository, times(1)).getProducts(anyLong(), anyLong());
        }
    }

    // -------------------------------------------------------
    // ✅ TEST 2: getProducts() — exception case
    // -------------------------------------------------------
    @Test
    void testGetProducts_Exception() {
        when(productRepository.getProducts(anyLong(), anyLong())).thenThrow(new RuntimeException(PRODUCT_LIST_ERROR));

        ApiResponseOutDto<List<ProductResponseOutDto>> response = productService.getProducts(0L, 10L);

        assertThat(response.getStatus()).isEqualTo("error");
        assertThat(response.getMessage()).contains(PRODUCT_LIST_ERROR);
        assertThat(response.getData()).isNull();
    }


    @Test
    void testGetProducts_EmptyList(){
        when(productRepository.getProducts(anyLong(), anyLong())).thenReturn(List.of());

        ApiResponseOutDto<List<ProductResponseOutDto>> response = productService.getProducts(0L, 10L);

        assertThat(response.getStatus()).isEqualTo("success");
        assertThat(response.getData()).isEmpty();
    }

    // -------------------------------------------------------
    // ✅ TEST 3: getProductByParameters() — with valid params
    // -------------------------------------------------------
    @Test
    void testGetProductByParameters_Success() {

        when(productRepository.findProductsFiltered(
                anyString(), anyString(), any(), any(), anyLong(), anyLong()))
                .thenReturn(mockProductList);

        try (MockedStatic<ProductResponseOutDto> mocked = mockStatic(ProductResponseOutDto.class)) {
            ProductResponseOutDto dto = new ProductResponseOutDto();
            dto.setProductName("Gold Product");
            dto.setStatus("ACTIVE");

            when(ProductResponseOutDto.fromEntity(any(Object[].class))).thenReturn(dto);

            ApiResponseOutDto<Map<String, Object>> response = productService.getProductByParameters(
                    "Gold", 10.0, 25.0, "ACTIVE", 0L, 10L
            );

            assertThat(response.getStatus()).isEqualTo("success");
            assertThat(response.getData()).containsKeys("products", "totalCount");
            assertThat(response.getData().get("totalCount")).isEqualTo(6L);

            verify(productRepository, times(1))
                    .findProductsFiltered(anyString(), anyString(), any(), any(), anyLong(), anyLong());
        }
    }

    // -------------------------------------------------------
    // ✅ TEST 4: getProductByParameters() — null or blank params
    // -------------------------------------------------------
    @Test
    void testGetProductByParameters_WithNullParams() {


        try (MockedStatic<ProductResponseOutDto> mocked = mockStatic(ProductResponseOutDto.class)) {
            ProductResponseOutDto dto = new ProductResponseOutDto();
            dto.setProductName("Gold Product");
            mocked.when(() -> ProductResponseOutDto.fromEntity(any(Object[].class)))
                    .thenReturn(dto);

            when(productRepository.findProductsFiltered(any(), any(), any(), any(), anyLong(), anyLong()))
                    .thenReturn(mockProductList);

            ApiResponseOutDto<Map<String, Object>> response =
                    productService.getProductByParameters(" ", null, null, " ", null, null);

            assertThat(response.getStatus()).isEqualTo("success");
            assertThat(response.getData()).containsKeys("products", "totalCount");
            assertThat(response.getData().get("totalCount")).isEqualTo(6L);
        }
    }

    // -------------------------------------------------------
    // ✅ TEST 5: getProductByParameters() — exception case
    // -------------------------------------------------------
    @Test
    void testGetProductByParameters_Exception() {
        when(productRepository.findProductsFiltered(
                any(), any(), any(), any(), anyLong(), anyLong()))
                .thenThrow(new RuntimeException("DB failure"));

        ApiResponseOutDto<Map<String, Object>> response =
                productService.getProductByParameters("Gold", null, null, "ACTIVE", 0L, 10L);

        assertThat(response.getStatus()).isEqualTo("error");
        assertThat(response.getMessage()).contains("DB failure");
        assertThat(response.getData()).isNull();
    }

    // -------------------------------------------------------
    // ✅ TEST 6: exportProductsToCsv() — success
    // -------------------------------------------------------
    @Test
    void testExportProductsToCsv_Success() throws IOException {
        when(productRepository.getProducts(anyLong(), anyLong())).thenReturn(getproductList);

        ByteArrayInputStream inputStream = productService.exportProductsToCsv();

        assertThat(inputStream).isNotNull();

        // Parse CSV content
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            List<CSVRecord> records = parser.getRecords();
            assertThat(records).hasSize(1);
            assertThat(records.get(0).get("Product Name")).isEqualTo("Gold Product");
        }
    }

    // -------------------------------------------------------
    // ✅ TEST 7: exportProductsToCsv() — throws exception
    // -------------------------------------------------------
    @Test
    void testExportProductsToCsv_Exception() {
        when(productRepository.getProducts(anyLong(), anyLong())).thenThrow(new RuntimeException(CSV_ERROR));

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> productService.exportProductsToCsv()
        );

        assertThat(exception.getMessage()).contains(CSV_ERROR);
    }
}