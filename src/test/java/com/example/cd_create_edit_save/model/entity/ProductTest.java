package com.example.cd_create_edit_save.model.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testSettersAndGetters() {
        Product product = new Product();

        String productId = "P001";
        String productShtCd = "PRD";
        String feeTypeShtCd = "FT";
        String rewardsTypeShtCd = "RW";
        String aprType = "Fixed";
        String aprValueType = "Percentage";
        BigDecimal purchaseAprMin = new BigDecimal("10.50");
        BigDecimal purchaseAprMax = new BigDecimal("15.75");
        BigDecimal cashAprMin = new BigDecimal("5.25");
        BigDecimal cashAprMax = new BigDecimal("8.50");
        Integer creditLineMin = 1000;
        Integer creditLineMax = 5000;
        String securityDepositIndicator = "Y";
        Integer securityDepositMin = 200;
        Integer securityDepositMax = 1000;
        String termsConditionsLink = "http://example.com/terms";
        String cardholderAgreementLink = "http://example.com/agreement";
        String cardImageLink = "http://example.com/image.png";
        String status = "ACTIVE";
        String createdBy = "Admin";
        LocalDateTime createdDatetime = LocalDateTime.now();
        String reviewedBy = "Reviewer";
        LocalDateTime reviewedDatetime = LocalDateTime.now();
        String reviewComments = "Looks good";
        String overrideBy = "Manager";
        LocalDateTime overrideDatetime = LocalDateTime.now();
        byte[] overrideJustification = "Justified".getBytes();
        String prin = "Principal";
        String cwsProductId = "CWS123";
        String chaCode = "CH001";
        String boardingIndicator = "Y";
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now().plusDays(30);
        String toBeApprovedBy = "Approver";
        String approvalPriorityLevel = "High";
        String commentsToApprover = "Approve ASAP";

        product.setProductId(productId);
        product.setProductShtCd(productShtCd);
        product.setFeeTypeShtCd(feeTypeShtCd);
        product.setRewardsTypeShtCd(rewardsTypeShtCd);
        product.setAprType(aprType);
        product.setAprValueType(aprValueType);
        product.setPurchaseAprMin(purchaseAprMin);
        product.setPurchaseAprMax(purchaseAprMax);
        product.setCashAprMin(cashAprMin);
        product.setCashAprMax(cashAprMax);
        product.setCreditLineMin(creditLineMin);
        product.setCreditLineMax(creditLineMax);
        product.setSecurityDepositIndicator(securityDepositIndicator);
        product.setSecurityDepositMin(securityDepositMin);
        product.setSecurityDepositMax(securityDepositMax);
        product.setTermsConditionsLink(termsConditionsLink);
        product.setCardholderAgreementLink(cardholderAgreementLink);
        product.setCardImageLink(cardImageLink);
        product.setStatus(status);
        product.setCreatedBy(createdBy);
        product.setCreatedDatetime(createdDatetime);
        product.setReviewedBy(reviewedBy);
        product.setReviewedDatetime(reviewedDatetime);
        product.setReviewComments(reviewComments);
        product.setOverrideBy(overrideBy);
        product.setOverrideDatetime(overrideDatetime);
        product.setOverrideJustification(overrideJustification);
        product.setPrin(prin);
        product.setCwsProductId(cwsProductId);
        product.setChaCode(chaCode);
        product.setBoardingIndicator(boardingIndicator);
        product.setStartDate(startDate);
        product.setEndDate(endDate);
        product.setToBeApprovedBy(toBeApprovedBy);
        product.setApprovalPriorityLevel(approvalPriorityLevel);
        product.setCommentsToApprover(commentsToApprover);

        assertEquals(productId, product.getProductId());
        assertEquals(productShtCd, product.getProductShtCd());
        assertEquals(feeTypeShtCd, product.getFeeTypeShtCd());
        assertEquals(rewardsTypeShtCd, product.getRewardsTypeShtCd());
        assertEquals(aprType, product.getAprType());
        assertEquals(aprValueType, product.getAprValueType());
        assertEquals(purchaseAprMin, product.getPurchaseAprMin());
        assertEquals(purchaseAprMax, product.getPurchaseAprMax());
        assertEquals(cashAprMin, product.getCashAprMin());
        assertEquals(cashAprMax, product.getCashAprMax());
        assertEquals(creditLineMin, product.getCreditLineMin());
        assertEquals(creditLineMax, product.getCreditLineMax());
        assertEquals(securityDepositIndicator, product.getSecurityDepositIndicator());
        assertEquals(securityDepositMin, product.getSecurityDepositMin());
        assertEquals(securityDepositMax, product.getSecurityDepositMax());
        assertEquals(termsConditionsLink, product.getTermsConditionsLink());
        assertEquals(cardholderAgreementLink, product.getCardholderAgreementLink());
        assertEquals(cardImageLink, product.getCardImageLink());
        assertEquals(status, product.getStatus());
        assertEquals(createdBy, product.getCreatedBy());
        assertEquals(createdDatetime, product.getCreatedDatetime());
        assertEquals(reviewedBy, product.getReviewedBy());
        assertEquals(reviewedDatetime, product.getReviewedDatetime());
        assertEquals(reviewComments, product.getReviewComments());
        assertEquals(overrideBy, product.getOverrideBy());
        assertEquals(overrideDatetime, product.getOverrideDatetime());
        assertArrayEquals(overrideJustification, product.getOverrideJustification());
        assertEquals(prin, product.getPrin());
        assertEquals(cwsProductId, product.getCwsProductId());
        assertEquals(chaCode, product.getChaCode());
        assertEquals(boardingIndicator, product.getBoardingIndicator());
        assertEquals(startDate, product.getStartDate());
        assertEquals(endDate, product.getEndDate());
        assertEquals(toBeApprovedBy, product.getToBeApprovedBy());
        assertEquals(approvalPriorityLevel, product.getApprovalPriorityLevel());
        assertEquals(commentsToApprover, product.getCommentsToApprover());
    }

    @Test
    void testEqualsAndHashCode() {
        Product product1 = createSampleProduct();
        Product product2 = createSampleProduct();

        assertEquals(product1, product1);
        assertEquals(product1.hashCode(), product1.hashCode());
        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());

        product2.setProductId("P002");
        assertNotEquals(product1, product2);
        assertNotEquals(product1.hashCode(), product2.hashCode());

        product2 = createSampleProduct();
        product2.setStatus("INACTIVE");
        assertNotEquals(product1, product2);
        assertNotEquals(product1.hashCode(), product2.hashCode());

        assertNotEquals(product1, new Object());
        assertNotEquals(product1, null);

        Product empty1 = new Product();
        Product empty2 = new Product();
        assertEquals(empty1, empty2);
        assertEquals(empty1.hashCode(), empty2.hashCode());
    }

    @Test
    void testBuilderAndToString() {
        byte[] overrideJustification = "Test".getBytes();
        LocalDateTime now = LocalDateTime.now();

        Product product = Product.builder()
                .productId("P001")
                .productShtCd("PRD")
                .feeTypeShtCd("FT")
                .rewardsTypeShtCd("RW")
                .aprType("Fixed")
                .aprValueType("Percentage")
                .purchaseAprMin(new BigDecimal("10.00"))
                .purchaseAprMax(new BigDecimal("15.00"))
                .cashAprMin(new BigDecimal("5.00"))
                .cashAprMax(new BigDecimal("8.00"))
                .creditLineMin(1000)
                .creditLineMax(5000)
                .securityDepositIndicator("Y")
                .securityDepositMin(100)
                .securityDepositMax(500)
                .termsConditionsLink("link1")
                .cardholderAgreementLink("link2")
                .cardImageLink("link3")
                .status("ACTIVE")
                .createdBy("Admin")
                .createdDatetime(now)
                .reviewedBy("Reviewer")
                .reviewedDatetime(now)
                .reviewComments("Reviewed")
                .overrideBy("Manager")
                .overrideDatetime(now)
                .overrideJustification(overrideJustification)
                .prin("Principal")
                .cwsProductId("CWS123")
                .chaCode("CH001")
                .boardingIndicator("Y")
                .startDate(now)
                .endDate(now.plusDays(10))
                .toBeApprovedBy("Approver")
                .approvalPriorityLevel("High")
                .commentsToApprover("Approve soon")
                .build();

        assertNotNull(product.toString());
        assertTrue(product.toString().contains("P001"));
        assertTrue(Arrays.equals(overrideJustification, product.getOverrideJustification()));
    }

    private Product createSampleProduct() {
        Product product = new Product();
        product.setProductId("P001");
        product.setProductShtCd("PRD");
        product.setFeeTypeShtCd("FT");
        product.setRewardsTypeShtCd("RW");
        product.setStatus("ACTIVE");
        return product;
    }
}