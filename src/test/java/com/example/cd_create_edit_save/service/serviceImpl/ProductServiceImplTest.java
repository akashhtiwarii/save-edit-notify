package com.example.cd_create_edit_save.service.serviceImpl;

import com.example.cd_create_edit_save.exception.InvalidRequestException;
import com.example.cd_create_edit_save.exception.ResourceNotFoundException;
import com.example.cd_create_edit_save.mapper.ProductMapper;
import com.example.cd_create_edit_save.model.dto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.model.entity.Product;
import com.example.cd_create_edit_save.repository.ProductRepository;
import com.example.cd_create_edit_save.validator.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductValidator productValidator;

    @InjectMocks
    private ProductServiceImpl productService;

    private ProductCreateInDto createRequestDto;
    private ProductUpdateInDto updateRequestDto;
    private Product product;
    private Product existingProduct;
    private ProductOutDto productOutDto;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        startDate = LocalDateTime.now().plusWeeks(2);
        endDate = LocalDateTime.now().plusMonths(6);

        createRequestDto = ProductCreateInDto.builder()
                .productShtCd("GOL")
                .feeTypeShtCd("AF")
                .rewardsTypeShtCd("CB")
                .aprType("FIXED")
                .aprValueType("SPECIFIC")
                .purchaseAprMin(new BigDecimal("10.50"))
                .purchaseAprMax(new BigDecimal("10.50"))
                .cashAprMin(new BigDecimal("11.03"))
                .cashAprMax(new BigDecimal("11.03"))
                .termsConditionsLink("https://example.com/terms")
                .cardholderAgreementLink("https://example.com/agreement")
                .cardImageLink("https://example.com/image.png")
                .creditLineMin(1000)
                .creditLineMax(5000)
                .securityDepositIndicator("Y")
                .securityDepositMin(200)
                .securityDepositMax(500)
                .toBeApprovedBy("jdoe")
                .approvalPriorityLevel("HIGH PRIORITY")
                .commentsToApprover("Please review urgently")
                .prin("PRIN001")
                .cwsProductId("CWS-001")
                .chaCode("CHA-WEB")
                .pcFlag1(true)
                .pcFlag2(false)
                .upc("UPC001")
                .startDate(startDate)
                .endDate(endDate)
                .build();

        updateRequestDto = ProductUpdateInDto.builder()
                .productShtCd("GOL")
                .feeTypeShtCd("AF")
                .rewardsTypeShtCd("CB")
                .aprType("VARIABLE")
                .aprValueType("RANGE")
                .purchaseAprMin(new BigDecimal("12.50"))
                .purchaseAprMax(new BigDecimal("18.50"))
                .cashAprMin(new BigDecimal("13.13"))
                .cashAprMax(new BigDecimal("19.43"))
                .termsConditionsLink("https://example.com/terms-updated")
                .cardholderAgreementLink("https://example.com/agreement-updated")
                .cardImageLink("https://example.com/image-updated.png")
                .creditLineMin(2000)
                .creditLineMax(10000)
                .securityDepositIndicator("N")
                .securityDepositMin(null)
                .securityDepositMax(null)
                .toBeApprovedBy("jsmith")
                .approvalPriorityLevel("NORMAL PRIORITY")
                .commentsToApprover("Standard review")
                .prin("PRIN002")
                .cwsProductId("CWS-002")
                .chaCode("CHA-MOB")
                .pcFlag1(false)
                .pcFlag2(true)
                .upc("UPC002")
                .startDate(startDate)
                .endDate(endDate)
                .build();

        product = new Product();
        product.setProductId("GOL-AF-CB-0000001");
        product.setProductShtCd("GOL");
        product.setFeeTypeShtCd("AF");
        product.setRewardsTypeShtCd("CB");
        product.setStatus("PENDING");
        product.setCreatedBy("admin");

        existingProduct = new Product();
        existingProduct.setProductId("GOL-AF-CB-0000001");
        existingProduct.setProductShtCd("GOL");
        existingProduct.setFeeTypeShtCd("AF");
        existingProduct.setRewardsTypeShtCd("CB");
        existingProduct.setStatus("ACTIVE");

        productOutDto = ProductOutDto.builder()
                .productId("GOL-AF-CB-0000001")
                .productShtCd("GOL")
                .feeTypeShtCd("AF")
                .rewardsTypeShtCd("CB")
                .status("PENDING")
                .createdBy("admin")
                .build();
    }

    @Test
    void testCreateProduct_Success() {
        // Arrange
        String createdBy = "admin";
        doNothing().when(productValidator).validateProductCreateRequest(createRequestDto);
        when(productRepository.findLatestProductIdByPrefix("GOL-AF-CB-")).thenReturn(Optional.empty());
        when(productMapper.toEntity(createRequestDto, "GOL-AF-CB-0000001", createdBy)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponseDto(product)).thenReturn(productOutDto);

        ProductOutDto result = productService.createProduct(createRequestDto, createdBy);

        assertNotNull(result);
        assertEquals("GOL-AF-CB-0000001", result.getProductId());
        assertEquals("GOL", result.getProductShtCd());
        assertEquals("AF", result.getFeeTypeShtCd());
        assertEquals("CB", result.getRewardsTypeShtCd());
        assertEquals("PENDING", result.getStatus());
        assertEquals("admin", result.getCreatedBy());

        verify(productValidator, times(1)).validateProductCreateRequest(createRequestDto);
        verify(productRepository, times(1)).findLatestProductIdByPrefix("GOL-AF-CB-");
        verify(productMapper, times(1)).toEntity(createRequestDto, "GOL-AF-CB-0000001", createdBy);
        verify(productRepository, times(1)).save(product);
        verify(productMapper, times(1)).toResponseDto(product);
    }

    @Test
    void testCreateProduct_WithExistingProducts_GeneratesNextSequence() {

        String createdBy = "admin";
        Product newProduct = new Product();
        newProduct.setProductId("GOL-AF-CB-0000003");

        ProductOutDto newProductOutDto = ProductOutDto.builder()
                .productId("GOL-AF-CB-0000003")
                .build();

        doNothing().when(productValidator).validateProductCreateRequest(createRequestDto);
        when(productRepository.findLatestProductIdByPrefix("GOL-AF-CB-"))
                .thenReturn(Optional.of("GOL-AF-CB-0000002"));
        when(productMapper.toEntity(createRequestDto, "GOL-AF-CB-0000003", createdBy)).thenReturn(newProduct);
        when(productRepository.save(newProduct)).thenReturn(newProduct);
        when(productMapper.toResponseDto(newProduct)).thenReturn(newProductOutDto);

        ProductOutDto result = productService.createProduct(createRequestDto, createdBy);

        assertNotNull(result);
        assertEquals("GOL-AF-CB-0000003", result.getProductId());

        verify(productValidator, times(1)).validateProductCreateRequest(createRequestDto);
        verify(productRepository, times(1)).findLatestProductIdByPrefix("GOL-AF-CB-");
    }

    @Test
    void testCreateProduct_ValidationFails_InvalidProductShortCode() {

        String createdBy = "admin";
        doThrow(new InvalidRequestException("Product Short Code 'XXX' does not exist in the system"))
                .when(productValidator).validateProductCreateRequest(createRequestDto);

        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.createProduct(createRequestDto, createdBy)
        );

        assertEquals("Product Short Code 'XXX' does not exist in the system", exception.getMessage());
        verify(productValidator, times(1)).validateProductCreateRequest(createRequestDto);
        verify(productRepository, never()).findLatestProductIdByPrefix(anyString());
        verify(productRepository, never()).save(any());
    }

    @Test
    void testCreateProduct_ValidationFails_InvalidFeeTypeCode() {

        String createdBy = "admin";
        doThrow(new InvalidRequestException("Fee Type Short Code 'XX' does not exist in the system"))
                .when(productValidator).validateProductCreateRequest(createRequestDto);

        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.createProduct(createRequestDto, createdBy)
        );

        assertEquals("Fee Type Short Code 'XX' does not exist in the system", exception.getMessage());
        verify(productValidator, times(1)).validateProductCreateRequest(createRequestDto);
        verify(productRepository, never()).save(any());
    }

    @Test
    void testCreateProduct_ValidationFails_InvalidAprTypeValueTypeCombination() {

        String createdBy = "admin";
        doThrow(new InvalidRequestException("When APR type is FIXED, APR value type must be SPECIFIC"))
                .when(productValidator).validateProductCreateRequest(createRequestDto);

        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.createProduct(createRequestDto, createdBy)
        );

        assertEquals("When APR type is FIXED, APR value type must be SPECIFIC", exception.getMessage());
        verify(productValidator, times(1)).validateProductCreateRequest(createRequestDto);
    }

    @Test
    void testCreateProduct_ValidationFails_InvalidCashAprCalculation() {

        String createdBy = "admin";
        doThrow(new InvalidRequestException("Cash APR Min is incorrect. Expected: 11.03 (Purchase APR Min + 5%), but received: 11.00"))
                .when(productValidator).validateProductCreateRequest(createRequestDto);


        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.createProduct(createRequestDto, createdBy)
        );

        assertTrue(exception.getMessage().contains("Cash APR Min is incorrect"));
        verify(productValidator, times(1)).validateProductCreateRequest(createRequestDto);
    }

    @Test
    void testCreateProduct_ValidationFails_InvalidSecurityDeposit() {

        String createdBy = "admin";
        doThrow(new InvalidRequestException("Security deposit min is required when security deposit is enabled"))
                .when(productValidator).validateProductCreateRequest(createRequestDto);


        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.createProduct(createRequestDto, createdBy)
        );

        assertEquals("Security deposit min is required when security deposit is enabled", exception.getMessage());
        verify(productValidator, times(1)).validateProductCreateRequest(createRequestDto);
    }

    @Test
    void testCreateProduct_ValidationFails_InvalidDates() {

        String createdBy = "admin";
        doThrow(new InvalidRequestException("Start date must be at least one week from today"))
                .when(productValidator).validateProductCreateRequest(createRequestDto);


        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.createProduct(createRequestDto, createdBy)
        );

        assertEquals("Start date must be at least one week from today", exception.getMessage());
        verify(productValidator, times(1)).validateProductCreateRequest(createRequestDto);
    }

    @Test
    void testCreateProduct_ValidationFails_InvalidApprover() {

        String createdBy = "admin";
        doThrow(new InvalidRequestException("Approver 'invalid_user' does not exist in the system"))
                .when(productValidator).validateProductCreateRequest(createRequestDto);


        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.createProduct(createRequestDto, createdBy)
        );

        assertEquals("Approver 'invalid_user' does not exist in the system", exception.getMessage());
        verify(productValidator, times(1)).validateProductCreateRequest(createRequestDto);
    }


    @Test
    void testUpdateProduct_Success() {

        String productId = "GOL-AF-CB-0000001";
        String updatedBy = "admin";
        Product newVersionProduct = new Product();
        newVersionProduct.setProductId("GOL-AF-CB-0000002");
        newVersionProduct.setStatus("REVISION_PENDING");

        ProductOutDto updatedProductOutDto = ProductOutDto.builder()
                .productId("GOL-AF-CB-0000002")
                .productShtCd("GOL")
                .feeTypeShtCd("AF")
                .rewardsTypeShtCd("CB")
                .status("REVISION_PENDING")
                .build();

        when(productValidator.validateProductIdAndGetProduct(productId)).thenReturn(existingProduct);
        doNothing().when(productValidator).validateNonEditableFields(existingProduct, updateRequestDto);
        doNothing().when(productValidator).validateProductUpdateRequest(updateRequestDto);
        when(productRepository.findLatestProductIdByPrefix("GOL-AF-CB-"))
                .thenReturn(Optional.of("GOL-AF-CB-0000001"));
        when(productMapper.toEntity(updateRequestDto, "GOL-AF-CB-0000002", updatedBy))
                .thenReturn(newVersionProduct);
        when(productRepository.save(newVersionProduct)).thenReturn(newVersionProduct);
        when(productMapper.toResponseDto(newVersionProduct)).thenReturn(updatedProductOutDto);


        ProductOutDto result = productService.updateProduct(productId, updateRequestDto, updatedBy);

        assertNotNull(result);
        assertEquals("GOL-AF-CB-0000002", result.getProductId());
        assertEquals("REVISION_PENDING", result.getStatus());

        verify(productValidator, times(1)).validateProductIdAndGetProduct(productId);
        verify(productValidator, times(1)).validateNonEditableFields(existingProduct, updateRequestDto);
        verify(productValidator, times(1)).validateProductUpdateRequest(updateRequestDto);
        verify(productRepository, times(1)).findLatestProductIdByPrefix("GOL-AF-CB-");
        verify(productRepository, times(1)).save(newVersionProduct);
    }

    @Test
    void testUpdateProduct_ProductNotFound() {

        String productId = "INVALID-ID";
        String updatedBy = "admin";

        when(productValidator.validateProductIdAndGetProduct(productId))
                .thenThrow(new ResourceNotFoundException("Product not found with ID: " + productId));


        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.updateProduct(productId, updateRequestDto, updatedBy)
        );

        assertEquals("Product not found with ID: INVALID-ID", exception.getMessage());
        verify(productValidator, times(1)).validateProductIdAndGetProduct(productId);
        verify(productValidator, never()).validateNonEditableFields(any(), any());
        verify(productValidator, never()).validateProductUpdateRequest(any());
        verify(productRepository, never()).save(any());
    }

    @Test
    void testUpdateProduct_AttemptToChangeProductShortCode() {

        String productId = "GOL-AF-CB-0000001";
        String updatedBy = "admin";
        updateRequestDto.setProductShtCd("PLU"); // Attempting to change

        when(productValidator.validateProductIdAndGetProduct(productId)).thenReturn(existingProduct);
        doThrow(new InvalidRequestException("Product Short Code cannot be changed. Original: GOL, Attempted: PLU"))
                .when(productValidator).validateNonEditableFields(existingProduct, updateRequestDto);

        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.updateProduct(productId, updateRequestDto, updatedBy)
        );

        assertTrue(exception.getMessage().contains("Product Short Code cannot be changed"));
        verify(productValidator, times(1)).validateProductIdAndGetProduct(productId);
        verify(productValidator, times(1)).validateNonEditableFields(existingProduct, updateRequestDto);
        verify(productValidator, never()).validateProductUpdateRequest(any());
        verify(productRepository, never()).save(any());
    }

    @Test
    void testUpdateProduct_AttemptToChangeFeeTypeCode() {

        String productId = "GOL-AF-CB-0000001";
        String updatedBy = "admin";
        updateRequestDto.setFeeTypeShtCd("MF"); // Attempting to change

        when(productValidator.validateProductIdAndGetProduct(productId)).thenReturn(existingProduct);
        doThrow(new InvalidRequestException("Fee Type Short Code cannot be changed. Original: AF, Attempted: MF"))
                .when(productValidator).validateNonEditableFields(existingProduct, updateRequestDto);

        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.updateProduct(productId, updateRequestDto, updatedBy)
        );

        assertTrue(exception.getMessage().contains("Fee Type Short Code cannot be changed"));
        verify(productValidator, times(1)).validateNonEditableFields(existingProduct, updateRequestDto);
    }

    @Test
    void testUpdateProduct_AttemptToChangeRewardsTypeCode() {
        String productId = "GOL-AF-CB-0000001";
        String updatedBy = "admin";
        updateRequestDto.setRewardsTypeShtCd("TR"); // Attempting to change

        when(productValidator.validateProductIdAndGetProduct(productId)).thenReturn(existingProduct);
        doThrow(new InvalidRequestException("Rewards Type Short Code cannot be changed. Original: CB, Attempted: TR"))
                .when(productValidator).validateNonEditableFields(existingProduct, updateRequestDto);

        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.updateProduct(productId, updateRequestDto, updatedBy)
        );

        assertTrue(exception.getMessage().contains("Rewards Type Short Code cannot be changed"));
        verify(productValidator, times(1)).validateNonEditableFields(existingProduct, updateRequestDto);
    }

    @Test
    void testUpdateProduct_ValidationFails_InvalidPrinCode() {

        String productId = "GOL-AF-CB-0000001";
        String updatedBy = "admin";

        when(productValidator.validateProductIdAndGetProduct(productId)).thenReturn(existingProduct);
        doNothing().when(productValidator).validateNonEditableFields(existingProduct, updateRequestDto);
        doThrow(new InvalidRequestException("PRIN Code 'PRINXXX' does not exist in the system"))
                .when(productValidator).validateProductUpdateRequest(updateRequestDto);

        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.updateProduct(productId, updateRequestDto, updatedBy)
        );

        assertEquals("PRIN Code 'PRINXXX' does not exist in the system", exception.getMessage());
        verify(productValidator, times(1)).validateProductUpdateRequest(updateRequestDto);
        verify(productRepository, never()).save(any());
    }

    @Test
    void testUpdateProduct_ValidationFails_InvalidCwsProductCode() {

        String productId = "GOL-AF-CB-0000001";
        String updatedBy = "admin";

        when(productValidator.validateProductIdAndGetProduct(productId)).thenReturn(existingProduct);
        doNothing().when(productValidator).validateNonEditableFields(existingProduct, updateRequestDto);
        doThrow(new InvalidRequestException("CWS Product Code 'CWS-XXX' does not exist in the system"))
                .when(productValidator).validateProductUpdateRequest(updateRequestDto);

        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.updateProduct(productId, updateRequestDto, updatedBy)
        );

        assertEquals("CWS Product Code 'CWS-XXX' does not exist in the system", exception.getMessage());
        verify(productValidator, times(1)).validateProductUpdateRequest(updateRequestDto);
    }

    @Test
    void testUpdateProduct_ValidationFails_InvalidChaCode() {

        String productId = "GOL-AF-CB-0000001";
        String updatedBy = "admin";

        when(productValidator.validateProductIdAndGetProduct(productId)).thenReturn(existingProduct);
        doNothing().when(productValidator).validateNonEditableFields(existingProduct, updateRequestDto);
        doThrow(new InvalidRequestException("CHA Code 'CHA-XXX' does not exist in the system"))
                .when(productValidator).validateProductUpdateRequest(updateRequestDto);

        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.updateProduct(productId, updateRequestDto, updatedBy)
        );

        assertEquals("CHA Code 'CHA-XXX' does not exist in the system", exception.getMessage());
        verify(productValidator, times(1)).validateProductUpdateRequest(updateRequestDto);
    }

    @Test
    void testUpdateProduct_ValidationFails_InvalidCreditLine() {

        String productId = "GOL-AF-CB-0000001";
        String updatedBy = "admin";

        when(productValidator.validateProductIdAndGetProduct(productId)).thenReturn(existingProduct);
        doNothing().when(productValidator).validateNonEditableFields(existingProduct, updateRequestDto);
        doThrow(new InvalidRequestException("Credit line max must be greater than credit line min"))
                .when(productValidator).validateProductUpdateRequest(updateRequestDto);

        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.updateProduct(productId, updateRequestDto, updatedBy)
        );

        assertEquals("Credit line max must be greater than credit line min", exception.getMessage());
        verify(productValidator, times(1)).validateProductUpdateRequest(updateRequestDto);
    }

    @Test
    void testUpdateProduct_EmptyProductId() {

        String productId = "";
        String updatedBy = "admin";

        when(productValidator.validateProductIdAndGetProduct(productId))
                .thenThrow(new InvalidRequestException("Product ID cannot be null or empty"));

        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> productService.updateProduct(productId, updateRequestDto, updatedBy)
        );

        assertEquals("Product ID cannot be null or empty", exception.getMessage());
        verify(productValidator, times(1)).validateProductIdAndGetProduct(productId);
    }

    @Test
    void testGetProductById_Success() {
        String productId = "PRD-001";
        Product getProduct = new Product();
        getProduct.setProductId("PRD-001");
        getProduct.setProductShtCd("ABC");
        getProduct.setFeeTypeShtCd("FT");
        getProduct.setRewardsTypeShtCd("RW");
        getProduct.setStatus("ACTIVE");

        ProductOutDto getProductOutDto = new ProductOutDto();
        getProductOutDto.setProductId("PRD-001");
        getProductOutDto.setProductShtCd("ABC");
        getProductOutDto.setStatus("ACTIVE");

        when(productValidator.validateProductIdAndGetProduct(productId)).thenReturn(getProduct);
        when(productMapper.toDto(getProduct)).thenReturn(getProductOutDto);

        ProductOutDto result = productService.getProductById(productId);

        assertNotNull(result);
        assertEquals("PRD-001", result.getProductId());
        assertEquals("ABC", result.getProductShtCd());
        assertEquals("ACTIVE", result.getStatus());

        verify(productValidator, times(1)).validateProductIdAndGetProduct(productId);
        verify(productMapper, times(1)).toDto(getProduct);
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
}