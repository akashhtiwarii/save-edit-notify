package com.example.cd_create_edit_save.mapper;

import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.model.entity.Product;
import org.springframework.stereotype.Component;

@Component
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
                .aprValueType(product.getAprValueType())
                .purchaseAprMin(product.getPurchaseAprMin())
                .purchaseAprMax(product.getPurchaseAprMax())
                .cashAprMin(product.getCashAprMin())
                .cashAprMax(product.getCashAprMax())
                .creditLineMin(product.getCreditLineMin())
                .creditLineMax(product.getCreditLineMax())
                .securityDepositIndicator(product.getSecurityDepositIndicator())
                .securityDepositMin(product.getSecurityDepositMin())
                .securityDepositMax(product.getSecurityDepositMax())
                .termsConditionsLink(product.getTermsConditionsLink())
                .cardholderAgreementLink(product.getCardholderAgreementLink())
                .cardImageLink(product.getCardImageLink())
                .status(product.getStatus())
                .createdBy(product.getCreatedBy())
                .createdDatetime(product.getCreatedDatetime())
                .reviewedBy(product.getReviewedBy())
                .reviewedDatetime(product.getReviewedDatetime())
                .reviewComments(product.getReviewComments())
                .overrideBy(product.getOverrideBy())
                .overrideDatetime(product.getOverrideDatetime())
                .overrideJustification(product.getOverrideJustification())
                .prin(product.getPrin())
                .cwsProductId(product.getCwsProductId())
                .chaCode(product.getChaCode())
                .boardingIndicator(product.getBoardingIndicator())
                .startDate(product.getStartDate())
                .endDate(product.getEndDate())
                .build();
    }
}
