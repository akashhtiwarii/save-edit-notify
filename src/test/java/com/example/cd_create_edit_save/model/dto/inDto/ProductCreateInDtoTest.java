package com.example.cd_create_edit_save.model.dto.inDto;

import com.example.cd_create_edit_save.model.dto.ProductCreateInDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductCreateInDtoTest {

    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        ProductCreateInDto dto = new ProductCreateInDto();

        String productShtCd = "GOL";
        dto.setProductShtCd(productShtCd);
        assertEquals(productShtCd, dto.getProductShtCd());

        String feeTypeShtCd = "AF";
        dto.setFeeTypeShtCd(feeTypeShtCd);
        assertEquals(feeTypeShtCd, dto.getFeeTypeShtCd());

        String rewardsTypeShtCd = "CB";
        dto.setRewardsTypeShtCd(rewardsTypeShtCd);
        assertEquals(rewardsTypeShtCd, dto.getRewardsTypeShtCd());

        String aprType = "FIXED";
        dto.setAprType(aprType);
        assertEquals(aprType, dto.getAprType());

        String aprValueType = "SPECIFIC";
        dto.setAprValueType(aprValueType);
        assertEquals(aprValueType, dto.getAprValueType());

        BigDecimal purchaseAprMin = new BigDecimal("10.50");
        dto.setPurchaseAprMin(purchaseAprMin);
        assertEquals(purchaseAprMin, dto.getPurchaseAprMin());

        BigDecimal purchaseAprMax = new BigDecimal("10.50");
        dto.setPurchaseAprMax(purchaseAprMax);
        assertEquals(purchaseAprMax, dto.getPurchaseAprMax());

        BigDecimal cashAprMin = new BigDecimal("11.03");
        dto.setCashAprMin(cashAprMin);
        assertEquals(cashAprMin, dto.getCashAprMin());

        BigDecimal cashAprMax = new BigDecimal("11.03");
        dto.setCashAprMax(cashAprMax);
        assertEquals(cashAprMax, dto.getCashAprMax());

        String termsLink = "https://example.com/terms";
        dto.setTermsConditionsLink(termsLink);
        assertEquals(termsLink, dto.getTermsConditionsLink());

        String agreementLink = "https://example.com/agreement";
        dto.setCardholderAgreementLink(agreementLink);
        assertEquals(agreementLink, dto.getCardholderAgreementLink());

        String imageLink = "https://example.com/image.png";
        dto.setCardImageLink(imageLink);
        assertEquals(imageLink, dto.getCardImageLink());

        Integer creditLineMin = 1000;
        dto.setCreditLineMin(creditLineMin);
        assertEquals(creditLineMin, dto.getCreditLineMin());

        Integer creditLineMax = 5000;
        dto.setCreditLineMax(creditLineMax);
        assertEquals(creditLineMax, dto.getCreditLineMax());

        String securityDepositIndicator = "Y";
        dto.setSecurityDepositIndicator(securityDepositIndicator);
        assertEquals(securityDepositIndicator, dto.getSecurityDepositIndicator());

        Integer securityDepositMin = 200;
        dto.setSecurityDepositMin(securityDepositMin);
        assertEquals(securityDepositMin, dto.getSecurityDepositMin());

        Integer securityDepositMax = 500;
        dto.setSecurityDepositMax(securityDepositMax);
        assertEquals(securityDepositMax, dto.getSecurityDepositMax());

        String toBeApprovedBy = "jdoe";
        dto.setToBeApprovedBy(toBeApprovedBy);
        assertEquals(toBeApprovedBy, dto.getToBeApprovedBy());

        String approvalPriorityLevel = "HIGH PRIORITY";
        dto.setApprovalPriorityLevel(approvalPriorityLevel);
        assertEquals(approvalPriorityLevel, dto.getApprovalPriorityLevel());

        String commentsToApprover = "Please review urgently";
        dto.setCommentsToApprover(commentsToApprover);
        assertEquals(commentsToApprover, dto.getCommentsToApprover());

        String prin = "PRIN001";
        dto.setPrin(prin);
        assertEquals(prin, dto.getPrin());

        String cwsProductId = "CWS-001";
        dto.setCwsProductId(cwsProductId);
        assertEquals(cwsProductId, dto.getCwsProductId());

        String chaCode = "CHA-WEB";
        dto.setChaCode(chaCode);
        assertEquals(chaCode, dto.getChaCode());

        Boolean pcFlag1 = true;
        dto.setPcFlag1(pcFlag1);
        assertEquals(pcFlag1, dto.getPcFlag1());

        Boolean pcFlag2 = false;
        dto.setPcFlag2(pcFlag2);
        assertEquals(pcFlag2, dto.getPcFlag2());

        Boolean pcFlag3 = true;
        dto.setPcFlag3(pcFlag3);
        assertEquals(pcFlag3, dto.getPcFlag3());

        Boolean pcFlag4 = false;
        dto.setPcFlag4(pcFlag4);
        assertEquals(pcFlag4, dto.getPcFlag4());

        Boolean pcFlag5 = true;
        dto.setPcFlag5(pcFlag5);
        assertEquals(pcFlag5, dto.getPcFlag5());

        Boolean pcFlag6 = false;
        dto.setPcFlag6(pcFlag6);
        assertEquals(pcFlag6, dto.getPcFlag6());

        Boolean pcFlag7 = true;
        dto.setPcFlag7(pcFlag7);
        assertEquals(pcFlag7, dto.getPcFlag7());

        Boolean pcFlag8 = false;
        dto.setPcFlag8(pcFlag8);
        assertEquals(pcFlag8, dto.getPcFlag8());

        Boolean pcFlag9 = true;
        dto.setPcFlag9(pcFlag9);
        assertEquals(pcFlag9, dto.getPcFlag9());

        Boolean pcFlag10 = false;
        dto.setPcFlag10(pcFlag10);
        assertEquals(pcFlag10, dto.getPcFlag10());

        String upc = "UPC001";
        dto.setUpc(upc);
        assertEquals(upc, dto.getUpc());

        LocalDateTime startDate = LocalDateTime.now().plusWeeks(2);
        dto.setStartDate(startDate);
        assertEquals(startDate, dto.getStartDate());

        LocalDateTime endDate = LocalDateTime.now().plusMonths(6);
        dto.setEndDate(endDate);
        assertEquals(endDate, dto.getEndDate());
    }

    @Test
    void testAllArgsConstructorAndEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime future = now.plusMonths(6);

        ProductCreateInDto dto1 = new ProductCreateInDto(
                "GOL", "AF", "CB", "FIXED", "SPECIFIC",
                new BigDecimal("10.50"), new BigDecimal("10.50"),
                new BigDecimal("11.03"), new BigDecimal("11.03"),
                "https://example.com/terms", "https://example.com/agreement", "https://example.com/image.png",
                1000, 5000, "Y", 200, 500,
                "jdoe", "HIGH PRIORITY", "Please review urgently",
                "PRIN001", "CWS-001", "CHA-WEB",
                true, false, true, false, true, false, true, false, true, false,
                "UPC001", now, future
        );

        ProductCreateInDto dto2 = new ProductCreateInDto(
                "GOL", "AF", "CB", "FIXED", "SPECIFIC",
                new BigDecimal("10.50"), new BigDecimal("10.50"),
                new BigDecimal("11.03"), new BigDecimal("11.03"),
                "https://example.com/terms", "https://example.com/agreement", "https://example.com/image.png",
                1000, 5000, "Y", 200, 500,
                "jdoe", "HIGH PRIORITY", "Please review urgently",
                "PRIN001", "CWS-001", "CHA-WEB",
                true, false, true, false, true, false, true, false, true, false,
                "UPC001", now, future
        );

        ProductCreateInDto dto3 = new ProductCreateInDto();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testBuilder() {
        LocalDateTime startDate = LocalDateTime.now().plusWeeks(2);
        LocalDateTime endDate = LocalDateTime.now().plusMonths(6);

        ProductCreateInDto dto = ProductCreateInDto.builder()
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
                .pcFlag3(true)
                .pcFlag4(false)
                .pcFlag5(true)
                .pcFlag6(false)
                .pcFlag7(true)
                .pcFlag8(false)
                .pcFlag9(true)
                .pcFlag10(false)
                .upc("UPC001")
                .startDate(startDate)
                .endDate(endDate)
                .build();

        assertEquals("GOL", dto.getProductShtCd());
        assertEquals("AF", dto.getFeeTypeShtCd());
        assertEquals("CB", dto.getRewardsTypeShtCd());
        assertEquals("FIXED", dto.getAprType());
        assertEquals("SPECIFIC", dto.getAprValueType());
        assertEquals(new BigDecimal("10.50"), dto.getPurchaseAprMin());
        assertEquals(new BigDecimal("10.50"), dto.getPurchaseAprMax());
        assertEquals(new BigDecimal("11.03"), dto.getCashAprMin());
        assertEquals(new BigDecimal("11.03"), dto.getCashAprMax());
        assertEquals("https://example.com/terms", dto.getTermsConditionsLink());
        assertEquals("https://example.com/agreement", dto.getCardholderAgreementLink());
        assertEquals("https://example.com/image.png", dto.getCardImageLink());
        assertEquals(1000, dto.getCreditLineMin());
        assertEquals(5000, dto.getCreditLineMax());
        assertEquals("Y", dto.getSecurityDepositIndicator());
        assertEquals(200, dto.getSecurityDepositMin());
        assertEquals(500, dto.getSecurityDepositMax());
        assertEquals("jdoe", dto.getToBeApprovedBy());
        assertEquals("HIGH PRIORITY", dto.getApprovalPriorityLevel());
        assertEquals("Please review urgently", dto.getCommentsToApprover());
        assertEquals("PRIN001", dto.getPrin());
        assertEquals("CWS-001", dto.getCwsProductId());
        assertEquals("CHA-WEB", dto.getChaCode());
        assertTrue(dto.getPcFlag1());
        assertFalse(dto.getPcFlag2());
        assertTrue(dto.getPcFlag3());
        assertFalse(dto.getPcFlag4());
        assertTrue(dto.getPcFlag5());
        assertFalse(dto.getPcFlag6());
        assertTrue(dto.getPcFlag7());
        assertFalse(dto.getPcFlag8());
        assertTrue(dto.getPcFlag9());
        assertFalse(dto.getPcFlag10());
        assertEquals("UPC001", dto.getUpc());
        assertEquals(startDate, dto.getStartDate());
        assertEquals(endDate, dto.getEndDate());
    }

    @Test
    void testToString() {
        ProductCreateInDto dto = ProductCreateInDto.builder()
                .productShtCd("GOL")
                .feeTypeShtCd("AF")
                .rewardsTypeShtCd("CB")
                .aprType("FIXED")
                .prin("PRIN001")
                .cwsProductId("CWS-001")
                .chaCode("CHA-WEB")
                .build();

        String result = dto.toString();

        assertTrue(result.contains("GOL"));
        assertTrue(result.contains("AF"));
        assertTrue(result.contains("CB"));
        assertTrue(result.contains("FIXED"));
        assertTrue(result.contains("PRIN001"));
        assertTrue(result.contains("CWS-001"));
        assertTrue(result.contains("CHA-WEB"));
    }

    @Test
    void testEqualsWithNullAndDifferentClass() {
        ProductCreateInDto dto1 = ProductCreateInDto.builder()
                .productShtCd("GOL")
                .build();

        assertNotEquals(dto1, null);
        assertNotEquals(dto1, new Object());
        assertEquals(dto1, dto1);
    }
}
