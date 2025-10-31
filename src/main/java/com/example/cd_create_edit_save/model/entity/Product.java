package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_PRODUCT")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {

    @Id
    @Column(name = "PRODUCT_ID", length = 50)
    private String productId;

    @Column(name = "PRODUCT_SHT_CD", length = 3, nullable = false)
    private String productShtCd;

    @Column(name = "FEE_TYPE_SHT_CD", length = 2, nullable = false)
    private String feeTypeShtCd;

    @Column(name = "REWARDS_TYPE_SHT_CD", length = 2, nullable = false)
    private String rewardsTypeShtCd;

    @Column(name = "APR_TYPE", length = 50)
    private String aprType;

    @Column(name = "APR_VALUE_TYPE", length = 50)
    private String aprValueType;

    @Column(name = "PURCHASE_APR_MIN", precision = 5, scale = 2)
    private BigDecimal purchaseAprMin;

    @Column(name = "PURCHASE_APR_MAX", precision = 5, scale = 2)
    private BigDecimal purchaseAprMax;

    @Column(name = "CASH_APR_MIN", precision = 5, scale = 2)
    private BigDecimal cashAprMin;

    @Column(name = "CASH_APR_MAX", precision = 5, scale = 2)
    private BigDecimal cashAprMax;

    @Column(name = "CREDIT_LINE_MIN")
    private Integer creditLineMin;

    @Column(name = "CREDIT_LINE_MAX")
    private Integer creditLineMax;

    @Column(name = "SECURITY_DEPOSIT_INDICATOR", length = 1)
    private String securityDepositIndicator;

    @Column(name = "SECURITY_DEPOSIT_MIN")
    private Integer securityDepositMin;

    @Column(name = "SECURITY_DEPOSIT_MAX")
    private Integer securityDepositMax;

    @Column(name = "TERMS_CONDITIONS_LINK", length = 5000)
    private String termsConditionsLink;

    @Column(name = "CARDHOLDER_AGREEMENT_LINK", length = 5000)
    private String cardholderAgreementLink;

    @Column(name = "CARD_IMAGE_LINK", length = 500)
    private String cardImageLink;

    @Column(name = "STATUS", length = 50)
    private String status;

    @Column(name = "CREATED_BY", length = 255)
    private String createdBy;

    @Column(name = "CREATED_DATETIME")
    private LocalDateTime createdDatetime;

    @Column(name = "REVIEWED_BY", length = 255)
    private String reviewedBy;

    @Column(name = "REVIEWED_DATETIME")
    private LocalDateTime reviewedDatetime;

    @Column(name = "REVIEW_COMMENTS", length = 1000)
    private String reviewComments;

    @Column(name = "OVERRIDE_BY", length = 100)
    private String overrideBy;

    @Column(name = "OVERRIDE_DATETIME")
    private LocalDateTime overrideDatetime;

    @Column(name = "OVERRIDE_JUSTIFICATION", columnDefinition = "BYTEA")
    private byte[] overrideJustification;

    @Column(name = "PRIN", length = 50)
    private String prin;

    @Column(name = "CWS_PRODUCT_ID", length = 50)
    private String cwsProductId;

    @Column(name = "CHA_CODE", length = 50)
    private String chaCode;

    @Column(name = "BOARDING_INDICATOR", columnDefinition = "TEXT")
    private String boardingIndicator;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;
}
