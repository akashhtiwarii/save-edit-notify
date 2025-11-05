package com.example.cd_create_edit_save.model.dto.outDto;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductOutDtoTest {

    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        ProductOutDto dto = new ProductOutDto();

        String productId = "PRD-001";
        String productShtCd = "ABC";
        String feeTypeShtCd = "FT";
        String rewardsTypeShtCd = "RW";
        String aprType = "Fixed";
        String aprValueType = "Percent";

        BigDecimal purchaseAprMin = new BigDecimal("10.5");
        BigDecimal purchaseAprMax = new BigDecimal("15.5");
        BigDecimal cashAprMin = new BigDecimal("12.5");
        BigDecimal cashAprMax = new BigDecimal("18.0");

        String termsLink = "https://terms.com";
        String agreementLink = "https://agreement.com";
        String imageLink = "https://image.com";

        Integer creditLineMin = 1000;
        Integer creditLineMax = 5000;
        String securityDepositIndicator = "Y";
        Integer securityDepositMin = 200;
        Integer securityDepositMax = 500;

        String prin = "PRIN001";
        String cwsProductId = "CWS001";
        String chaCode = "CH001";
        String boardingIndicator = "Yes";

        LocalDateTime startDate = LocalDateTime.now().minusDays(5);
        LocalDateTime endDate = LocalDateTime.now().plusDays(5);

        String status = "ACTIVE";
        String toBeApprovedBy = "manager@example.com";
        String approvalPriorityLevel = "High";
        String commentsToApprover = "Please review soon.";

        String reviewedBy = "Reviewer";
        LocalDateTime reviewedDatetime = LocalDateTime.now();
        String reviewComments = "All good.";

        String overrideBy = "Admin";
        LocalDateTime overrideDatetime = LocalDateTime.now();
        byte[] overrideJustification = "Justified".getBytes();

        String createdBy = "Creator";
        LocalDateTime createdDatetime = LocalDateTime.now();

        dto.setProductId(productId);
        dto.setProductShtCd(productShtCd);
        dto.setFeeTypeShtCd(feeTypeShtCd);
        dto.setRewardsTypeShtCd(rewardsTypeShtCd);
        dto.setAprType(aprType);
        dto.setAprValueType(aprValueType);
        dto.setPurchaseAprMin(purchaseAprMin);
        dto.setPurchaseAprMax(purchaseAprMax);
        dto.setCashAprMin(cashAprMin);
        dto.setCashAprMax(cashAprMax);
        dto.setTermsConditionsLink(termsLink);
        dto.setCardholderAgreementLink(agreementLink);
        dto.setCardImageLink(imageLink);
        dto.setCreditLineMin(creditLineMin);
        dto.setCreditLineMax(creditLineMax);
        dto.setSecurityDepositIndicator(securityDepositIndicator);
        dto.setSecurityDepositMin(securityDepositMin);
        dto.setSecurityDepositMax(securityDepositMax);
        dto.setPrin(prin);
        dto.setCwsProductId(cwsProductId);
        dto.setChaCode(chaCode);
        dto.setBoardingIndicator(boardingIndicator);
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        dto.setStatus(status);
        dto.setToBeApprovedBy(toBeApprovedBy);
        dto.setApprovalPriorityLevel(approvalPriorityLevel);
        dto.setCommentsToApprover(commentsToApprover);
        dto.setReviewedBy(reviewedBy);
        dto.setReviewedDatetime(reviewedDatetime);
        dto.setReviewComments(reviewComments);
        dto.setOverrideBy(overrideBy);
        dto.setOverrideDatetime(overrideDatetime);
        dto.setOverrideJustification(overrideJustification);
        dto.setCreatedBy(createdBy);
        dto.setCreatedDatetime(createdDatetime);

        assertEquals(productId, dto.getProductId());
        assertEquals(productShtCd, dto.getProductShtCd());
        assertEquals(feeTypeShtCd, dto.getFeeTypeShtCd());
        assertEquals(rewardsTypeShtCd, dto.getRewardsTypeShtCd());
        assertEquals(aprType, dto.getAprType());
        assertEquals(approvalPriorityLevel, dto.getApprovalPriorityLevel());
        assertEquals(createdBy, dto.getCreatedBy());
        assertArrayEquals(overrideJustification, dto.getOverrideJustification());
    }

    @Test
    void testAllArgsConstructorAndEqualsAndHashCode() {
        byte[] justification = "data".getBytes();
        LocalDateTime now = LocalDateTime.now();

        ProductOutDto dto1 = new ProductOutDto(
                "P1", "A1", "FT", "RW", "APR", "VAL",
                BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.TEN,
                "t1", "a1", "i1",
                100, 500, "Y", 200, 400,
                "PRIN1", "CWS1", "CH1", "Yes",
                now, now.plusDays(1), "ACTIVE", "manager", "High", "OK",
                "rev", now, "fine", "admin", now, justification, "creator", now
        );

        ProductOutDto dto2 = new ProductOutDto(
                "P1", "A1", "FT", "RW", "APR", "VAL",
                BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ONE, BigDecimal.TEN,
                "t1", "a1", "i1",
                100, 500, "Y", 200, 400,
                "PRIN1", "CWS1", "CH1", "Yes",
                now, now.plusDays(1), "ACTIVE", "manager", "High", "OK",
                "rev", now, "fine", "admin", now, justification, "creator", now
        );

        ProductOutDto dto3 = new ProductOutDto();

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1, dto3);
    }

    @Test
    void testBuilder() {
        byte[] justification = "builder".getBytes();
        LocalDateTime now = LocalDateTime.now();

        ProductOutDto dto = ProductOutDto.builder()
                .productId("P100")
                .productShtCd("AB1")
                .feeTypeShtCd("FT")
                .rewardsTypeShtCd("RW")
                .aprType("Variable")
                .aprValueType("Percent")
                .purchaseAprMin(new BigDecimal("5.0"))
                .purchaseAprMax(new BigDecimal("15.0"))
                .cashAprMin(new BigDecimal("10.0"))
                .cashAprMax(new BigDecimal("20.0"))
                .status("ACTIVE")
                .overrideJustification(justification)
                .createdDatetime(now)
                .build();

        assertEquals("P100", dto.getProductId());
        assertEquals("AB1", dto.getProductShtCd());
        assertEquals("ACTIVE", dto.getStatus());
        assertArrayEquals(justification, dto.getOverrideJustification());
    }

    @Test
    void testToString() {
        ProductOutDto dto = ProductOutDto.builder()
                .productId("P200")
                .status("INACTIVE")
                .build();

        String result = dto.toString();

        assertTrue(result.contains("P200"));
        assertTrue(result.contains("INACTIVE"));
    }
}

