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
    @Mock private FeeValuesRepository feeValuesRepository;

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
        existing.setAprType("FIXED");

        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd("GOL");
        dto.setFeeTypeShtCd("AF");
        dto.setRewardsTypeShtCd("CB");
        dto.setAprType("FIXED");

        assertDoesNotThrow(() -> productValidator.validateNonEditableFields(existing, dto));
    }

    @Test
    void testValidateNonEditableFields_ProductShortCodeChanged_ThrowsException() {
        Product existing = new Product();
        existing.setProductShtCd("GOL");
        existing.setFeeTypeShtCd("AF");
        existing.setRewardsTypeShtCd("CB");
        existing.setAprType("FIXED");

        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd("PLU");
        dto.setFeeTypeShtCd("AF");
        dto.setRewardsTypeShtCd("CB");
        dto.setAprType("FIXED");

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
        existing.setAprType("FIXED");

        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd("GOL");
        dto.setFeeTypeShtCd("MF");
        dto.setRewardsTypeShtCd("CB");
        dto.setAprType("FIXED");

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
        existing.setAprType("FIXED");

        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd("GOL");
        dto.setFeeTypeShtCd("AF");
        dto.setRewardsTypeShtCd("TR");
        dto.setAprType("FIXED");

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateNonEditableFields(existing, dto));

        assertTrue(ex.getMessage().contains("Rewards Type Short Code cannot be changed"));
        assertTrue(ex.getMessage().contains("Original: CB"));
        assertTrue(ex.getMessage().contains("Attempted: TR"));
    }

    @Test
    void testValidateNonEditableFields_AprTypeChanged_ThrowsException() {
        Product existing = new Product();
        existing.setProductShtCd("GOL");
        existing.setFeeTypeShtCd("AF");
        existing.setRewardsTypeShtCd("CB");
        existing.setAprType("FIXED");

        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd("GOL");
        dto.setFeeTypeShtCd("AF");
        dto.setRewardsTypeShtCd("CB");
        dto.setAprType("VARIABLE");

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateNonEditableFields(existing, dto));

        assertTrue(ex.getMessage().contains("APR Type cannot be changed"));
        assertTrue(ex.getMessage().contains("Original: FIXED"));
        assertTrue(ex.getMessage().contains("Attempted: VARIABLE"));
    }

    // ==================== validateFieldChanges Tests ====================

    @Test
    void testValidateFieldChanges_OnlyDatesChanged_ThrowsException() {
        Product existing = createFullExistingProduct();
        ProductUpdateInDto dto = createMatchingUpdateDto(existing);

        // Only change dates
        dto.setStartDate(LocalDateTime.now().plusWeeks(3));
        dto.setEndDate(LocalDateTime.now().plusWeeks(4));

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateFieldChanges(existing, dto));

        assertTrue(ex.getMessage().contains("Cannot create new product version with only date changes"));
    }

    @Test
    void testValidateFieldChanges_NoChanges_ThrowsException() {
        Product existing = createFullExistingProduct();
        ProductUpdateInDto dto = createMatchingUpdateDto(existing);

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateFieldChanges(existing, dto));

        assertTrue(ex.getMessage().contains("No changes detected"));
    }

    @Test
    void testValidateFieldChanges_OtherFieldsChanged_Success() {
        Product existing = createFullExistingProduct();
        ProductUpdateInDto dto = createMatchingUpdateDto(existing);

        // Change a non-date field
        dto.setCreditLineMin(2000);

        assertDoesNotThrow(() -> productValidator.validateFieldChanges(existing, dto));
    }

    @Test
    void testValidateFieldChanges_DatesAndOtherFieldsChanged_Success() {
        Product existing = createFullExistingProduct();
        ProductUpdateInDto dto = createMatchingUpdateDto(existing);

        // Change both dates and other fields
        dto.setStartDate(LocalDateTime.now().plusWeeks(3));
        dto.setCreditLineMin(2000);

        assertDoesNotThrow(() -> productValidator.validateFieldChanges(existing, dto));
    }

    // ==================== Fee Value Validation Tests ====================

    @Test
    void testValidateFeeValue_NoFee_NullValue_Success() {
        ProductCreateInDto dto = validCreateDto();
        dto.setFeeTypeShtCd("NF");
        dto.setFeeValue(null);

        setupAllCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductCreateRequest(dto));
    }

    @Test
    void testValidateFeeValue_NoFee_ZeroValue_Success() {
        ProductCreateInDto dto = validCreateDto();
        dto.setFeeTypeShtCd("NF");
        dto.setFeeValue(BigDecimal.ZERO);

        setupAllCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductCreateRequest(dto));
    }

    @Test
    void testValidateFeeValue_NoFee_NonZeroValue_ThrowsException() {
        ProductCreateInDto dto = validCreateDto();
        dto.setFeeTypeShtCd("NF");
        dto.setFeeValue(BigDecimal.valueOf(50));

        setupAllCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductCreateRequest(dto));

        assertTrue(ex.getMessage().contains("Fee value must be null or 0 when fee type is NONE"));
    }

    @Test
    void testValidateFeeValue_AnnualFee_ValidValue_Success() {
        ProductCreateInDto dto = validCreateDto();
        dto.setFeeTypeShtCd("AF");
        dto.setFeeValue(BigDecimal.valueOf(95));

        setupAllCodeMocksAsValid();
        when(feeValuesRepository.existsByFeeValueAndFeeType(BigDecimal.valueOf(95), "ANNUAL")).thenReturn(true);

        assertDoesNotThrow(() -> productValidator.validateProductCreateRequest(dto));

        verify(feeValuesRepository).existsByFeeValueAndFeeType(BigDecimal.valueOf(95), "ANNUAL");
    }

    @Test
    void testValidateFeeValue_AnnualFee_NullValue_ThrowsException() {
        ProductCreateInDto dto = validCreateDto();
        dto.setFeeTypeShtCd("AF");
        dto.setFeeValue(null);

        setupAllCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductCreateRequest(dto));

        assertTrue(ex.getMessage().contains("Fee value is required and must be greater than 0 for ANNUAL fee type"));
    }

    @Test
    void testValidateFeeValue_AnnualFee_ZeroValue_ThrowsException() {
        ProductCreateInDto dto = validCreateDto();
        dto.setFeeTypeShtCd("AF");
        dto.setFeeValue(BigDecimal.ZERO);

        setupAllCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductCreateRequest(dto));

        assertTrue(ex.getMessage().contains("Fee value is required and must be greater than 0 for ANNUAL fee type"));
    }

    @Test
    void testValidateFeeValue_AnnualFee_ValueNotInDatabase_ThrowsException() {
        ProductCreateInDto dto = validCreateDto();
        dto.setFeeTypeShtCd("AF");
        dto.setFeeValue(BigDecimal.valueOf(95));

        setupAllCodeMocksAsValid();
        when(feeValuesRepository.existsByFeeValueAndFeeType(BigDecimal.valueOf(95), "ANNUAL")).thenReturn(false);

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductCreateRequest(dto));

        assertTrue(ex.getMessage().contains("Fee value 95 does not exist in the system for ANNUAL fee type"));
    }

    @Test
    void testValidateFeeValue_MonthlyFee_ValidValue_Success() {
        ProductCreateInDto dto = validCreateDto();
        dto.setFeeTypeShtCd("MF");
        dto.setFeeValue(BigDecimal.valueOf(9.95));

        setupAllCodeMocksAsValid();
        when(feeValuesRepository.existsByFeeValueAndFeeType(BigDecimal.valueOf(9.95), "MONTHLY")).thenReturn(true);

        assertDoesNotThrow(() -> productValidator.validateProductCreateRequest(dto));

        verify(feeValuesRepository).existsByFeeValueAndFeeType(BigDecimal.valueOf(9.95), "MONTHLY");
    }

    @Test
    void testValidateFeeValue_MonthlyFee_NullValue_ThrowsException() {
        ProductCreateInDto dto = validCreateDto();
        dto.setFeeTypeShtCd("MF");
        dto.setFeeValue(null);

        setupAllCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductCreateRequest(dto));

        assertTrue(ex.getMessage().contains("Fee value is required and must be greater than 0 for MONTHLY fee type"));
    }

    @Test
    void testValidateFeeValue_MonthlyFee_ValueNotInDatabase_ThrowsException() {
        ProductCreateInDto dto = validCreateDto();
        dto.setFeeTypeShtCd("MF");
        dto.setFeeValue(BigDecimal.valueOf(9.95));

        setupAllCodeMocksAsValid();
        when(feeValuesRepository.existsByFeeValueAndFeeType(BigDecimal.valueOf(9.95), "MONTHLY")).thenReturn(false);

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductCreateRequest(dto));

        assertTrue(ex.getMessage().contains("Fee value 9.95 does not exist in the system for MONTHLY fee type"));
    }

    // ==================== APR Min/Max Tests ====================

    @Test
    void testValidateAprMinMax_SpecificWithEqualValues_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprValueType("SPECIFIC");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10));
        dto.setCashAprMin(BigDecimal.valueOf(15));
        dto.setCashAprMax(BigDecimal.valueOf(15));

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateAprMinMax_SpecificWithDifferentValues_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprValueType("SPECIFIC");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(15));

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("For SPECIFIC APR value type, Purchase APR Min and Max must be equal", ex.getMessage());
    }

    @Test
    void testValidateAprMinMax_RangeWithValidRange_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprValueType("RANGE");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(15));
        dto.setCashAprMin(BigDecimal.valueOf(15));
        dto.setCashAprMax(BigDecimal.valueOf(20));

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateAprMinMax_RangeWithMinGreaterThanMax_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
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
        dto.setPurchaseAprMin(BigDecimal.valueOf(10.00));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10.00));
        dto.setCashAprMin(BigDecimal.valueOf(15.00));
        dto.setCashAprMax(BigDecimal.valueOf(15.00));

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testVerifyCashApr_IncorrectMinCalculation_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setPurchaseAprMin(BigDecimal.valueOf(10.00));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10.00));
        dto.setCashAprMin(BigDecimal.valueOf(16.00)); // Should be 15.00
        dto.setCashAprMax(BigDecimal.valueOf(15.00));

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertTrue(ex.getMessage().contains("Cash APR Min must be exactly 15.00"));
    }

    @Test
    void testVerifyCashApr_IncorrectMaxCalculation_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setPurchaseAprMin(BigDecimal.valueOf(10.00));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10.00));
        dto.setCashAprMin(BigDecimal.valueOf(15.00));
        dto.setCashAprMax(BigDecimal.valueOf(16.00)); // Should be 15.00

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertTrue(ex.getMessage().contains("Cash APR Max must be exactly 15.00"));
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

        assertTrue(ex.getMessage().contains("Security deposit min is required and must be greater than 0 when security deposit is enabled"));
    }

    @Test
    void testValidateSecurityDeposit_IndicatorY_MinZero_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator("Y");
        dto.setSecurityDepositMin(0);
        dto.setSecurityDepositMax(500);

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertTrue(ex.getMessage().contains("Security deposit min is required and must be greater than 0 when security deposit is enabled"));
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

        assertTrue(ex.getMessage().contains("Security deposit max is required and must be greater than 0 when security deposit is enabled"));
    }

    @Test
    void testValidateSecurityDeposit_IndicatorY_MaxZero_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator("Y");
        dto.setSecurityDepositMin(200);
        dto.setSecurityDepositMax(0);

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertTrue(ex.getMessage().contains("Security deposit max is required and must be greater than 0 when security deposit is enabled"));
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
    void testValidateSecurityDeposit_IndicatorN_BothNull_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator("N");
        dto.setSecurityDepositMin(null);
        dto.setSecurityDepositMax(null);

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateSecurityDeposit_IndicatorN_BothZero_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator("N");
        dto.setSecurityDepositMin(0);
        dto.setSecurityDepositMax(0);

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateSecurityDeposit_IndicatorN_MinNotNullOrZero_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator("N");
        dto.setSecurityDepositMin(200);
        dto.setSecurityDepositMax(null);

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertTrue(ex.getMessage().contains("Security deposit min must be null or 0 when security deposit is not required"));
    }

    @Test
    void testValidateSecurityDeposit_IndicatorN_MaxNotNullOrZero_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator("N");
        dto.setSecurityDepositMin(null);
        dto.setSecurityDepositMax(500);

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertTrue(ex.getMessage().contains("Security deposit max must be null or 0 when security deposit is not required"));
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
    void testValidateDates_StartDateExactlyOneWeekFromNow_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setStartDate(LocalDateTime.now().plusWeeks(1));
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
    void testValidateDates_StartDateToday_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setStartDate(LocalDateTime.now());
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

    @Test
    void testValidateDates_EndDateOneDayAfterStart_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        LocalDateTime startDate = LocalDateTime.now().plusWeeks(2);
        dto.setStartDate(startDate);
        dto.setEndDate(startDate.plusDays(1));

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
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

        assertTrue(ex.getMessage().contains("Approver 'invalidUser' does not exist in the system"));
    }

    @Test
    void testValidateApprover_EmptyApproverName_ValidationFails() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setToBeApprovedBy("");

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }
    @Test
    void testValidateProductCreateRequest_Success() {
        ProductCreateInDto dto = validCreateDto();
        dto.setFeeTypeShtCd("AF");
        dto.setFeeValue(BigDecimal.valueOf(95));

        setupAllCodeMocksAsValid();
        when(feeValuesRepository.existsByFeeValueAndFeeType(BigDecimal.valueOf(95), "ANNUAL"))
                .thenReturn(true);

        assertDoesNotThrow(() -> productValidator.validateProductCreateRequest(dto));

        verify(productShortCodeRepository).existsByProductShortCode("GOL");
        verify(feeTypeShortCodeRepository).existsByFeeTypeShortCode("AF");
        verify(rewardsTypeShortCodeRepository).existsByRewardsTypeShortCode("CB");
        verify(prinCodeRepository).existsByPrinCode("PRIN01");
        verify(cwsProdCodeRepository).existsByCwsProdCode("CWS01");
        verify(chaCodeRepository).existsByChaCode("CHA01");
        verify(userRepository).existsByUsername("admin");
        verify(feeValuesRepository).existsByFeeValueAndFeeType(BigDecimal.valueOf(95), "ANNUAL");
    }

    @Test
    void testValidateProductCreateRequest_InvalidFeeTypeShortCode() {
        ProductCreateInDto dto = validCreateDto();
        dto.setProductShtCd("GOL");
        dto.setFeeTypeShtCd("AF");

        when(productShortCodeRepository.existsByProductShortCode("GOL")).thenReturn(true);
        when(feeTypeShortCodeRepository.existsByFeeTypeShortCode("AF")).thenReturn(false);

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductCreateRequest(dto));

        assertTrue(ex.getMessage().contains("Fee Type Short Code 'AF' does not exist"));
        verify(feeTypeShortCodeRepository).existsByFeeTypeShortCode("AF");
    }

    @Test
    void testValidateProductCreateRequest_InvalidRewardsTypeShortCode() {
        ProductCreateInDto dto = validCreateDto();
        dto.setProductShtCd("GOL");
        dto.setFeeTypeShtCd("AF");

        when(productShortCodeRepository.existsByProductShortCode(anyString())).thenReturn(true);
        when(feeTypeShortCodeRepository.existsByFeeTypeShortCode(anyString())).thenReturn(true);
        when(rewardsTypeShortCodeRepository.existsByRewardsTypeShortCode("CB")).thenReturn(false);

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductCreateRequest(dto));

        assertTrue(ex.getMessage().contains("Rewards Type Short Code 'CB' does not exist"));
        verify(rewardsTypeShortCodeRepository).existsByRewardsTypeShortCode("CB");
    }

    @Test
    void testVerifyCashApr_WithDecimals_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setPurchaseAprMin(BigDecimal.valueOf(12.50));
        dto.setPurchaseAprMax(BigDecimal.valueOf(12.50));
        dto.setCashAprMin(BigDecimal.valueOf(17.50));
        dto.setCashAprMax(BigDecimal.valueOf(17.50));

        setupUpdateCodeMocksAsValid();
        // ensure any feeValue/fee checks (if AF) won't block the test
        when(feeValuesRepository.existsByFeeValueAndFeeType(any(), anyString())).thenReturn(true);

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
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
        dto.setFeeTypeShtCd("NF");
        dto.setRewardsTypeShtCd("CB");
        dto.setPrin("PRIN01");
        dto.setCwsProductId("CWS01");
        dto.setChaCode("CHA01");
        dto.setAprType("FIXED");
        dto.setAprValueType("SPECIFIC");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10.00));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10.00));
        dto.setCashAprMin(BigDecimal.valueOf(15.00));
        dto.setCashAprMax(BigDecimal.valueOf(15.00));
        dto.setCreditLineMin(1000);
        dto.setCreditLineMax(5000);
        dto.setSecurityDepositIndicator("N");
        dto.setSecurityDepositMin(null);
        dto.setSecurityDepositMax(null);
        dto.setStartDate(LocalDateTime.now().plusWeeks(2));
        dto.setEndDate(LocalDateTime.now().plusMonths(6));
        dto.setToBeApprovedBy("admin");
        dto.setFeeValue(null);
        dto.setTermsConditionsLink("https://example.com/terms");
        dto.setCardholderAgreementLink("https://example.com/agreement");
        dto.setCardImageLink("https://example.com/image.jpg");
        dto.setUpc("UPC123");
        return dto;
    }

    private ProductUpdateInDto validUpdateDto() {
        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd("GOL");
        dto.setFeeTypeShtCd("NF");
        dto.setRewardsTypeShtCd("CB");
        dto.setPrin("PRIN01");
        dto.setCwsProductId("CWS01");
        dto.setChaCode("CHA01");
        dto.setAprType("FIXED");
        dto.setAprValueType("SPECIFIC");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10.00));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10.00));
        dto.setCashAprMin(BigDecimal.valueOf(15.00));
        dto.setCashAprMax(BigDecimal.valueOf(15.00));
        dto.setCreditLineMin(1000);
        dto.setCreditLineMax(5000);
        dto.setSecurityDepositIndicator("N");
        dto.setSecurityDepositMin(null);
        dto.setSecurityDepositMax(null);
        dto.setStartDate(LocalDateTime.now().plusWeeks(2));
        dto.setEndDate(LocalDateTime.now().plusWeeks(3));
        dto.setToBeApprovedBy("admin");
        dto.setFeeValue(null);
        dto.setTermsConditionsLink("https://example.com/terms");
        dto.setCardholderAgreementLink("https://example.com/agreement");
        dto.setCardImageLink("https://example.com/image.jpg");
        dto.setUpc("UPC123");
        return dto;
    }

    private Product createFullExistingProduct() {
        Product product = new Product();
        product.setProductId("GOL-NF-CB-0000001");
        product.setProductShtCd("GOL");
        product.setFeeTypeShtCd("NF");
        product.setRewardsTypeShtCd("CB");
        product.setAprType("FIXED");
        product.setAprValueType("SPECIFIC");
        product.setPurchaseAprMin(BigDecimal.valueOf(10.00));
        product.setPurchaseAprMax(BigDecimal.valueOf(10.00));
        product.setCashAprMin(BigDecimal.valueOf(15.00));
        product.setCashAprMax(BigDecimal.valueOf(15.00));
        product.setCreditLineMin(1000);
        product.setCreditLineMax(5000);
        product.setSecurityDepositIndicator("N");
        product.setSecurityDepositMin(null);
        product.setSecurityDepositMax(null);
        product.setTermsConditionsLink("https://example.com/terms");
        product.setCardholderAgreementLink("https://example.com/agreement");
        product.setCardImageLink("https://example.com/image.jpg");
        product.setPrin("PRIN01");
        product.setCwsProductId("CWS01");
        product.setChaCode("CHA01");
        product.setBoardingIndicator("UPC123");
        product.setStartDate(LocalDateTime.now().plusWeeks(2));
        product.setEndDate(LocalDateTime.now().plusWeeks(3));
        product.setToBeApprovedBy("admin");
        product.setCommentsToApprover("Test comments");
        product.setFeeValue(null);
        product.setStatus("PENDING");
        product.setRequestType("CREATE");
        return product;
    }

    private ProductUpdateInDto createMatchingUpdateDto(Product existing) {
        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd(existing.getProductShtCd());
        dto.setFeeTypeShtCd(existing.getFeeTypeShtCd());
        dto.setRewardsTypeShtCd(existing.getRewardsTypeShtCd());
        dto.setAprType(existing.getAprType());
        dto.setAprValueType(existing.getAprValueType());
        dto.setPurchaseAprMin(existing.getPurchaseAprMin());
        dto.setPurchaseAprMax(existing.getPurchaseAprMax());
        dto.setCashAprMin(existing.getCashAprMin());
        dto.setCashAprMax(existing.getCashAprMax());
        dto.setCreditLineMin(existing.getCreditLineMin());
        dto.setCreditLineMax(existing.getCreditLineMax());
        dto.setSecurityDepositIndicator(existing.getSecurityDepositIndicator());
        dto.setSecurityDepositMin(existing.getSecurityDepositMin());
        dto.setSecurityDepositMax(existing.getSecurityDepositMax());
        dto.setTermsConditionsLink(existing.getTermsConditionsLink());
        dto.setCardholderAgreementLink(existing.getCardholderAgreementLink());
        dto.setCardImageLink(existing.getCardImageLink());
        dto.setPrin(existing.getPrin());
        dto.setCwsProductId(existing.getCwsProductId());
        dto.setChaCode(existing.getChaCode());
        dto.setStartDate(existing.getStartDate());
        dto.setEndDate(existing.getEndDate());
        dto.setToBeApprovedBy(existing.getToBeApprovedBy());
        dto.setCommentsToApprover(existing.getCommentsToApprover());
        dto.setFeeValue(existing.getFeeValue());
        dto.setUpc("UPC123");
        dto.setPcFlag1(false);
        dto.setPcFlag2(false);
        dto.setPcFlag3(false);
        dto.setPcFlag4(false);
        dto.setPcFlag5(false);
        dto.setPcFlag6(false);
        dto.setPcFlag7(false);
        dto.setPcFlag8(false);
        dto.setPcFlag9(false);
        dto.setPcFlag10(false);
        return dto;
    }
}
