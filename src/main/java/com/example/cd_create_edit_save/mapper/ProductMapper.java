package com.example.cd_create_edit_save.mapper;

import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.model.entity.Product;

public class ProductMapper {

    /**
     * Convert Product entity to ProductOutDto
     *
     * @param product the product entity
     * @return ProductOutDto
     */
    public ProductOutDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        return ProductOutDto.builder()
                .productId(product.getProductId())
                .productShtCd(product.getProductShtCd())
                .feeTypeShtCd(product.getFeeTypeShtCd())
                .rewardsTypeShtCd(product.getRewardsTypeShtCd())
                .aprType(product.getAprType())
//                .purchaseAprType(product.getPurchaseAprType())
//                .purchaseAprMin(product.getPurchaseAprMin())
//                .purchaseAprMax(product.getPurchaseAprMax())
//                .cashAprType(product.getCashAprType())
//                .cashAprMin(product.getCashAprMin())
//                .cashAprMax(product.getCashAprMax())
//                .creditLineMin(product.getCreditLineMin())
//                .creditLineMax(product.getCreditLineMax())
//                .securityDepositValue(product.getSecurityDepositValue())
//                .securityDepositIndicator(product.getSecurityDepositIndicator())
//                .depositMin(product.getDepositMin())
//                .depositMax(product.getDepositMax())
//                .termsConditions(product.getTermsConditions())
//                .cardholderAgreement(product.getCardholderAgreement())
//                .cardImage(product.getCardImage())
                .status(product.getStatus())
                .createdBy(product.getCreatedBy())
                .createdDatetime(product.getCreatedDatetime())
                .reviewedBy(product.getReviewedBy())
                .reviewedDatetime(product.getReviewedDatetime())
                .overrideBy(product.getOverrideBy())
                .overrideDatetime(product.getOverrideDatetime())
                .overrideJustification(product.getOverrideJustification())
                .reviewComments(product.getReviewComments())
                .prin(product.getPrin())
                .cwsProductId(product.getCwsProductId())
                .chaCode(product.getChaCode())
//                .boardingIndicator(product.getBoardingIndicator())
                .startDate(product.getStartDate())
                .endDate(product.getEndDate())
                .build();
    }
}
