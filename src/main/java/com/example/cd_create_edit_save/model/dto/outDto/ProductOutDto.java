package com.example.cd_create_edit_save.model.dto.outDto;

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
public class ProductOutDto {

    private String productId;
    private String productShtCd;
    private String feeTypeShtCd;
    private String rewardsTypeShtCd;
    private String aprType;
    private String aprValueType;
    private BigDecimal purchaseAprMin;
    private BigDecimal purchaseAprMax;
    private BigDecimal cashAprMin;
    private BigDecimal cashAprMax;
    private Integer creditLineMin;
    private Integer creditLineMax;
    private String securityDepositIndicator;
    private Integer securityDepositMin;
    private Integer securityDepositMax;
    private String termsConditionsLink;
    private String cardholderAgreementLink;
    private String cardImageLink;
    private String status;
    private String createdBy;
    private LocalDateTime createdDatetime;
    private String reviewedBy;
    private LocalDateTime reviewedDatetime;
    private String reviewComments;
    private String overrideBy;
    private LocalDateTime overrideDatetime;
    private byte[] overrideJustification;
    private String prin;
    private String cwsProductId;
    private String chaCode;
    private String boardingIndicator;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
