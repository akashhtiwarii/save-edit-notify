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

    @Column(name = "PURCHASE_APR_TYPE", length = 50)
    private String purchaseAprType;

    @Column(name = "PURCHASE_APR_MIN", precision = 5, scale = 2)
    private BigDecimal purchaseAprMin;

    @Column(name = "PURCHASE_APR_MAX", precision = 5, scale = 2)
    private BigDecimal purchaseAprMax;

    @Column(name = "CASH_APR_TYPE", length = 50)
    private String cashAprType;

    @Column(name = "CASH_APR_MIN", precision = 5, scale = 2)
    private BigDecimal cashAprMin;

    @Column(name = "CASH_APR_MAX", precision = 5, scale = 2)
    private BigDecimal cashAprMax;

    @Column(name = "CREDIT_LINE_MIN")
    private Integer creditLineMin;

    @Column(name = "CREDIT_LINE_MAX")
    private Integer creditLineMax;

    @Column(name = "SECURITY_DEPOSIT_VALUE")
    private Integer securityDepositValue;

    @Column(name = "SECURITY_DEPOSIT_INDICATOR", length = 1)
    private String securityDepositIndicator;

    @Column(name = "DEPOSIT_MIN")
    private Integer depositMin;

    @Column(name = "DEPOSIT_MAX")
    private Integer depositMax;

    @Column(name = "TERMS_CONDITIONS", length = 5000)
    private String termsConditions;

    @Column(name = "CARDHOLDER_AGREEMENT", length = 5000)
    private String cardholderAgreement;

    @Column(name = "CARD_IMAGE", length = 500)
    private String cardImage;

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

    @Column(name = "OVERRIDE_BY", length = 100)
    private String overrideBy;

    @Column(name = "OVERRIDE_DATETIME")
    private LocalDateTime overrideDatetime;

    @Column(name = "override_justification", columnDefinition = "BYTEA")
    private byte[] overrideJustification;

    @Column(name = "REVIEW_COMMENTS", length = 1000)
    private String reviewComments;

    @Column(name = "PRIN")
    private Integer prin;

    @Column(name = "CWS_PRODUCT_ID")
    private Integer cwsProductId;

    @Column(name = "CHA_CODE", length = 4)
    private String chaCode;

    @Column(name = "BOARDING_INDICATOR", columnDefinition = "text[]")
    private String[] boardingIndicator;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;
}