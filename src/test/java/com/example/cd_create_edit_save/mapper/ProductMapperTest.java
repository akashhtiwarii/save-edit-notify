package com.example.cd_create_edit_save.mapper;

import com.example.cd_create_edit_save.model.dto.inDto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.inDto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.model.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ProductMapper
 * Target: 90%+ coverage with minimal test cases
 */
class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
    }


    @Test
    @DisplayName("Map CreateDto to Entity - All fields mapped correctly")
    void testToEntity_FromCreateDto_AllFieldsMapped() {

        ProductCreateInDto dto = ProductCreateInDto.builder()
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
                .securityDepositIndicator("Y")
                .securityDepositMin(100)
                .securityDepositMax(500)
                .toBeApprovedBy("John Doe")
                .feeValue(new BigDecimal("99.00"))
                .commentsToApprover("Please review")
                .prin("PRIN123")
                .cwsProductId("CWS456")
                .chaCode("CHA789")
                .pcFlag1(true)
                .pcFlag2(false)
                .pcFlag3(true)
                .pcFlag4(null)
                .pcFlag5(null)
                .pcFlag6(null)
                .pcFlag7(null)
                .pcFlag8(null)
                .pcFlag9(null)
                .pcFlag10(null)
                .upc("UPC001")
                .startDate(LocalDateTime.of(2025, 1, 1, 0, 0))
                .endDate(LocalDateTime.of(2026, 1, 1, 0, 0))
                .build();

        String productId = "ABC-AF-RW-0000001";
        String createdBy = "testUser";

        Product product = productMapper.toEntity(dto, productId, createdBy);

        assertEquals(productId, product.getProductId());
        assertEquals("ABC", product.getProductShtCd());
        assertEquals("AF", product.getFeeTypeShtCd());
        assertEquals("RW", product.getRewardsTypeShtCd());

        assertEquals("FIXED", product.getAprType());
        assertEquals("SPECIFIC", product.getAprValueType());
        assertEquals(new BigDecimal("10.00"), product.getPurchaseAprMin());
        assertEquals(new BigDecimal("10.00"), product.getPurchaseAprMax());
        assertEquals(new BigDecimal("15.00"), product.getCashAprMin());
        assertEquals(new BigDecimal("15.00"), product.getCashAprMax());

        assertEquals(500, product.getCreditLineMin());
        assertEquals(5000, product.getCreditLineMax());

        assertEquals("Y", product.getSecurityDepositIndicator());
        assertEquals(100, product.getSecurityDepositMin());
        assertEquals(500, product.getSecurityDepositMax());

        assertEquals("https://example.com/terms", product.getTermsConditionsLink());
        assertEquals("https://example.com/agreement", product.getCardholderAgreementLink());
        assertEquals("https://example.com/image.png", product.getCardImageLink());

        assertEquals("PENDING", product.getStatus());
        assertEquals("CREATE", product.getRequestType());

        assertEquals(createdBy, product.getCreatedBy());
        assertEquals(createdBy, product.getUpdatedBy());
        assertNotNull(product.getCreatedDatetime());
        assertNotNull(product.getUpdatedDatetime());

        assertEquals("John Doe", product.getToBeApprovedBy());
        assertEquals("Please review", product.getCommentsToApprover());
        assertNull(product.getReviewedBy());
        assertNull(product.getReviewedDatetime());
        assertNull(product.getReviewComments());

        assertEquals(new BigDecimal("99.00"), product.getFeeValue());

        assertEquals("PRIN123", product.getPrin());
        assertEquals("CWS456", product.getCwsProductId());
        assertEquals("CHA789", product.getChaCode());

        assertEquals("PC_FLAG1,PC_FLAG3,UPC001", product.getBoardingIndicator());

        assertEquals(LocalDateTime.of(2025, 1, 1, 0, 0), product.getStartDate());
        assertEquals(LocalDateTime.of(2026, 1, 1, 0, 0), product.getEndDate());
    }

    @Test
    @DisplayName("Map CreateDto to Entity - Boarding indicator with all flags true")
    void testToEntity_FromCreateDto_AllFlagsTrue() {

        ProductCreateInDto dto = createCreateDto();
        dto.setPcFlag1(true);
        dto.setPcFlag2(true);
        dto.setPcFlag3(true);
        dto.setPcFlag4(true);
        dto.setPcFlag5(true);
        dto.setPcFlag6(true);
        dto.setPcFlag7(true);
        dto.setPcFlag8(true);
        dto.setPcFlag9(true);
        dto.setPcFlag10(true);
        dto.setUpc("UPC001");

        Product product = productMapper.toEntity(dto, "TEST-ID", "user");

        assertEquals("PC_FLAG1,PC_FLAG2,PC_FLAG3,PC_FLAG4,PC_FLAG5,PC_FLAG6,PC_FLAG7,PC_FLAG8,PC_FLAG9,PC_FLAG10,UPC001",
                product.getBoardingIndicator());
    }

    @Test
    @DisplayName("Map CreateDto to Entity - Boarding indicator with no flags and no UPC")
    void testToEntity_FromCreateDto_NoFlagsNoUpc() {

        ProductCreateInDto dto = createCreateDto();
        dto.setUpc("");
        Product product = productMapper.toEntity(dto, "TEST-ID", "user");
        assertEquals("", product.getBoardingIndicator());
    }

    @Test
    @DisplayName("Map CreateDto to Entity - Security deposit N with null values")
    void testToEntity_FromCreateDto_SecurityDepositNo() {

        ProductCreateInDto dto = createCreateDto();
        dto.setSecurityDepositIndicator("N");
        dto.setSecurityDepositMin(null);
        dto.setSecurityDepositMax(null);

        Product product = productMapper.toEntity(dto, "TEST-ID", "user");

        assertEquals("N", product.getSecurityDepositIndicator());
        assertNull(product.getSecurityDepositMin());
        assertNull(product.getSecurityDepositMax());
    }


    @Test
    @DisplayName("Map UpdateDto to Entity - All fields mapped correctly")
    void testToEntity_FromUpdateDto_AllFieldsMapped() {

        ProductUpdateInDto dto = ProductUpdateInDto.builder()
                .productShtCd("XYZ")
                .feeTypeShtCd("MF")
                .rewardsTypeShtCd("CB")
                .aprType("VARIABLE")
                .aprValueType("RANGE")
                .purchaseAprMin(new BigDecimal("12.00"))
                .purchaseAprMax(new BigDecimal("18.00"))
                .cashAprMin(new BigDecimal("17.00"))
                .cashAprMax(new BigDecimal("23.00"))
                .termsConditionsLink("https://example.com/terms-updated")
                .cardholderAgreementLink("https://example.com/agreement-updated")
                .cardImageLink("https://example.com/image-updated.png")
                .creditLineMin(1000)
                .creditLineMax(10000)
                .securityDepositIndicator("Y")
                .securityDepositMin(200)
                .securityDepositMax(1000)
                .toBeApprovedBy("Jane Smith")
                .feeValue(new BigDecimal("50.00"))
                .commentsToApprover("Updated product")
                .prin("PRIN456")
                .cwsProductId("CWS789")
                .chaCode("CHA012")
                .pcFlag1(false)
                .pcFlag2(true)
                .pcFlag3(false)
                .pcFlag4(null)
                .pcFlag5(null)
                .pcFlag6(null)
                .pcFlag7(null)
                .pcFlag8(null)
                .pcFlag9(null)
                .pcFlag10(null)
                .upc("UPC002")
                .startDate(LocalDateTime.of(2025, 6, 1, 0, 0))
                .endDate(LocalDateTime.of(2026, 6, 1, 0, 0))
                .build();

        String productId = "XYZ-MF-CB-0000001";
        String updatedBy = "updateUser";

        Product product = productMapper.toEntity(dto, productId, updatedBy);

        assertEquals(productId, product.getProductId());
        assertEquals("XYZ", product.getProductShtCd());
        assertEquals("MF", product.getFeeTypeShtCd());
        assertEquals("CB", product.getRewardsTypeShtCd());

        assertEquals("VARIABLE", product.getAprType());
        assertEquals("RANGE", product.getAprValueType());
        assertEquals(new BigDecimal("12.00"), product.getPurchaseAprMin());
        assertEquals(new BigDecimal("18.00"), product.getPurchaseAprMax());

        assertEquals("REVISION_PENDING", product.getStatus());
        assertEquals("UPDATE", product.getRequestType());

        assertEquals(updatedBy, product.getCreatedBy());
        assertEquals(updatedBy, product.getUpdatedBy());
        assertNotNull(product.getCreatedDatetime());
        assertNotNull(product.getUpdatedDatetime());

        assertEquals(new BigDecimal("50.00"), product.getFeeValue());

        assertEquals("PRIN456", product.getPrin());
        assertEquals("CWS789", product.getCwsProductId());
        assertEquals("CHA012", product.getChaCode());

        assertEquals("PC_FLAG2,UPC002", product.getBoardingIndicator());

        assertEquals(LocalDateTime.of(2025, 6, 1, 0, 0), product.getStartDate());
        assertEquals(LocalDateTime.of(2026, 6, 1, 0, 0), product.getEndDate());
    }

    @Test
    @DisplayName("Map UpdateDto to Entity - Boarding indicator with only UPC")
    void testToEntity_FromUpdateDto_OnlyUpc() {

        ProductUpdateInDto dto = createUpdateDto();
        dto.setUpc("UPC999");
        Product product = productMapper.toEntity(dto, "TEST-ID", "user");
        assertEquals("UPC999", product.getBoardingIndicator());
    }


    @Test
    @DisplayName("Map Entity to OutDto - All fields mapped correctly")
    void testToDto_AllFieldsMapped() {
        Product product = Product.builder()
                .productId("ABC-AF-RW-0000001")
                .productShtCd("ABC")
                .feeTypeShtCd("AF")
                .rewardsTypeShtCd("RW")
                .aprType("FIXED")
                .aprValueType("SPECIFIC")
                .purchaseAprMin(new BigDecimal("10.00"))
                .purchaseAprMax(new BigDecimal("10.00"))
                .cashAprMin(new BigDecimal("15.00"))
                .cashAprMax(new BigDecimal("15.00"))
                .creditLineMin(500)
                .creditLineMax(5000)
                .securityDepositIndicator("Y")
                .securityDepositMin(100)
                .securityDepositMax(500)
                .termsConditionsLink("https://example.com/terms")
                .cardholderAgreementLink("https://example.com/agreement")
                .cardImageLink("https://example.com/image.png")
                .status("PENDING")
                .createdBy("testUser")
                .createdDatetime(LocalDateTime.of(2025, 1, 1, 10, 0))
                .updatedBy("testUser")
                .updatedDatetime(LocalDateTime.of(2025, 1, 1, 10, 0))
                .requestType("CREATE")
                .toBeApprovedBy("John Doe")
                .feeValue(new BigDecimal("99.00"))
                .commentsToApprover("Please review")
                .reviewedBy("Jane Smith")
                .reviewedDatetime(LocalDateTime.of(2025, 1, 2, 10, 0))
                .reviewComments("Approved")
                .overrideBy("Admin")
                .overrideDatetime(LocalDateTime.of(2025, 1, 3, 10, 0))
                .overrideJustification("Override reason".getBytes())
                .prin("PRIN123")
                .cwsProductId("CWS456")
                .chaCode("CHA789")
                .boardingIndicator("PC_FLAG1,UPC001")
                .startDate(LocalDateTime.of(2025, 1, 1, 0, 0))
                .endDate(LocalDateTime.of(2026, 1, 1, 0, 0))
                .build();

        ProductOutDto dto = productMapper.toDto(product);

        assertEquals("ABC-AF-RW-0000001", dto.getProductId());
        assertEquals("ABC", dto.getProductShtCd());
        assertEquals("AF", dto.getFeeTypeShtCd());
        assertEquals("RW", dto.getRewardsTypeShtCd());

        assertEquals("FIXED", dto.getAprType());
        assertEquals("SPECIFIC", dto.getAprValueType());
        assertEquals(new BigDecimal("10.00"), dto.getPurchaseAprMin());
        assertEquals(new BigDecimal("10.00"), dto.getPurchaseAprMax());
        assertEquals(new BigDecimal("15.00"), dto.getCashAprMin());
        assertEquals(new BigDecimal("15.00"), dto.getCashAprMax());

        assertEquals(500, dto.getCreditLineMin());
        assertEquals(5000, dto.getCreditLineMax());

        assertEquals("Y", dto.getSecurityDepositIndicator());
        assertEquals(100, dto.getSecurityDepositMin());
        assertEquals(500, dto.getSecurityDepositMax());

        assertEquals("https://example.com/terms", dto.getTermsConditionsLink());
        assertEquals("https://example.com/agreement", dto.getCardholderAgreementLink());
        assertEquals("https://example.com/image.png", dto.getCardImageLink());

        assertEquals("PENDING", dto.getStatus());
        assertEquals("CREATE", dto.getRequestType());

        assertEquals("testUser", dto.getCreatedBy());
        assertEquals("testUser", dto.getUpdatedBy());
        assertEquals(LocalDateTime.of(2025, 1, 1, 10, 0), dto.getCreatedDatetime());
        assertEquals(LocalDateTime.of(2025, 1, 1, 10, 0), dto.getUpdatedDatetime());

        assertEquals("John Doe", dto.getToBeApprovedBy());
        assertEquals("Please review", dto.getCommentsToApprover());
        assertEquals("Jane Smith", dto.getReviewedBy());
        assertEquals(LocalDateTime.of(2025, 1, 2, 10, 0), dto.getReviewedDatetime());
        assertEquals("Approved", dto.getReviewComments());

        assertEquals("Admin", dto.getOverrideBy());
        assertEquals(LocalDateTime.of(2025, 1, 3, 10, 0), dto.getOverrideDatetime());
        assertArrayEquals("Override reason".getBytes(), dto.getOverrideJustification());

        assertEquals(new BigDecimal("99.00"), dto.getFeeValue());

        assertEquals("PRIN123", dto.getPrin());
        assertEquals("CWS456", dto.getCwsProductId());
        assertEquals("CHA789", dto.getChaCode());

        assertEquals("PC_FLAG1,UPC001", dto.getBoardingIndicator());

        assertEquals(LocalDateTime.of(2025, 1, 1, 0, 0), dto.getStartDate());
        assertEquals(LocalDateTime.of(2026, 1, 1, 0, 0), dto.getEndDate());
    }

    @Test
    @DisplayName("Map Entity to OutDto - Null entity returns null")
    void testToDto_NullEntity() {
        ProductOutDto dto = productMapper.toDto(null);
        assertNull(dto);
    }

    @Test
    @DisplayName("Map Entity to OutDto - Entity with null optional fields")
    void testToDto_NullOptionalFields() {
        Product product = Product.builder()
                .productId("TEST-ID")
                .productShtCd("ABC")
                .feeTypeShtCd("AF")
                .rewardsTypeShtCd("RW")
                .aprType("FIXED")
                .aprValueType("SPECIFIC")
                .purchaseAprMin(new BigDecimal("10.00"))
                .purchaseAprMax(new BigDecimal("10.00"))
                .cashAprMin(new BigDecimal("15.00"))
                .cashAprMax(new BigDecimal("15.00"))
                .creditLineMin(500)
                .creditLineMax(5000)
                .securityDepositIndicator("N")
                .securityDepositMin(null)
                .securityDepositMax(null)
                .termsConditionsLink("https://example.com/terms")
                .cardholderAgreementLink("https://example.com/agreement")
                .cardImageLink("https://example.com/image.png")
                .status("PENDING")
                .createdBy("user")
                .createdDatetime(LocalDateTime.now())
                .updatedBy("user")
                .updatedDatetime(LocalDateTime.now())
                .requestType("CREATE")
                .toBeApprovedBy("approver")
                .feeValue(null)
                .commentsToApprover(null)
                .reviewedBy(null)
                .reviewedDatetime(null)
                .reviewComments(null)
                .overrideBy(null)
                .overrideDatetime(null)
                .overrideJustification(null)
                .prin("PRIN123")
                .cwsProductId("CWS456")
                .chaCode("CHA789")
                .boardingIndicator("")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusYears(1))
                .build();


        ProductOutDto dto = productMapper.toDto(product);

        assertNull(dto.getSecurityDepositMin());
        assertNull(dto.getSecurityDepositMax());
        assertNull(dto.getFeeValue());
        assertNull(dto.getCommentsToApprover());
        assertNull(dto.getReviewedBy());
        assertNull(dto.getReviewedDatetime());
        assertNull(dto.getReviewComments());
        assertNull(dto.getOverrideBy());
        assertNull(dto.getOverrideDatetime());
        assertNull(dto.getOverrideJustification());
    }


    private ProductCreateInDto createCreateDto() {
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
                .prin("PRIN123")
                .cwsProductId("CWS456")
                .chaCode("CHA789")
                .upc("UPC001")
                .startDate(LocalDateTime.now().plusWeeks(2))
                .endDate(LocalDateTime.now().plusYears(1))
                .build();
    }

    private ProductUpdateInDto createUpdateDto() {
        return ProductUpdateInDto.builder()
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
                .prin("PRIN123")
                .cwsProductId("CWS456")
                .chaCode("CHA789")
                .upc("UPC001")
                .startDate(LocalDateTime.now().plusWeeks(2))
                .endDate(LocalDateTime.now().plusYears(1))
                .build();
    }
}