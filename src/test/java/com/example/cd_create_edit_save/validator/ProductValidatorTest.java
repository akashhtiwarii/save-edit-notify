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
import java.time.LocalDate;
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

    // ========== Product ID Validation Tests ==========

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

    // ========== Product Create Request Validation Tests ==========

    @Test
    void testValidateProductCreateRequest_Success() {
        ProductCreateInDto dto = validCreateDto();
        setupAllCodeMocksAsValid();
        when(feeValuesRepository.existsByFeeValueAndFeeType(any(), anyString())).thenReturn(true);

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

    // ========== Fee Value Validation Tests ==========

    @Test
    void testValidateFeeValue_NoFee_WithNullValue_Success() {
        ProductCreateInDto dto = validCreateDto();
        dto.setFeeTypeShtCd("NF");
        dto.setFeeValue(null);

        setupAllCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductCreateRequest(dto));
    }

    @Test
    void testValidateFeeValue_NoFee_WithZeroValue_Success() {
        ProductCreateInDto dto = validCreateDto();
        dto.setFeeTypeShtCd("NF");
        dto.setFeeValue(BigDecimal.ZERO);

        setupAllCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductCreateRequest(dto));
    }

    @Test
    void testValidateFeeValue_NoFee_WithNonZeroValue_ThrowsException() {
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

    // ========== APR Validation Tests ==========

    @Test
    void testValidateAprMinMax_SpecificWithEqualValues_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprValueType("SPECIFIC");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10));
        dto.setCashAprMin(BigDecimal.valueOf(15.00));
        dto.setCashAprMax(BigDecimal.valueOf(15.00));

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
    void testValidateAprMinMax_RangeWithValidValues_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprValueType("RANGE");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(15));
        dto.setCashAprMin(BigDecimal.valueOf(15.00));
        dto.setCashAprMax(BigDecimal.valueOf(20.00));

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

    // ========== Cash APR Verification Tests ==========

    @Test
    void testVerifyCashApr_CorrectCalculation_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setPurchaseAprMin(BigDecimal.valueOf(10.00));
        dto.setPurchaseAprMax(BigDecimal.valueOf(15.00));
        dto.setCashAprMin(BigDecimal.valueOf(15.00)); // 10 + 5
        dto.setCashAprMax(BigDecimal.valueOf(20.00)); // 15 + 5

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testVerifyCashApr_IncorrectMinCalculation_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setPurchaseAprMin(BigDecimal.valueOf(10.00));
        dto.setPurchaseAprMax(BigDecimal.valueOf(15.00));
        dto.setCashAprMin(BigDecimal.valueOf(16.00)); // Wrong: should be 15.00
        dto.setCashAprMax(BigDecimal.valueOf(20.00));

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertTrue(ex.getMessage().contains("Cash APR Min must be exactly 15.00"));
    }

    @Test
    void testVerifyCashApr_IncorrectMaxCalculation_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setPurchaseAprMin(BigDecimal.valueOf(10.00));
        dto.setPurchaseAprMax(BigDecimal.valueOf(15.00));
        dto.setCashAprMin(BigDecimal.valueOf(15.00));
        dto.setCashAprMax(BigDecimal.valueOf(21.00)); // Wrong: should be 20.00

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertTrue(ex.getMessage().contains("Cash APR Max must be exactly 20.00"));
    }

    // ========== Credit Line Validation Tests ==========

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

    // ========== Security Deposit Validation Tests ==========

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
    void testValidateSecurityDeposit_IndicatorN_MinNotZero_ThrowsException() {
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
    void testValidateSecurityDeposit_IndicatorN_MaxNotZero_ThrowsException() {
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
    void testValidateSecurityDeposit_IndicatorNull_NoValidation() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setSecurityDepositIndicator(null);
        dto.setSecurityDepositMin(null);
        dto.setSecurityDepositMax(null);

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    // ========== Date Validation Tests (UPDATED - Using LocalDate) ==========

    @Test
    void testValidateDates_ValidDates_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        LocalDate today = LocalDate.now();
        dto.setStartDate(today.plusWeeks(2).atStartOfDay());
        dto.setEndDate(today.plusMonths(6).atStartOfDay());

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateDates_StartDateExactlyOneWeekAway_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        LocalDate today = LocalDate.now();
        LocalDate oneWeekFromToday = today.plusWeeks(1);

        // Set to exactly one week from today at any time
        dto.setStartDate(oneWeekFromToday.atTime(10, 30)); // 10:30 AM
        dto.setEndDate(oneWeekFromToday.plusMonths(6).atStartOfDay());

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateDates_StartDateAtMidnightOneWeekAway_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        LocalDate today = LocalDate.now();
        LocalDate oneWeekFromToday = today.plusWeeks(1);

        // Set to exactly one week from today at midnight
        dto.setStartDate(oneWeekFromToday.atStartOfDay());
        dto.setEndDate(oneWeekFromToday.plusMonths(6).atStartOfDay());

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateDates_StartDateSixDaysAway_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        LocalDate today = LocalDate.now();

        // Set to 6 days from today (less than one week)
        dto.setStartDate(today.plusDays(6).atStartOfDay());
        dto.setEndDate(today.plusMonths(6).atStartOfDay());

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertTrue(ex.getMessage().contains("Start date must be at least one week from today"));
    }

    @Test
    void testValidateDates_StartDateThreeDaysAway_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        LocalDate today = LocalDate.now();

        dto.setStartDate(today.plusDays(3).atTime(14, 30));
        dto.setEndDate(today.plusMonths(6).atStartOfDay());

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertTrue(ex.getMessage().contains("Start date must be at least one week from today"));
    }

    @Test
    void testValidateDates_StartDateToday_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        LocalDate today = LocalDate.now();

        dto.setStartDate(today.atStartOfDay());
        dto.setEndDate(today.plusMonths(6).atStartOfDay());

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertTrue(ex.getMessage().contains("Start date must be at least one week from today"));
    }

    @Test
    void testValidateDates_EndDateBeforeStartDate_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        LocalDate today = LocalDate.now();

        dto.setStartDate(today.plusWeeks(3).atStartOfDay());
        dto.setEndDate(today.plusWeeks(2).atStartOfDay());

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("End date must be after start date (not equal)", ex.getMessage());
    }

    @Test
    void testValidateDates_EndDateEqualStartDate_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.plusWeeks(2);

        dto.setStartDate(startDate.atTime(9, 0));
        dto.setEndDate(startDate.atTime(17, 0));

        setupUpdateCodeMocksAsValid();

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));

        assertEquals("End date must be after start date (not equal)", ex.getMessage());
    }

    @Test
    void testValidateDates_EndDateOneDayAfterStartDate_Success() {
        ProductUpdateInDto dto = validUpdateDto();
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.plusWeeks(2);

        dto.setStartDate(startDate.atStartOfDay());
        dto.setEndDate(startDate.plusDays(1).atStartOfDay());

        setupUpdateCodeMocksAsValid();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    // ========== Approver Validation Tests ==========

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

    // ========== Non-Editable Fields Validation Tests ==========

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

    // ========== Field Changes Validation Tests ==========

    @Test
    void testValidateFieldChanges_NoChanges_ThrowsException() {
        Product existing = createExistingProduct();
        ProductUpdateInDto dto = createDtoMatchingProduct(existing);

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateFieldChanges(existing, dto));

        assertTrue(ex.getMessage().contains("No changes detected"));
    }

    @Test
    void testValidateFieldChanges_OnlyDatesChanged_ThrowsException() {
        Product existing = createExistingProduct();
        ProductUpdateInDto dto = createDtoMatchingProduct(existing);

        // Only change dates
        dto.setStartDate(existing.getStartDate().plusDays(1));
        dto.setEndDate(existing.getEndDate().plusDays(1));

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateFieldChanges(existing, dto));

        assertTrue(ex.getMessage().contains("Cannot create new product version with only date changes"));
    }

    @Test
    void testValidateFieldChanges_OtherFieldsChanged_Success() {
        Product existing = createExistingProduct();
        ProductUpdateInDto dto = createDtoMatchingProduct(existing);

        // Change a non-date field
        dto.setCreditLineMax(existing.getCreditLineMax() + 1000);

        assertDoesNotThrow(() -> productValidator.validateFieldChanges(existing, dto));
    }

    @Test
    void testValidateFieldChanges_OtherFieldsAndDatesChanged_Success() {
        Product existing = createExistingProduct();
        ProductUpdateInDto dto = createDtoMatchingProduct(existing);

        // Change both dates and other fields
        dto.setStartDate(existing.getStartDate().plusDays(1));
        dto.setEndDate(existing.getEndDate().plusDays(1));
        dto.setCreditLineMin(existing.getCreditLineMin() + 500);

        assertDoesNotThrow(() -> productValidator.validateFieldChanges(existing, dto));
    }

    @Test
    void testValidateFieldChanges_AprValueTypeChanged_Success() {
        Product existing = createExistingProduct();
        ProductUpdateInDto dto = createDtoMatchingProduct(existing);

        dto.setAprValueType("RANGE");

        assertDoesNotThrow(() -> productValidator.validateFieldChanges(existing, dto));
    }

    @Test
    void testValidateFieldChanges_PurchaseAprChanged_Success() {
        Product existing = createExistingProduct();
        ProductUpdateInDto dto = createDtoMatchingProduct(existing);

        dto.setPurchaseAprMin(existing.getPurchaseAprMin().add(BigDecimal.ONE));

        assertDoesNotThrow(() -> productValidator.validateFieldChanges(existing, dto));
    }

    @Test
    void testValidateFieldChanges_SecurityDepositIndicatorChanged_Success() {
        Product existing = createExistingProduct();
        ProductUpdateInDto dto = createDtoMatchingProduct(existing);

        dto.setSecurityDepositIndicator("Y");
        dto.setSecurityDepositMin(100);
        dto.setSecurityDepositMax(500);

        assertDoesNotThrow(() -> productValidator.validateFieldChanges(existing, dto));
    }

    @Test
    void testValidateFieldChanges_LinksChanged_Success() {
        Product existing = createExistingProduct();
        ProductUpdateInDto dto = createDtoMatchingProduct(existing);

        dto.setTermsConditionsLink("https://new-link.com/terms");

        assertDoesNotThrow(() -> productValidator.validateFieldChanges(existing, dto));
    }

    @Test
    void testValidateFieldChanges_PrinChanged_Success() {
        Product existing = createExistingProduct();
        ProductUpdateInDto dto = createDtoMatchingProduct(existing);

        dto.setPrin("PRIN02");

        assertDoesNotThrow(() -> productValidator.validateFieldChanges(existing, dto));
    }

    @Test
    void testValidateFieldChanges_BoardingIndicatorChanged_Success() {
        Product existing = createExistingProduct();
        ProductUpdateInDto dto = createDtoMatchingProduct(existing);

        dto.setPcFlag1(true);

        assertDoesNotThrow(() -> productValidator.validateFieldChanges(existing, dto));
    }

    @Test
    void testValidateFieldChanges_ApproverChanged_Success() {
        Product existing = createExistingProduct();
        ProductUpdateInDto dto = createDtoMatchingProduct(existing);

        dto.setToBeApprovedBy("newApprover");

        assertDoesNotThrow(() -> productValidator.validateFieldChanges(existing, dto));
    }

    @Test
    void testValidateFieldChanges_FeeValueChanged_Success() {
        Product existing = createExistingProduct();
        ProductUpdateInDto dto = createDtoMatchingProduct(existing);

        dto.setFeeValue(BigDecimal.valueOf(99));

        assertDoesNotThrow(() -> productValidator.validateFieldChanges(existing, dto));
    }

    // ========== Helper Methods ==========

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
        dto.setFeeValue(BigDecimal.valueOf(95));
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
        dto.setStartDate(LocalDate.now().plusWeeks(2).atStartOfDay());
        dto.setEndDate(LocalDate.now().plusMonths(6).atStartOfDay());
        dto.setToBeApprovedBy("admin");
        dto.setTermsConditionsLink("https://example.com/terms");
        dto.setCardholderAgreementLink("https://example.com/agreement");
        dto.setCardImageLink("https://example.com/image");
        return dto;
    }

    private ProductUpdateInDto validUpdateDto() {
        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd("GOL");
        dto.setFeeTypeShtCd("AF");
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
        dto.setStartDate(LocalDate.now().plusWeeks(2).atStartOfDay());
        dto.setEndDate(LocalDate.now().plusWeeks(3).atStartOfDay());
        dto.setToBeApprovedBy("admin");
        dto.setTermsConditionsLink("https://example.com/terms");
        dto.setCardholderAgreementLink("https://example.com/agreement");
        dto.setCardImageLink("https://example.com/image");
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
        dto.setUpc("");
        return dto;
    }

    private Product createExistingProduct() {
        Product product = new Product();
        product.setProductId("P123");
        product.setProductShtCd("GOL");
        product.setFeeTypeShtCd("AF");
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
        product.setStartDate(LocalDate.now().plusWeeks(2).atStartOfDay());
        product.setEndDate(LocalDate.now().plusMonths(6).atStartOfDay());
        product.setToBeApprovedBy("admin");
        product.setTermsConditionsLink("https://example.com/terms");
        product.setCardholderAgreementLink("https://example.com/agreement");
        product.setCardImageLink("https://example.com/image");
        product.setPrin("PRIN01");
        product.setCwsProductId("CWS01");
        product.setChaCode("CHA01");
        product.setBoardingIndicator("");
        product.setCommentsToApprover(null);
        product.setFeeValue(BigDecimal.valueOf(95));
        return product;
    }

    private ProductUpdateInDto createDtoMatchingProduct(Product product) {
        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd(product.getProductShtCd());
        dto.setFeeTypeShtCd(product.getFeeTypeShtCd());
        dto.setRewardsTypeShtCd(product.getRewardsTypeShtCd());
        dto.setAprType(product.getAprType());
        dto.setAprValueType(product.getAprValueType());
        dto.setPurchaseAprMin(product.getPurchaseAprMin());
        dto.setPurchaseAprMax(product.getPurchaseAprMax());
        dto.setCashAprMin(product.getCashAprMin());
        dto.setCashAprMax(product.getCashAprMax());
        dto.setCreditLineMin(product.getCreditLineMin());
        dto.setCreditLineMax(product.getCreditLineMax());
        dto.setSecurityDepositIndicator(product.getSecurityDepositIndicator());
        dto.setSecurityDepositMin(product.getSecurityDepositMin());
        dto.setSecurityDepositMax(product.getSecurityDepositMax());
        dto.setStartDate(product.getStartDate());
        dto.setEndDate(product.getEndDate());
        dto.setToBeApprovedBy(product.getToBeApprovedBy());
        dto.setTermsConditionsLink(product.getTermsConditionsLink());
        dto.setCardholderAgreementLink(product.getCardholderAgreementLink());
        dto.setCardImageLink(product.getCardImageLink());
        dto.setPrin(product.getPrin());
        dto.setCwsProductId(product.getCwsProductId());
        dto.setChaCode(product.getChaCode());
        dto.setCommentsToApprover(product.getCommentsToApprover());
        dto.setFeeValue(product.getFeeValue());
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
        dto.setUpc("");
        return dto;
    }
}