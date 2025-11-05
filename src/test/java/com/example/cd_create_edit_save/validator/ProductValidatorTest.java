package com.example.cd_create_edit_save.validator;

import com.example.cd_create_edit_save.exception.InvalidRequestException;
import com.example.cd_create_edit_save.exception.ResourceNotFoundException;
import com.example.cd_create_edit_save.model.dto.inDto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.inDto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.entity.Product;
import com.example.cd_create_edit_save.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductValidatorTest {

    @Mock private UserRepository userRepository;
    @Mock private ProductRepository productRepository;
    @Mock private ProductShortCodeRepository productShortCodeRepository;
    @Mock private FeeTypeShortCodeRepository feeTypeShortCodeRepository;
    @Mock private RewardsTypeShortCodeRepository rewardsTypeShortCodeRepository;
    @Mock private PrinCodeRepository prinCodeRepository;
    @Mock private CwsProdCodeRepository cwsProdCodeRepository;
    @Mock private ChaCodeRepository chaCodeRepository;

    @InjectMocks
    private ProductValidator productValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ==================== validateProductIdAndGetProduct Tests ====================

    @Test
    void testValidateProductIdAndGetProduct_Success() {
        Product product = new Product();
        product.setProductId("P123");
        when(productRepository.findById("P123")).thenReturn(Optional.of(product));

        Product result = productValidator.validateProductIdAndGetProduct("P123");

        assertNotNull(result);
        assertEquals("P123", result.getProductId());
        verify(productRepository).findById("P123");
    }

    @Test
    void testValidateProductIdAndGetProduct_NullId_ThrowsException() {
        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductIdAndGetProduct(null));
        assertEquals("Product ID cannot be null or empty", ex.getMessage());
        verify(productRepository, never()).findById(anyString());
    }

    @Test
    void testValidateProductIdAndGetProduct_EmptyId_ThrowsException() {
        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductIdAndGetProduct(""));
        assertEquals("Product ID cannot be null or empty", ex.getMessage());
        verify(productRepository, never()).findById(anyString());
    }

    @Test
    void testValidateProductIdAndGetProduct_WhitespaceId_ThrowsException() {
        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductIdAndGetProduct("   "));
        assertEquals("Product ID cannot be null or empty", ex.getMessage());
        verify(productRepository, never()).findById(anyString());
    }

    @Test
    void testValidateProductIdAndGetProduct_NotFound_ThrowsException() {
        when(productRepository.findById("P123")).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> productValidator.validateProductIdAndGetProduct("P123"));

        assertTrue(ex.getMessage().contains("Product not found with ID: P123"));
        verify(productRepository).findById("P123");
    }

    // ==================== validateProductCreateRequest Tests ====================

    @Test
    void testValidateProductCreateRequest_Success() {
        ProductCreateInDto dto = validCreateDto();

        setupAllCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductCreateRequest(dto));

        verify(productShortCodeRepository).existsByProductShortCode("GOL");
        verify(feeTypeShortCodeRepository).existsByFeeTypeShortCode("AF");
        verify(rewardsTypeShortCodeRepository).existsByRewardsTypeShortCode("CB");
        verify(prinCodeRepository).existsByPrinCode("PRIN01");
        verify(cwsProdCodeRepository).existsByCwsProdCode("CWS01");
        verify(chaCodeRepository).existsByChaCode("CHA01");
        verify(userRepository).existsByUsername("admin");
    }

    @Test
    void testValidateProductCreateRequest_InvalidProductShortCode() {
        ProductCreateInDto dto = validCreateDto();
        when(productShortCodeRepository.existsByProductShortCode("GOL")).thenReturn(false);

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductCreateRequest(dto));

        assertTrue(ex.getMessage().contains("Product Short Code 'GOL' does not exist"));
        verify(productShortCodeRepository).existsByProductShortCode("GOL");
    }

    @Test
    void testValidateProductCreateRequest_InvalidFeeTypeShortCode() {
        ProductCreateInDto dto = validCreateDto();
        when(productShortCodeRepository.existsByProductShortCode("GOL")).thenReturn(true);
        when(feeTypeShortCodeRepository.existsByFeeTypeShortCode("AF")).thenReturn(false);

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductCreateRequest(dto));

        assertTrue(ex.getMessage().contains("Fee Type Short Code 'AF' does not exist"));
    }

    @Test
    void testValidateProductCreateRequest_InvalidRewardsTypeShortCode() {
        ProductCreateInDto dto = validCreateDto();
        when(productShortCodeRepository.existsByProductShortCode("GOL")).thenReturn(true);
        when(feeTypeShortCodeRepository.existsByFeeTypeShortCode("AF")).thenReturn(true);
        when(rewardsTypeShortCodeRepository.existsByRewardsTypeShortCode("CB")).thenReturn(false);

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductCreateRequest(dto));

        assertTrue(ex.getMessage().contains("Rewards Type Short Code 'CB' does not exist"));
    }

    @Test
    void testValidateProductCreateRequest_InvalidPrinCode() {
        ProductCreateInDto dto = validCreateDto();
        when(productShortCodeRepository.existsByProductShortCode(anyString())).thenReturn(true);
        when(feeTypeShortCodeRepository.existsByFeeTypeShortCode(anyString())).thenReturn(true);
        when(rewardsTypeShortCodeRepository.existsByRewardsTypeShortCode(anyString())).thenReturn(true);
        when(prinCodeRepository.existsByPrinCode("PRIN01")).thenReturn(false);

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductCreateRequest(dto));

        assertTrue(ex.getMessage().contains("PRIN Code 'PRIN01' does not exist"));
    }

    @Test
    void testValidateProductCreateRequest_InvalidCwsProductCode() {
        ProductCreateInDto dto = validCreateDto();
        when(productShortCodeRepository.existsByProductShortCode(anyString())).thenReturn(true);
        when(feeTypeShortCodeRepository.existsByFeeTypeShortCode(anyString())).thenReturn(true);
        when(rewardsTypeShortCodeRepository.existsByRewardsTypeShortCode(anyString())).thenReturn(true);
        when(prinCodeRepository.existsByPrinCode(anyString())).thenReturn(true);
        when(cwsProdCodeRepository.existsByCwsProdCode("CWS01")).thenReturn(false);

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductCreateRequest(dto));

        assertTrue(ex.getMessage().contains("CWS Product Code 'CWS01' does not exist"));
    }

    @Test
    void testValidateProductCreateRequest_InvalidChaCode() {
        ProductCreateInDto dto = validCreateDto();
        when(productShortCodeRepository.existsByProductShortCode(anyString())).thenReturn(true);
        when(feeTypeShortCodeRepository.existsByFeeTypeShortCode(anyString())).thenReturn(true);
        when(rewardsTypeShortCodeRepository.existsByRewardsTypeShortCode(anyString())).thenReturn(true);
        when(prinCodeRepository.existsByPrinCode(anyString())).thenReturn(true);
        when(cwsProdCodeRepository.existsByCwsProdCode(anyString())).thenReturn(true);
        when(chaCodeRepository.existsByChaCode("CHA01")).thenReturn(false);

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductCreateRequest(dto));

        assertTrue(ex.getMessage().contains("CHA Code 'CHA01' does not exist"));
    }

    // ==================== validateProductUpdateRequest Tests ====================

    @Test
    void testValidateProductUpdateRequest_Success() {
        ProductUpdateInDto dto = validUpdateDto();

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));

        verify(prinCodeRepository).existsByPrinCode("PRIN01");
        verify(cwsProdCodeRepository).existsByCwsProdCode("CWS01");
        verify(chaCodeRepository).existsByChaCode("CHA01");
        verify(userRepository).existsByUsername("admin");
    }

    // ==================== validateNonEditableFields Tests ====================

    @Test
    void testValidateNonEditableFields_Success() {
        Product existing = new Product();
        existing.setProductShtCd("GOL");
        existing.setFeeTypeShtCd("AF");
        existing.setRewardsTypeShtCd("CB");

        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd("GOL");
        dto.setFeeTypeShtCd("AF");
        dto.setRewardsTypeShtCd("CB");

        assertDoesNotThrow(() -> productValidator.validateNonEditableFields(existing, dto));
    }

    @Test
    void testValidateNonEditableFields_ProductShortCodeChanged_ThrowsException() {
        Product existing = new Product();
        existing.setProductShtCd("GOL");
        existing.setFeeTypeShtCd("AF");
        existing.setRewardsTypeShtCd("CB");

        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd("PLU");
        dto.setFeeTypeShtCd("AF");
        dto.setRewardsTypeShtCd("CB");

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateNonEditableFields(existing, dto));

        assertTrue(ex.getMessage().contains("Product Short Code cannot be changed"));
        assertTrue(ex.getMessage().contains("Original: GOL"));
        assertTrue(ex.getMessage().contains("Attempted: PLU"));
    }

    @Test
    void testValidateNonEditableFields_FeeTypeChanged_ThrowsException() {
        Product existing = new Product();
        existing.setProductShtCd("GOL");
        existing.setFeeTypeShtCd("AF");
        existing.setRewardsTypeShtCd("CB");

        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd("GOL");
        dto.setFeeTypeShtCd("MF");
        dto.setRewardsTypeShtCd("CB");

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateNonEditableFields(existing, dto));

        assertTrue(ex.getMessage().contains("Fee Type Short Code cannot be changed"));
        assertTrue(ex.getMessage().contains("Original: AF"));
        assertTrue(ex.getMessage().contains("Attempted: MF"));
    }

    @Test
    void testValidateNonEditableFields_RewardsTypeChanged_ThrowsException() {
        Product existing = new Product();
        existing.setProductShtCd("GOL");
        existing.setFeeTypeShtCd("AF");
        existing.setRewardsTypeShtCd("CB");

        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd("GOL");
        dto.setFeeTypeShtCd("AF");
        dto.setRewardsTypeShtCd("TR");

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateNonEditableFields(existing, dto));

        assertTrue(ex.getMessage().contains("Rewards Type Short Code cannot be changed"));
        assertTrue(ex.getMessage().contains("Original: CB"));
        assertTrue(ex.getMessage().contains("Attempted: TR"));
    }

    // ==================== APR Type and Value Type Tests ====================

    @Test
    void testValidateAprTypeAndValueType_FixedAndSpecific_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprType("FIXED");
        dto.setAprValueType("SPECIFIC");

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateAprTypeAndValueType_VariableAndRange_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprType("VARIABLE");
        dto.setAprValueType("RANGE");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(15));
        dto.setCashAprMin(BigDecimal.valueOf(10.5));
        dto.setCashAprMax(BigDecimal.valueOf(15.75));

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateAprTypeAndValueType_FixedWithRange_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprType("FIXED");
        dto.setAprValueType("RANGE");

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("When APR type is FIXED, APR value type must be SPECIFIC", ex.getMessage());
    }

    @Test
    void testValidateAprTypeAndValueType_VariableWithSpecific_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprType("VARIABLE");
        dto.setAprValueType("SPECIFIC");

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("When APR type is VARIABLE, APR value type must be RANGE", ex.getMessage());
    }

    // ==================== APR Min/Max Tests ====================

    @Test
    void testValidateAprMinMax_SpecificWithEqualValues_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprType("FIXED");
        dto.setAprValueType("SPECIFIC");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10));
        dto.setCashAprMin(BigDecimal.valueOf(10.5));
        dto.setCashAprMax(BigDecimal.valueOf(10.5));

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateAprMinMax_SpecificWithDifferentValues_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprType("FIXED");
        dto.setAprValueType("SPECIFIC");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(15));

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("For SPECIFIC APR value type, Purchase APR Min and Max must be equal", ex.getMessage());
    }

    @Test
    void testValidateAprMinMax_RangeWithMinGreaterThanMax_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprType("VARIABLE");
        dto.setAprValueType("RANGE");
        dto.setPurchaseAprMin(BigDecimal.valueOf(20));
        dto.setPurchaseAprMax(BigDecimal.valueOf(15));

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("For RANGE APR value type, Purchase APR Max must be greater than Min", ex.getMessage());
    }

    @Test
    void testValidateAprMinMax_RangeWithEqualValues_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprType("VARIABLE");
        dto.setAprValueType("RANGE");
        dto.setPurchaseAprMin(BigDecimal.valueOf(15));
        dto.setPurchaseAprMax(BigDecimal.valueOf(15));

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("For RANGE APR value type, Purchase APR Max must be greater than Min", ex.getMessage());
    }

    // ==================== Cash APR Tests ====================

    @Test
    void testVerifyCashApr_CorrectCalculation_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10));
        dto.setCashAprMin(BigDecimal.valueOf(10.50));
        dto.setCashAprMax(BigDecimal.valueOf(10.50));

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testVerifyCashApr_IncorrectMinCalculation_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10));
        dto.setCashAprMin(BigDecimal.valueOf(11.00)); // Should be 10.50
        dto.setCashAprMax(BigDecimal.valueOf(10.50));

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertTrue(ex.getMessage().contains("Cash APR Min is incorrect"));
    }

    @Test
    void testVerifyCashApr_IncorrectMaxCalculation_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10));
        dto.setCashAprMin(BigDecimal.valueOf(10.50));
        dto.setCashAprMax(BigDecimal.valueOf(11.00)); // Should be 10.50

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertTrue(ex.getMessage().contains("Cash APR Max is incorrect"));
    }

    // ==================== Credit Line Tests ====================

    @Test
    void testValidateCreditLine_ValidRange_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setCreditLineMin(1000);
        dto.setCreditLineMax(5000);

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateCreditLine_MinGreaterThanMax_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setCreditLineMin(5000);
        dto.setCreditLineMax(1000);

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("Credit line max must be greater than credit line min", ex.getMessage());
    }

    @Test
    void testValidateCreditLine_MinEqualMax_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setCreditLineMin(1000);
        dto.setCreditLineMax(1000);

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("Credit line max must be greater than credit line min", ex.getMessage());
    }

    // ==================== Security Deposit Tests ====================

    @Test
    void testValidateSecurityDeposit_IndicatorY_ValidRange_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator("Y");
        dto.setSecurityDepositMin(200);
        dto.setSecurityDepositMax(500);

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateSecurityDeposit_IndicatorY_MinNull_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator("Y");
        dto.setSecurityDepositMin(null);
        dto.setSecurityDepositMax(500);

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("Security deposit min is required when security deposit is enabled", ex.getMessage());
    }

    @Test
    void testValidateSecurityDeposit_IndicatorY_MaxNull_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator("Y");
        dto.setSecurityDepositMin(200);
        dto.setSecurityDepositMax(null);

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("Security deposit max is required when security deposit is enabled", ex.getMessage());
    }

    @Test
    void testValidateSecurityDeposit_IndicatorY_MinGreaterThanMax_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator("Y");
        dto.setSecurityDepositMin(500);
        dto.setSecurityDepositMax(200);

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("Security deposit max must be greater than security deposit min", ex.getMessage());
    }

    @Test
    void testValidateSecurityDeposit_IndicatorY_MinEqualMax_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator("Y");
        dto.setSecurityDepositMin(500);
        dto.setSecurityDepositMax(500);

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("Security deposit max must be greater than security deposit min", ex.getMessage());
    }

    @Test
    void testValidateSecurityDeposit_IndicatorN_MinNotNull_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator("N");
        dto.setSecurityDepositMin(200);
        dto.setSecurityDepositMax(null);

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("Security deposit min must be null when security deposit is not required", ex.getMessage());
    }

    @Test
    void testValidateSecurityDeposit_IndicatorN_MaxNotNull_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator("N");
        dto.setSecurityDepositMin(null);
        dto.setSecurityDepositMax(500);

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("Security deposit max must be null when security deposit is not required", ex.getMessage());
    }

    @Test
    void testValidateSecurityDeposit_IndicatorNull_NoValidation() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator(null);
        dto.setSecurityDepositMin(null);
        dto.setSecurityDepositMax(null);

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    // ==================== Date Validation Tests ====================

    @Test
    void testValidateDates_ValidDates_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setStartDate(LocalDateTime.now().plusWeeks(2));
        dto.setEndDate(LocalDateTime.now().plusMonths(6));

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateDates_StartDateTooSoon_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setStartDate(LocalDateTime.now().plusDays(3));
        dto.setEndDate(LocalDateTime.now().plusMonths(6));

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertTrue(ex.getMessage().contains("Start date must be at least one week from today"));
    }

    @Test
    void testValidateDates_EndDateBeforeStartDate_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setStartDate(LocalDateTime.now().plusWeeks(3));
        dto.setEndDate(LocalDateTime.now().plusWeeks(2));

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("End date must be after start date (not equal)", ex.getMessage());
    }

    @Test
    void testValidateDates_EndDateEqualStartDate_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        LocalDateTime sameDate = LocalDateTime.now().plusWeeks(2);
        dto.setStartDate(sameDate);
        dto.setEndDate(sameDate);

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("End date must be after start date (not equal)", ex.getMessage());
    }

    // ==================== Approver Validation Tests ====================

    @Test
    void testValidateApprover_ApproverExists_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setToBeApprovedBy("admin");

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));

        verify(userRepository).existsByUsername("admin");
    }

    @Test
    void testValidateApprover_ApproverNotFound_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setToBeApprovedBy("invalidUser");

        when(prinCodeRepository.existsByPrinCode(anyString())).thenReturn(true);
        when(cwsProdCodeRepository.existsByCwsProdCode(anyString())).thenReturn(true);
        when(chaCodeRepository.existsByChaCode(anyString())).thenReturn(true);
        when(userRepository.existsByUsername("invalidUser")).thenReturn(false);

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("Approver 'invalidUser' does not exist in the system. Please select a valid user.", ex.getMessage());
    }

    // ==================== Helper Methods ====================

    private void setupAllCodeMocksAsValid() {
        when(productShortCodeRepository.existsByProductShortCode(anyString())).thenReturn(true);
        when(feeTypeShortCodeRepository.existsByFeeTypeShortCode(anyString())).thenReturn(true);
        when(rewardsTypeShortCodeRepository.existsByRewardsTypeShortCode(anyString())).thenReturn(true);
        when(prinCodeRepository.existsByPrinCode(anyString())).thenReturn(true);
        when(cwsProdCodeRepository.existsByCwsProdCode(anyString())).thenReturn(true);
        when(chaCodeRepository.existsByChaCode(anyString())).thenReturn(true);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
    }

    private void setupUpdateCodeMocksAsValid() {
        when(prinCodeRepository.existsByPrinCode(anyString())).thenReturn(true);
        when(cwsProdCodeRepository.existsByCwsProdCode(anyString())).thenReturn(true);
        when(chaCodeRepository.existsByChaCode(anyString())).thenReturn(true);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
    }

    private ProductCreateInDto validCreateDto() {
        ProductCreateInDto dto = new ProductCreateInDto();
        dto.setProductShtCd("GOL");
        dto.setFeeTypeShtCd("AF");
        dto.setRewardsTypeShtCd("CB");
        dto.setPrin("PRIN01");
        dto.setCwsProductId("CWS01");
        dto.setChaCode("CHA01");
        dto.setAprType("FIXED");
        dto.setAprValueType("SPECIFIC");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10));
        dto.setCashAprMin(BigDecimal.valueOf(10.5));
        dto.setCashAprMax(BigDecimal.valueOf(10.5));
        dto.setCreditLineMin(1000);
        dto.setCreditLineMax(5000);
        dto.setSecurityDepositIndicator("N");
        dto.setSecurityDepositMin(null);
        dto.setSecurityDepositMax(null);
        dto.setStartDate(LocalDateTime.now().plusWeeks(2));
        dto.setEndDate(LocalDateTime.now().plusMonths(6));
        dto.setToBeApprovedBy("admin");
        return dto;
    }

    private ProductUpdateInDto validUpdateDto() {
        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setPrin("PRIN01");
        dto.setCwsProductId("CWS01");
        dto.setChaCode("CHA01");
        dto.setAprType("FIXED");
        dto.setAprValueType("SPECIFIC");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10));
        dto.setCashAprMin(BigDecimal.valueOf(10.5));
        dto.setCashAprMax(BigDecimal.valueOf(10.5));
        dto.setCreditLineMin(100);
        dto.setCreditLineMax(200);
        dto.setSecurityDepositIndicator("N");
        dto.setSecurityDepositMin(null);
        dto.setSecurityDepositMax(null);
        dto.setStartDate(LocalDateTime.now().plusWeeks(2));
        dto.setEndDate(LocalDateTime.now().plusWeeks(3));
        dto.setToBeApprovedBy("admin");
        return dto;
    }
}