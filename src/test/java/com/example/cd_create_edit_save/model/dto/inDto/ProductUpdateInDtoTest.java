package com.example.cd_create_edit_save.model.dto.inDto;

import com.example.cd_create_edit_save.model.dto.ProductUpdateInDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductUpdateInDtoTest {

    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        ProductUpdateInDto dto = new ProductUpdateInDto();

        String productShtCd = "PLU";
        dto.setProductShtCd(productShtCd);
        assertEquals(productShtCd, dto.getProductShtCd());

        String feeTypeShtCd = "MF";
        dto.setFeeTypeShtCd(feeTypeShtCd);
        assertEquals(feeTypeShtCd, dto.getFeeTypeShtCd());

        String rewardsTypeShtCd = "TR";
        dto.setRewardsTypeShtCd(rewardsTypeShtCd);
        assertEquals(rewardsTypeShtCd, dto.getRewardsTypeShtCd());

        String aprType = "VARIABLE";
        dto.setAprType(aprType);
        assertEquals(aprType, dto.getAprType());

        String aprValueType = "RANGE";
        dto.setAprValueType(aprValueType);
        assertEquals(aprValueType, dto.getAprValueType());

        BigDecimal purchaseAprMin = new BigDecimal("12.50");
        dto.setPurchaseAprMin(purchaseAprMin);
        assertEquals(purchaseAprMin, dto.getPurchaseAprMin());

        BigDecimal purchaseAprMax = new BigDecimal("18.50");
        dto.setPurchaseAprMax(purchaseAprMax);
        assertEquals(purchaseAprMax, dto.getPurchaseAprMax());

        BigDecimal cashAprMin = new BigDecimal("13.13");
        dto.setCashAprMin(cashAprMin);
        assertEquals(cashAprMin, dto.getCashAprMin());

        BigDecimal cashAprMax = new BigDecimal("19.43");
        dto.setCashAprMax(cashAprMax);
        assertEquals(cashAprMax, dto.getCashAprMax());

        String termsLink = "https://example.com/terms-updated";
        dto.setTermsConditionsLink(termsLink);
        assertEquals(termsLink, dto.getTermsConditionsLink());

        String agreementLink = "https://example.com/agreement-updated";
        dto.setCardholderAgreementLink(agreementLink);
        assertEquals(agreementLink, dto.getCardholderAgreementLink());

        String imageLink = "https://example.com/image-updated.png";
        dto.setCardImageLink(imageLink);
        assertEquals(imageLink, dto.getCardImageLink());

        Integer creditLineMin = 2000;
        dto.setCreditLineMin(creditLineMin);
        assertEquals(creditLineMin, dto.getCreditLineMin());

        Integer creditLineMax = 10000;
        dto.setCreditLineMax(creditLineMax);
        assertEquals(creditLineMax, dto.getCreditLineMax());

        String securityDepositIndicator = "N";
        dto.setSecurityDepositIndicator(securityDepositIndicator);
        assertEquals(securityDepositIndicator, dto.getSecurityDepositIndicator());

        Integer securityDepositMin = null;
        dto.setSecurityDepositMin(securityDepositMin);
        assertNull(dto.getSecurityDepositMin());

        Integer securityDepositMax = null;
        dto.setSecurityDepositMax(securityDepositMax);
        assertNull(dto.getSecurityDepositMax());

        String toBeApprovedBy = "jsmith";
        dto.setToBeApprovedBy(toBeApprovedBy);
        assertEquals(toBeApprovedBy, dto.getToBeApprovedBy());

        String approvalPriorityLevel = "NORMAL PRIORITY";
        dto.setApprovalPriorityLevel(approvalPriorityLevel);
        assertEquals(approvalPriorityLevel, dto.getApprovalPriorityLevel());

        String commentsToApprover = "Standard review needed";
        dto.setCommentsToApprover(commentsToApprover);
        assertEquals(commentsToApprover, dto.getCommentsToApprover());

        String prin = "PRIN002";
        dto.setPrin(prin);
        assertEquals(prin, dto.getPrin());

        String cwsProductId = "CWS-002";
        dto.setCwsProductId(cwsProductId);
        assertEquals(cwsProductId, dto.getCwsProductId());

        String chaCode = "CHA-MOB";
        dto.setChaCode(chaCode);
        assertEquals(chaCode, dto.getChaCode());

        Boolean pcFlag1 = false;
        dto.setPcFlag1(pcFlag1);
        assertEquals(pcFlag1, dto.getPcFlag1());

        Boolean pcFlag2 = true;
        dto.setPcFlag2(pcFlag2);
        assertEquals(pcFlag2, dto.getPcFlag2());

        Boolean pcFlag3 = false;
        dto.setPcFlag3(pcFlag3);
        assertEquals(pcFlag3, dto.getPcFlag3());

        Boolean pcFlag4 = true;
        dto.setPcFlag4(pcFlag4);
        assertEquals(pcFlag4, dto.getPcFlag4());

        Boolean pcFlag5 = false;
        dto.setPcFlag5(pcFlag5);
        assertEquals(pcFlag5, dto.getPcFlag5());

        Boolean pcFlag6 = true;
        dto.setPcFlag6(pcFlag6);
        assertEquals(pcFlag6, dto.getPcFlag6());

        Boolean pcFlag7 = false;
        dto.setPcFlag7(pcFlag7);
        assertEquals(pcFlag7, dto.getPcFlag7());

        Boolean pcFlag8 = true;
        dto.setPcFlag8(pcFlag8);
        assertEquals(pcFlag8, dto.getPcFlag8());

        Boolean pcFlag9 = false;
        dto.setPcFlag9(pcFlag9);
        assertEquals(pcFlag9, dto.getPcFlag9());

        Boolean pcFlag10 = true;
        dto.setPcFlag10(pcFlag10);
        assertEquals(pcFlag10, dto.getPcFlag10());

        String upc = "UPC002";
        dto.setUpc(upc);
        assertEquals(upc, dto.getUpc());

        LocalDateTime startDate = LocalDateTime.now().plusWeeks(3);
        dto.setStartDate(startDate);
        assertEquals(startDate, dto.getStartDate());

        LocalDateTime endDate = LocalDateTime.now().plusYears(1);
        dto.setEndDate(endDate);
        assertEquals(endDate, dto.getEndDate());
    }

    @Test
    void testAllArgsConstructorAndEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime future = now.plusMonths(12);

        ProductUpdateInDto dto1 = new ProductUpdateInDto(
                "PLU", "MF", "TR", "VARIABLE", "RANGE",
                new BigDecimal("12.50"), new BigDecimal("18.50"),
                new BigDecimal("13.13"), new BigDecimal("19.43"),
                "https://example.com/terms", "https://example.com/agreement", "https://example.com/image.png",
                2000, 10000, "N", null, null,
                "jsmith", "NORMAL PRIORITY", "Standard review needed",
                "PRIN002", "CWS-002", "CHA-MOB",
                false, true, false, true, false, true, false, true, false, true,
                "UPC002", now, future
        );

        ProductUpdateInDto dto2 = new ProductUpdateInDto(
                "PLU", "MF", "TR", "VARIABLE", "RANGE",
                new BigDecimal("12.50"), new BigDecimal("18.50"),
                new BigDecimal("13.13"), new BigDecimal("19.43"),
                "https://example.com/terms", "https://example.com/agreement", "https://example.com/image.png",
                2000, 10000, "N", null, null,
                "jsmith", "NORMAL PRIORITY", "Standard review needed",
                "PRIN002", "CWS-002", "CHA-MOB",
                false, true, false, true, false, true, false, true, false, true,
                "UPC002", now, future
        );

        ProductUpdateInDto dto3 = new ProductUpdateInDto();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testBuilder() {
        LocalDateTime startDate = LocalDateTime.now().plusWeeks(3);
        LocalDateTime endDate = LocalDateTime.now().plusYears(1);

        ProductUpdateInDto dto = ProductUpdateInDto.builder()
                .productShtCd("PLU")
                .feeTypeShtCd("MF")
                .rewardsTypeShtCd("TR")
                .aprType("VARIABLE")
                .aprValueType("RANGE")
                .purchaseAprMin(new BigDecimal("12.50"))
                .purchaseAprMax(new BigDecimal("18.50"))
                .cashAprMin(new BigDecimal("13.13"))
                .cashAprMax(new BigDecimal("19.43"))
                .termsConditionsLink("https://example.com/terms")
                .cardholderAgreementLink("https://example.com/agreement")
                .cardImageLink("https://example.com/image.png")
                .creditLineMin(2000)
                .creditLineMax(10000)
                .securityDepositIndicator("N")
                .securityDepositMin(null)
                .securityDepositMax(null)
                .toBeApprovedBy("jsmith")
                .approvalPriorityLevel("NORMAL PRIORITY")
                .commentsToApprover("Standard review needed")
                .prin("PRIN002")
                .cwsProductId("CWS-002")
                .chaCode("CHA-MOB")
                .pcFlag1(false)
                .pcFlag2(true)
                .pcFlag3(false)
                .pcFlag4(true)
                .pcFlag5(false)
                .pcFlag6(true)
                .pcFlag7(false)
                .pcFlag8(true)
                .pcFlag9(false)
                .pcFlag10(true)
                .upc("UPC002")
                .startDate(startDate)
                .endDate(endDate)
                .build();

        assertEquals("PLU", dto.getProductShtCd());
        assertEquals("MF", dto.getFeeTypeShtCd());
        assertEquals("TR", dto.getRewardsTypeShtCd());
        assertEquals("VARIABLE", dto.getAprType());
        assertEquals("RANGE", dto.getAprValueType());
        assertEquals(new BigDecimal("12.50"), dto.getPurchaseAprMin());
        assertEquals(new BigDecimal("18.50"), dto.getPurchaseAprMax());
        assertEquals(new BigDecimal("13.13"), dto.getCashAprMin());
        assertEquals(new BigDecimal("19.43"), dto.getCashAprMax());
        assertEquals("https://example.com/terms", dto.getTermsConditionsLink());
        assertEquals("https://example.com/agreement", dto.getCardholderAgreementLink());
        assertEquals("https://example.com/image.png", dto.getCardImageLink());
        assertEquals(2000, dto.getCreditLineMin());
        assertEquals(10000, dto.getCreditLineMax());
        assertEquals("N", dto.getSecurityDepositIndicator());
        assertNull(dto.getSecurityDepositMin());
        assertNull(dto.getSecurityDepositMax());
        assertEquals("jsmith", dto.getToBeApprovedBy());
        assertEquals("NORMAL PRIORITY", dto.getApprovalPriorityLevel());
        assertEquals("Standard review needed", dto.getCommentsToApprover());
        assertEquals("PRIN002", dto.getPrin());
        assertEquals("CWS-002", dto.getCwsProductId());
        assertEquals("CHA-MOB", dto.getChaCode());
        assertFalse(dto.getPcFlag1());
        assertTrue(dto.getPcFlag2());
        assertFalse(dto.getPcFlag3());
        assertTrue(dto.getPcFlag4());
        assertFalse(dto.getPcFlag5());
        assertTrue(dto.getPcFlag6());
        assertFalse(dto.getPcFlag7());
        assertTrue(dto.getPcFlag8());
        assertFalse(dto.getPcFlag9());
        assertTrue(dto.getPcFlag10());
        assertEquals("UPC002", dto.getUpc());
        assertEquals(startDate, dto.getStartDate());
        assertEquals(endDate, dto.getEndDate());
    }

    @Test
    void testToString() {
        ProductUpdateInDto dto = ProductUpdateInDto.builder()
                .productShtCd("PLU")
                .feeTypeShtCd("MF")
                .rewardsTypeShtCd("TR")
                .aprType("VARIABLE")
                .prin("PRIN002")
                .cwsProductId("CWS-002")
                .chaCode("CHA-MOB")
                .build();

        String result = dto.toString();

        assertTrue(result.contains("PLU"));
        assertTrue(result.contains("MF"));
        assertTrue(result.contains("TR"));
        assertTrue(result.contains("VARIABLE"));
        assertTrue(result.contains("PRIN002"));
        assertTrue(result.contains("CWS-002"));
        assertTrue(result.contains("CHA-MOB"));
    }

    @Test
    void testEqualsWithNullAndDifferentClass() {
        ProductUpdateInDto dto1 = ProductUpdateInDto.builder()
                .productShtCd("PLU")
                .build();

        assertNotEquals(dto1, null);
        assertNotEquals(dto1, new Object());
        assertEquals(dto1, dto1);
    }

    @Test
    void testBuilderWithSecurityDepositEnabled() {
        ProductUpdateInDto dto = ProductUpdateInDto.builder()
                .productShtCd("GOL")
                .securityDepositIndicator("Y")
                .securityDepositMin(300)
                .securityDepositMax(1000)
                .build();

        assertEquals("Y", dto.getSecurityDepositIndicator());
        assertEquals(300, dto.getSecurityDepositMin());
        assertEquals(1000, dto.getSecurityDepositMax());
    }

    @Test
    void testBuilderWithSecurityDepositDisabled() {
        ProductUpdateInDto dto = ProductUpdateInDto.builder()
                .productShtCd("GOL")
                .securityDepositIndicator("N")
                .securityDepositMin(null)
                .securityDepositMax(null)
                .build();

        assertEquals("N", dto.getSecurityDepositIndicator());
        assertNull(dto.getSecurityDepositMin());
        assertNull(dto.getSecurityDepositMax());
    }
}
