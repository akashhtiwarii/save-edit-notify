package com.example.cd_create_edit_save.model.dto.inDto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ProductCreateInDto validation
 * Covers all field validations including the new feeValue field
 * Target: 90%+ coverage
 */
class ProductCreateInDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private ProductCreateInDto createValidDto() {
        return ProductCreateInDto.builder()
                .productShtCd("ABC")
                .feeTypeShtCd("AF")
                .rewardsTypeShtCd("RW")
                .aprType("FIXED")
                .aprValueType("SPECIFIC")
                .purchaseAprMin(new BigDecimal("10.00"))
                .purchaseAprMax(new BigDecimal("10.00"))
                .cashAprMin(new BigDecimal("15.00"))
                .cashAprMax(new BigDecimal("15.00"))
                .termsConditionsLink("https://example.com/terms")
                .cardholderAgreementLink("https://example.com/agreement")
                .cardImageLink("https://example.com/image.png")
                .creditLineMin(500)
                .creditLineMax(5000)
                .securityDepositIndicator("N")
                .toBeApprovedBy("John Doe")
                .feeValue(new BigDecimal("99.00"))
                .prin("PRIN123")
                .cwsProductId("CWS456")
                .chaCode("CHA789")
                .upc("UPC001")
                .startDate(LocalDateTime.now().plusWeeks(2))
                .endDate(LocalDateTime.now().plusYears(1))
                .build();
    }

    @Test
    @DisplayName("Valid DTO - should pass")
    void testValidDto() {
        ProductCreateInDto dto = createValidDto();
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    // ==================== PRODUCT SHORT CODE TESTS ====================

    @Test
    @DisplayName("Product short code blank - should fail")
    void testProductShtCd_Blank() {
        ProductCreateInDto dto = createValidDto();
        dto.setProductShtCd("");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Product short code null - should fail")
    void testProductShtCd_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setProductShtCd(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Product short code too long - should fail")
    void testProductShtCd_TooLong() {
        ProductCreateInDto dto = createValidDto();
        dto.setProductShtCd("ABCD");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== FEE TYPE SHORT CODE TESTS ====================

    @Test
    @DisplayName("Fee type short code blank - should fail")
    void testFeeTypeShtCd_Blank() {
        ProductCreateInDto dto = createValidDto();
        dto.setFeeTypeShtCd("");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Fee type short code null - should fail")
    void testFeeTypeShtCd_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setFeeTypeShtCd(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Fee type short code too long - should fail")
    void testFeeTypeShtCd_TooLong() {
        ProductCreateInDto dto = createValidDto();
        dto.setFeeTypeShtCd("ABC");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== REWARDS TYPE SHORT CODE TESTS ====================

    @Test
    @DisplayName("Rewards type short code blank - should fail")
    void testRewardsTypeShtCd_Blank() {
        ProductCreateInDto dto = createValidDto();
        dto.setRewardsTypeShtCd("");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Rewards type short code null - should fail")
    void testRewardsTypeShtCd_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setRewardsTypeShtCd(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Rewards type short code too long - should fail")
    void testRewardsTypeShtCd_TooLong() {
        ProductCreateInDto dto = createValidDto();
        dto.setRewardsTypeShtCd("ABC");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== APR TYPE TESTS ====================

    @Test
    @DisplayName("APR type blank - should fail")
    void testAprType_Blank() {
        ProductCreateInDto dto = createValidDto();
        dto.setAprType("");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("APR type null - should fail")
    void testAprType_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setAprType(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("APR type invalid - should fail")
    void testAprType_Invalid() {
        ProductCreateInDto dto = createValidDto();
        dto.setAprType("INVALID");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("APR type FIXED - should pass")
    void testAprType_Fixed() {
        ProductCreateInDto dto = createValidDto();
        dto.setAprType("FIXED");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("APR type VARIABLE - should pass")
    void testAprType_Variable() {
        ProductCreateInDto dto = createValidDto();
        dto.setAprType("VARIABLE");
        dto.setAprValueType("RANGE");
        dto.setPurchaseAprMin(new BigDecimal("10.00"));
        dto.setPurchaseAprMax(new BigDecimal("15.00"));
        dto.setCashAprMin(new BigDecimal("15.00"));
        dto.setCashAprMax(new BigDecimal("20.00"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    // ==================== APR VALUE TYPE TESTS ====================

    @Test
    @DisplayName("APR value type blank - should fail")
    void testAprValueType_Blank() {
        ProductCreateInDto dto = createValidDto();
        dto.setAprValueType("");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("APR value type null - should fail")
    void testAprValueType_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setAprValueType(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("APR value type invalid - should fail")
    void testAprValueType_Invalid() {
        ProductCreateInDto dto = createValidDto();
        dto.setAprValueType("INVALID");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== PURCHASE APR TESTS ====================

    @Test
    @DisplayName("Purchase APR min null - should fail")
    void testPurchaseAprMin_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setPurchaseAprMin(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Purchase APR min negative - should fail")
    void testPurchaseAprMin_Negative() {
        ProductCreateInDto dto = createValidDto();
        dto.setPurchaseAprMin(new BigDecimal("-1.00"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Purchase APR min too high - should fail")
    void testPurchaseAprMin_TooHigh() {
        ProductCreateInDto dto = createValidDto();
        dto.setPurchaseAprMin(new BigDecimal("36.00"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Purchase APR min boundary 0.00 - should pass")
    void testPurchaseAprMin_BoundaryZero() {
        ProductCreateInDto dto = createValidDto();
        dto.setPurchaseAprMin(new BigDecimal("0.00"));
        dto.setPurchaseAprMax(new BigDecimal("0.00"));
        dto.setCashAprMin(new BigDecimal("5.00"));
        dto.setCashAprMax(new BigDecimal("5.00"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Purchase APR min boundary 35.99 - should pass")
    void testPurchaseAprMin_BoundaryMax() {
        ProductCreateInDto dto = createValidDto();
        dto.setPurchaseAprMin(new BigDecimal("35.99"));
        dto.setPurchaseAprMax(new BigDecimal("35.99"));
        dto.setCashAprMin(new BigDecimal("40.99"));
        dto.setCashAprMax(new BigDecimal("40.99"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Purchase APR max null - should fail")
    void testPurchaseAprMax_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setPurchaseAprMax(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Purchase APR max negative - should fail")
    void testPurchaseAprMax_Negative() {
        ProductCreateInDto dto = createValidDto();
        dto.setPurchaseAprMax(new BigDecimal("-1.00"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Purchase APR max too high - should fail")
    void testPurchaseAprMax_TooHigh() {
        ProductCreateInDto dto = createValidDto();
        dto.setPurchaseAprMax(new BigDecimal("36.00"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== CASH APR TESTS ====================

    @Test
    @DisplayName("Cash APR min null - should fail")
    void testCashAprMin_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setCashAprMin(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Cash APR min negative - should fail")
    void testCashAprMin_Negative() {
        ProductCreateInDto dto = createValidDto();
        dto.setCashAprMin(new BigDecimal("-1.00"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Cash APR min too high - should fail")
    void testCashAprMin_TooHigh() {
        ProductCreateInDto dto = createValidDto();
        dto.setCashAprMin(new BigDecimal("41.00"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Cash APR min boundary 40.99 - should pass")
    void testCashAprMin_BoundaryMax() {
        ProductCreateInDto dto = createValidDto();
        dto.setPurchaseAprMin(new BigDecimal("35.99"));
        dto.setPurchaseAprMax(new BigDecimal("35.99"));
        dto.setCashAprMin(new BigDecimal("40.99"));
        dto.setCashAprMax(new BigDecimal("40.99"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Cash APR max null - should fail")
    void testCashAprMax_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setCashAprMax(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Cash APR max negative - should fail")
    void testCashAprMax_Negative() {
        ProductCreateInDto dto = createValidDto();
        dto.setCashAprMax(new BigDecimal("-1.00"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Cash APR max too high - should fail")
    void testCashAprMax_TooHigh() {
        ProductCreateInDto dto = createValidDto();
        dto.setCashAprMax(new BigDecimal("41.00"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== URL TESTS ====================

    @Test
    @DisplayName("Terms link blank - should fail")
    void testTermsLink_Blank() {
        ProductCreateInDto dto = createValidDto();
        dto.setTermsConditionsLink("");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Terms link not HTTPS - should fail")
    void testTermsLink_NotHttps() {
        ProductCreateInDto dto = createValidDto();
        dto.setTermsConditionsLink("http://example.com/terms");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Cardholder agreement link blank - should fail")
    void testCardholderLink_Blank() {
        ProductCreateInDto dto = createValidDto();
        dto.setCardholderAgreementLink("");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Card image link blank - should fail")
    void testCardImageLink_Blank() {
        ProductCreateInDto dto = createValidDto();
        dto.setCardImageLink("");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== CREDIT LINE TESTS ====================

    @Test
    @DisplayName("Credit line min null - should fail")
    void testCreditLineMin_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setCreditLineMin(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Credit line min negative - should fail")
    void testCreditLineMin_Negative() {
        ProductCreateInDto dto = createValidDto();
        dto.setCreditLineMin(-100);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Credit line max null - should fail")
    void testCreditLineMax_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setCreditLineMax(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Credit line max negative - should fail")
    void testCreditLineMax_Negative() {
        ProductCreateInDto dto = createValidDto();
        dto.setCreditLineMax(-100);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== SECURITY DEPOSIT TESTS ====================

    @Test
    @DisplayName("Security deposit indicator blank - should fail")
    void testSecurityIndicator_Blank() {
        ProductCreateInDto dto = createValidDto();
        dto.setSecurityDepositIndicator("");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Security deposit indicator null - should fail")
    void testSecurityIndicator_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setSecurityDepositIndicator(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Security deposit indicator invalid - should fail")
    void testSecurityIndicator_Invalid() {
        ProductCreateInDto dto = createValidDto();
        dto.setSecurityDepositIndicator("X");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Security deposit indicator Y - should pass")
    void testSecurityIndicator_Yes() {
        ProductCreateInDto dto = createValidDto();
        dto.setSecurityDepositIndicator("Y");
        dto.setSecurityDepositMin(100);
        dto.setSecurityDepositMax(500);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Security deposit min negative - should fail")
    void testSecurityDepositMin_Negative() {
        ProductCreateInDto dto = createValidDto();
        dto.setSecurityDepositMin(-100);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Security deposit max negative - should fail")
    void testSecurityDepositMax_Negative() {
        ProductCreateInDto dto = createValidDto();
        dto.setSecurityDepositMax(-100);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== FEE VALUE TESTS (NEW) ====================

    @Test
    @DisplayName("Fee value null - should pass")
    void testFeeValue_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setFeeValue(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Fee value negative - should fail")
    void testFeeValue_Negative() {
        ProductCreateInDto dto = createValidDto();
        dto.setFeeValue(new BigDecimal("-10.00"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Fee value too many decimals - should fail")
    void testFeeValue_TooManyDecimals() {
        ProductCreateInDto dto = createValidDto();
        dto.setFeeValue(new BigDecimal("99.999"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Fee value zero - should pass")
    void testFeeValue_Zero() {
        ProductCreateInDto dto = createValidDto();
        dto.setFeeValue(new BigDecimal("0.00"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Fee value valid - should pass")
    void testFeeValue_Valid() {
        ProductCreateInDto dto = createValidDto();
        dto.setFeeValue(new BigDecimal("99.99"));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    // ==================== APPROVER TESTS ====================

    @Test
    @DisplayName("To be approved by blank - should fail")
    void testApprover_Blank() {
        ProductCreateInDto dto = createValidDto();
        dto.setToBeApprovedBy("");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("To be approved by null - should fail")
    void testApprover_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setToBeApprovedBy(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("To be approved by too long - should fail")
    void testApprover_TooLong() {
        ProductCreateInDto dto = createValidDto();
        dto.setToBeApprovedBy("A".repeat(256));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== COMMENTS TESTS ====================

    @Test
    @DisplayName("Comments null - should pass")
    void testComments_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setCommentsToApprover(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Comments too long - should fail")
    void testComments_TooLong() {
        ProductCreateInDto dto = createValidDto();
        dto.setCommentsToApprover("A".repeat(1001));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== PRIN TESTS ====================

    @Test
    @DisplayName("PRIN blank - should fail")
    void testPrin_Blank() {
        ProductCreateInDto dto = createValidDto();
        dto.setPrin("");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("PRIN null - should fail")
    void testPrin_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setPrin(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("PRIN too long - should fail")
    void testPrin_TooLong() {
        ProductCreateInDto dto = createValidDto();
        dto.setPrin("A".repeat(51));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== CWS PRODUCT ID TESTS ====================

    @Test
    @DisplayName("CWS Product ID blank - should fail")
    void testCwsProductId_Blank() {
        ProductCreateInDto dto = createValidDto();
        dto.setCwsProductId("");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("CWS Product ID null - should fail")
    void testCwsProductId_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setCwsProductId(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("CWS Product ID too long - should fail")
    void testCwsProductId_TooLong() {
        ProductCreateInDto dto = createValidDto();
        dto.setCwsProductId("A".repeat(51));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== CHA CODE TESTS ====================

    @Test
    @DisplayName("CHA Code blank - should fail")
    void testChaCode_Blank() {
        ProductCreateInDto dto = createValidDto();
        dto.setChaCode("");
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("CHA Code null - should fail")
    void testChaCode_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setChaCode(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("CHA Code too long - should fail")
    void testChaCode_TooLong() {
        ProductCreateInDto dto = createValidDto();
        dto.setChaCode("A".repeat(51));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== UPC TESTS ====================

    @Test
    @DisplayName("UPC null - should fail")
    void testUpc_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setUpc(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("UPC too long - should fail")
    void testUpc_TooLong() {
        ProductCreateInDto dto = createValidDto();
        dto.setUpc("A".repeat(51));
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== DATE TESTS ====================

    @Test
    @DisplayName("Start date null - should fail")
    void testStartDate_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setStartDate(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("End date null - should fail")
    void testEndDate_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setEndDate(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

    // ==================== PC FLAGS TESTS ====================

    @Test
    @DisplayName("PC Flags null - should pass")
    void testPcFlags_Null() {
        ProductCreateInDto dto = createValidDto();
        dto.setPcFlag1(null);
        dto.setPcFlag2(null);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("PC Flags true - should pass")
    void testPcFlags_True() {
        ProductCreateInDto dto = createValidDto();
        dto.setPcFlag1(true);
        dto.setPcFlag2(true);
        Set<ConstraintViolation<ProductCreateInDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}
