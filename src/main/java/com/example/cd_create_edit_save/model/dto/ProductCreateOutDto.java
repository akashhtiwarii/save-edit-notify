package com.example.cd_create_edit_save.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateOutDto {

    private String productId;
    private String productShtCd;
    private String feeTypeShtCd;
    private String rewardsTypeShtCd;
    private String aprType;

    private BigDecimal purchaseAprMin;
    private BigDecimal purchaseAprMax;
    private BigDecimal cashAprMin;
    private BigDecimal cashAprMax;

    private String termsConditions;
    private String cardholderAgreement;
    private String cardImage;

    private Integer creditLineMin;
    private Integer creditLineMax;
    private String securityDepositIndicator;
    private Integer depositMin;
    private Integer depositMax;

    private String prin;
    private String cwsProductId;
    private String chaCode;
    private String[] boardingIndicator;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String status;
    private String reviewedBy;
    private String reviewComments;

    private String createdBy;
    private LocalDateTime createdDatetime;
}