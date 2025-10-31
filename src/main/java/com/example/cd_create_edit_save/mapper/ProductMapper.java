package com.example.cd_create_edit_save.mapper;

import com.example.cd_create_edit_save.model.dto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.ProductCreateOutDto;
import com.example.cd_create_edit_save.model.entity.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapper {

    public Product toEntity(ProductCreateInDto dto, String generatedProductId, String createdBy) {
        String[] boardingIndicators = buildBoardingIndicators(dto);

        return Product.builder()
                .productId(generatedProductId)
                .productShtCd(dto.getProductShtCd())
                .feeTypeShtCd(dto.getFeeTypeShtCd())
                .rewardsTypeShtCd(dto.getRewardsTypeShtCd())
                .aprType(dto.getAprType())
                .purchaseAprMin(dto.getPurchaseAprMin())
                .purchaseAprMax(dto.getPurchaseAprMax())
                .cashAprMin(dto.getCashAprMin())
                .cashAprMax(dto.getCashAprMax())
                .creditLineMin(dto.getCreditLineMin())
                .creditLineMax(dto.getCreditLineMax())
                .securityDepositIndicator(dto.getSecurityDepositIndicator())
                .depositMin(dto.getDepositMin())
                .depositMax(dto.getDepositMax())
                .termsConditions(dto.getTermsConditions())
                .cardholderAgreement(dto.getCardholderAgreement())
                .cardImage(dto.getCardImage())
                .status("PENDING")
                .createdBy(createdBy)
                .createdDatetime(LocalDateTime.now())
                .reviewedBy(dto.getReviewedBy())
                .reviewComments(dto.getReviewComments())
                .prin(dto.getPrin())
                .cwsProductId(dto.getCwsProductId())
                .chaCode(dto.getChaCode())
                .boardingIndicator(boardingIndicators)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }

    public ProductCreateOutDto toResponseDto(Product entity) {
        return ProductCreateOutDto.builder()
                .productId(entity.getProductId())
                .productShtCd(entity.getProductShtCd())
                .feeTypeShtCd(entity.getFeeTypeShtCd())
                .rewardsTypeShtCd(entity.getRewardsTypeShtCd())
                .aprType(entity.getAprType())
                .purchaseAprMin(entity.getPurchaseAprMin())
                .purchaseAprMax(entity.getPurchaseAprMax())
                .cashAprMin(entity.getCashAprMin())
                .cashAprMax(entity.getCashAprMax())
                .termsConditions(entity.getTermsConditions())
                .cardholderAgreement(entity.getCardholderAgreement())
                .cardImage(entity.getCardImage())
                .creditLineMin(entity.getCreditLineMin())
                .creditLineMax(entity.getCreditLineMax())
                .securityDepositIndicator(entity.getSecurityDepositIndicator())
                .depositMin(entity.getDepositMin())
                .depositMax(entity.getDepositMax())
                .prin(entity.getPrin())
                .cwsProductId(entity.getCwsProductId())
                .chaCode(entity.getChaCode())
                .boardingIndicator(entity.getBoardingIndicator())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(entity.getStatus())
                .reviewedBy(entity.getReviewedBy())
                .reviewComments(entity.getReviewComments())
                .createdBy(entity.getCreatedBy())
                .createdDatetime(entity.getCreatedDatetime())
                .build();
    }

    private String[] buildBoardingIndicators(ProductCreateInDto dto) {
        List<String> indicators = new ArrayList<>();

        if (Boolean.TRUE.equals(dto.getPcFlag1())) indicators.add("PC_FLAG1");
        if (Boolean.TRUE.equals(dto.getPcFlag2())) indicators.add("PC_FLAG2");
        if (Boolean.TRUE.equals(dto.getPcFlag3())) indicators.add("PC_FLAG3");
        if (Boolean.TRUE.equals(dto.getPcFlag4())) indicators.add("PC_FLAG4");
        if (Boolean.TRUE.equals(dto.getPcFlag5())) indicators.add("PC_FLAG5");
        if (Boolean.TRUE.equals(dto.getPcFlag6())) indicators.add("PC_FLAG6");
        if (Boolean.TRUE.equals(dto.getPcFlag7())) indicators.add("PC_FLAG7");
        if (Boolean.TRUE.equals(dto.getPcFlag8())) indicators.add("PC_FLAG8");
        if (Boolean.TRUE.equals(dto.getPcFlag9())) indicators.add("PC_FLAG9");
        if (Boolean.TRUE.equals(dto.getPcFlag10())) indicators.add("PC_FLAG10");
        if (Boolean.TRUE.equals(dto.getUpc())) indicators.add("UPC");

        return indicators.toArray(new String[0]);
    }
}