package com.example.cd_create_edit_save.mapper;

import com.example.cd_create_edit_save.model.dto.inDto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.inDto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.model.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
    }

    @Test
    void testToEntity_FromCreateDto_Success() {
        ProductCreateInDto dto = new ProductCreateInDto();
        dto.setProductShtCd("PROD");
        dto.setFeeTypeShtCd("FEE");
        dto.setRewardsTypeShtCd("REW");
        dto.setAprType("FIXED");
        dto.setAprValueType("SPECIFIC");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10.5));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10.5));
        dto.setCashAprMin(BigDecimal.valueOf(11.0));
        dto.setCashAprMax(BigDecimal.valueOf(11.0));
        dto.setCreditLineMin(100);
        dto.setCreditLineMax(200);
        dto.setSecurityDepositIndicator("Y");
        dto.setSecurityDepositMin(10);
        dto.setSecurityDepositMax(20);
        dto.setTermsConditionsLink("terms.com");
        dto.setCardholderAgreementLink("card.com");
        dto.setCardImageLink("image.com");
        dto.setToBeApprovedBy("manager");
        dto.setApprovalPriorityLevel("HIGH PRIORITY");
        dto.setCommentsToApprover("Please approve");
        dto.setPrin("PRIN1");
        dto.setCwsProductId("CWS1");
        dto.setChaCode("CHA1");
        dto.setPcFlag1(true);
        dto.setPcFlag3(true);
        dto.setUpc("UPC123");
        dto.setStartDate(LocalDateTime.now().plusDays(10));
        dto.setEndDate(LocalDateTime.now().plusDays(20));

        Product entity = productMapper.toEntity(dto, "P123", "admin");

        assertEquals("P123", entity.getProductId());
        assertEquals("PROD", entity.getProductShtCd());
        assertEquals("PENDING", entity.getStatus());
        assertEquals("admin", entity.getCreatedBy());
        assertTrue(entity.getBoardingIndicator().contains("PC_FLAG1"));
        assertTrue(entity.getBoardingIndicator().contains("PC_FLAG3"));
        assertTrue(entity.getBoardingIndicator().contains("UPC123"));
        assertEquals("PRIN1", entity.getPrin());
        assertNotNull(entity.getCreatedDatetime());
    }

    @Test
    void testToEntity_FromUpdateDto_Success() {
        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd("PROD");
        dto.setFeeTypeShtCd("FEE");
        dto.setRewardsTypeShtCd("REW");
        dto.setAprType("FIXED");
        dto.setAprValueType("SPECIFIC");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10));
        dto.setCashAprMin(BigDecimal.valueOf(10.5));
        dto.setCashAprMax(BigDecimal.valueOf(10.5));
        dto.setCreditLineMin(100);
        dto.setCreditLineMax(200);
        dto.setSecurityDepositIndicator("N");
        dto.setTermsConditionsLink("terms.com");
        dto.setCardholderAgreementLink("card.com");
        dto.setCardImageLink("image.com");
        dto.setToBeApprovedBy("manager");
        dto.setApprovalPriorityLevel("HIGH PRIORITY");
        dto.setCommentsToApprover("Review changes");
        dto.setPrin("PRIN1");
        dto.setCwsProductId("CWS1");
        dto.setChaCode("CHA1");
        dto.setPcFlag2(true);
        dto.setPcFlag5(true);
        dto.setUpc("UPC456");
        dto.setStartDate(LocalDateTime.now().plusDays(5));
        dto.setEndDate(LocalDateTime.now().plusDays(15));

        Product entity = productMapper.toEntity(dto, "P999", "editor");

        assertEquals("P999", entity.getProductId());
        assertEquals("REVISION_PENDING", entity.getStatus());
        assertEquals("editor", entity.getCreatedBy());
        assertTrue(entity.getBoardingIndicator().contains("PC_FLAG2"));
        assertTrue(entity.getBoardingIndicator().contains("PC_FLAG5"));
        assertTrue(entity.getBoardingIndicator().contains("UPC456"));
    }

    @Test
    void testToResponseDto_Success() {
        Product product = Product.builder()
                .productId("P123")
                .productShtCd("PROD")
                .feeTypeShtCd("FEE")
                .rewardsTypeShtCd("REW")
                .aprType("FIXED")
                .aprValueType("SPECIFIC")
                .purchaseAprMin(BigDecimal.valueOf(10))
                .purchaseAprMax(BigDecimal.valueOf(10))
                .cashAprMin(BigDecimal.valueOf(10.5))
                .cashAprMax(BigDecimal.valueOf(10.5))
                .creditLineMin(100)
                .creditLineMax(200)
                .securityDepositIndicator("Y")
                .securityDepositMin(10)
                .securityDepositMax(20)
                .termsConditionsLink("terms.com")
                .cardholderAgreementLink("card.com")
                .cardImageLink("image.com")
                .prin("PRIN1")
                .cwsProductId("CWS1")
                .chaCode("CHA1")
                .boardingIndicator("PC_FLAG1,UPC123")
                .status("APPROVED")
                .toBeApprovedBy("manager")
                .approvalPriorityLevel("HIGH PRIORITY")
                .commentsToApprover("Done")
                .createdBy("admin")
                .createdDatetime(LocalDateTime.now())
                .build();

        ProductOutDto dto = productMapper.toResponseDto(product);

        assertEquals(product.getProductId(), dto.getProductId());
        assertEquals("APPROVED", dto.getStatus());
        assertEquals("PC_FLAG1,UPC123", dto.getBoardingIndicator());
        assertEquals("HIGH PRIORITY", dto.getApprovalPriorityLevel());
    }

    @Test
    void testToDto_Success() {
        Product product = Product.builder()
                .productId("P001")
                .productShtCd("PROD")
                .feeTypeShtCd("FEE")
                .rewardsTypeShtCd("REW")
                .aprType("FIXED")
                .aprValueType("SPECIFIC")
                .purchaseAprMin(BigDecimal.TEN)
                .purchaseAprMax(BigDecimal.TEN)
                .cashAprMin(BigDecimal.valueOf(10.5))
                .cashAprMax(BigDecimal.valueOf(10.5))
                .creditLineMin(100)
                .creditLineMax(200)
                .securityDepositIndicator("N")
                .termsConditionsLink("terms.com")
                .cardholderAgreementLink("card.com")
                .cardImageLink("img.com")
                .status("ACTIVE")
                .createdBy("user1")
                .createdDatetime(LocalDateTime.now())
                .toBeApprovedBy("approver")
                .approvalPriorityLevel("HIGH PRIORITY")
                .commentsToApprover("ok")
                .reviewedBy("revUser")
                .reviewedDatetime(LocalDateTime.now())
                .reviewComments("good")
                .overrideBy("overrideUser")
                .overrideDatetime(LocalDateTime.now())
                .overrideJustification(null)
                .prin("PRIN1")
                .cwsProductId("CWS1")
                .chaCode("CHA1")
                .boardingIndicator("PC_FLAG3")
                .startDate(LocalDateTime.now().plusDays(1))
                .endDate(LocalDateTime.now().plusDays(2))
                .build();

        ProductOutDto dto = productMapper.toDto(product);

        assertNotNull(dto);
        assertEquals("P001", dto.getProductId());
        assertEquals("overrideUser", dto.getOverrideBy());
        assertEquals("PC_FLAG3", dto.getBoardingIndicator());
        assertEquals("ACTIVE", dto.getStatus());
        assertEquals("approver", dto.getToBeApprovedBy());
    }

    @Test
    void testToDto_NullInput_ReturnsNull() {
        assertNull(productMapper.toDto(null));
    }

    @Test
    void testBuildBoardingIndicator_EmptyFlags_ReturnsOnlyUpc() {
        ProductCreateInDto dto = new ProductCreateInDto();
        dto.setUpc("UPC999");

        Product entity = productMapper.toEntity(dto, "P888", "tester");

        assertEquals("UPC999", entity.getBoardingIndicator());
    }

    @Test
    void testBuildBoardingIndicator_AllFalseAndNullUpc_ReturnsEmptyString() {
        ProductCreateInDto dto = new ProductCreateInDto();
        Product entity = productMapper.toEntity(dto, "PID", "tester");

        assertTrue(entity.getBoardingIndicator().isEmpty());
    }
}

