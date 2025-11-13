package com.example.cd_create_edit_save.mapper;

import com.example.cd_create_edit_save.model.dto.inDto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.inDto.ProductDateUpdateInDTO;
import com.example.cd_create_edit_save.model.dto.inDto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.model.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ProductMapper {


    public Product toEntity(ProductCreateInDto dto, String generatedProductId, String createdBy) {
        String boardingIndicator = buildBoardingIndicator(
                dto.getPcFlag1(), dto.getPcFlag2(), dto.getPcFlag3(), dto.getPcFlag4(), dto.getPcFlag5(),
                dto.getPcFlag6(), dto.getPcFlag7(), dto.getPcFlag8(), dto.getPcFlag9(), dto.getPcFlag10(),
                dto.getUpc()
        );

        return Product.builder()
                .productId(generatedProductId)
                .productShtCd(dto.getProductShtCd())
                .feeTypeShtCd(dto.getFeeTypeShtCd())
                .rewardsTypeShtCd(dto.getRewardsTypeShtCd())
                .aprType(dto.getAprType())
                .aprValueType(dto.getAprValueType())
                .purchaseAprMin(dto.getPurchaseAprMin())
                .purchaseAprMax(dto.getPurchaseAprMax())
                .cashAprMin(dto.getCashAprMin())
                .cashAprMax(dto.getCashAprMax())
                .creditLineMin(dto.getCreditLineMin())
                .creditLineMax(dto.getCreditLineMax())
                .securityDepositIndicator(dto.getSecurityDepositIndicator())
                .securityDepositMin(dto.getSecurityDepositMin())
                .securityDepositMax(dto.getSecurityDepositMax())
                .termsConditionsLink(dto.getTermsConditionsLink())
                .cardholderAgreementLink(dto.getCardholderAgreementLink())
                .cardImageLink(dto.getCardImageLink())
                .status("PENDING")
                .createdBy(createdBy)
                .createdDatetime(LocalDateTime.now())
                .updatedBy(createdBy)
                .updatedDatetime(LocalDateTime.now())
                .requestType("CREATE")
                .toBeApprovedBy(dto.getToBeApprovedBy())
                .feeValue(dto.getFeeValue())
                .commentsToApprover(dto.getCommentsToApprover())
                .reviewedBy(null)
                .reviewedDatetime(null)
                .reviewComments(null)
                .prin(dto.getPrin())
                .cwsProductId(dto.getCwsProductId())
                .chaCode(dto.getChaCode())
                .boardingIndicator(boardingIndicator)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }

    public Product toEntity(ProductUpdateInDto dto, String generatedProductId, String updatedBy) {
        String boardingIndicator = buildBoardingIndicator(
                dto.getPcFlag1(), dto.getPcFlag2(), dto.getPcFlag3(), dto.getPcFlag4(), dto.getPcFlag5(),
                dto.getPcFlag6(), dto.getPcFlag7(), dto.getPcFlag8(), dto.getPcFlag9(), dto.getPcFlag10(),
                dto.getUpc()
        );

        return Product.builder()
                .productId(generatedProductId)
                .productShtCd(dto.getProductShtCd())
                .feeTypeShtCd(dto.getFeeTypeShtCd())
                .rewardsTypeShtCd(dto.getRewardsTypeShtCd())
                .aprType(dto.getAprType())
                .aprValueType(dto.getAprValueType())
                .purchaseAprMin(dto.getPurchaseAprMin())
                .purchaseAprMax(dto.getPurchaseAprMax())
                .cashAprMin(dto.getCashAprMin())
                .cashAprMax(dto.getCashAprMax())
                .creditLineMin(dto.getCreditLineMin())
                .creditLineMax(dto.getCreditLineMax())
                .securityDepositIndicator(dto.getSecurityDepositIndicator())
                .securityDepositMin(dto.getSecurityDepositMin())
                .securityDepositMax(dto.getSecurityDepositMax())
                .termsConditionsLink(dto.getTermsConditionsLink())
                .cardholderAgreementLink(dto.getCardholderAgreementLink())
                .cardImageLink(dto.getCardImageLink())
                .status("REVISION_PENDING")
                .createdBy(updatedBy)
                .createdDatetime(LocalDateTime.now())
                .updatedBy(updatedBy)
                .updatedDatetime(LocalDateTime.now())
                .requestType("UPDATE")
                .toBeApprovedBy(dto.getToBeApprovedBy())
                .feeValue(dto.getFeeValue())
                .commentsToApprover(dto.getCommentsToApprover())
                .reviewedBy(null)
                .reviewedDatetime(null)
                .reviewComments(null)
                .prin(dto.getPrin())
                .cwsProductId(dto.getCwsProductId())
                .chaCode(dto.getChaCode())
                .boardingIndicator(boardingIndicator)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }

    /**
     * Convert Product entity to ProductCreateOutDto
     *
     * @param product the product entity
     * @return ProductCreateOutDto
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

                .feeValue(product.getFeeValue())

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

                .updatedBy(product.getUpdatedBy())
                .updatedDatetime(product.getUpdatedDatetime())

                .requestType(product.getRequestType())
                .toBeApprovedBy(product.getToBeApprovedBy())
                .commentsToApprover(product.getCommentsToApprover())

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

    /**
     * Maps updated date fields and metadata into the product entity.
     * Dates are already parsed in the service layer.
     *
     * @param product   existing product entity
     * @param dto       input date update DTO
     * @param newStart  new start datetime (already parsed)
     * @param newEnd    new end datetime (already parsed)
     * @param updatedBy user performing the update
     * @return updated product entity
     */
    public Product mapDateChangeFields(Product product,
                                       ProductDateUpdateInDTO dto,
                                       LocalDateTime newStart,
                                       LocalDateTime newEnd,
                                       String updatedBy) {
        log.debug("Mapping date change fields for Product ID: {}", product.getProductId());

        product.setStartDate(newStart);
        product.setEndDate(newEnd);
        log.debug("Set new start date: {} and end date: {}", newStart, newEnd);

        product.setToBeApprovedBy(dto.getApprover());
        product.setRequestType("DATE_CHANGE");
        log.debug("Set approver: '{}' and request type: DATE_CHANGE", dto.getApprover());

        String comments = dto.getReasonForDateChange();
        if (StringUtils.hasText(dto.getAdditionalNotes())) {
            comments += " | " + dto.getAdditionalNotes();
        }
        product.setCommentsToApprover(comments);
        log.debug("Set commentsToApprover: {}", comments);

        product.setUpdatedBy(updatedBy);
        product.setUpdatedDatetime(LocalDateTime.now());
        log.debug("Set updatedBy: '{}' and updatedDatetime", updatedBy);

        log.info("Date change mapping completed successfully for Product ID: {}", product.getProductId());
        return product;
    }

    private String buildBoardingIndicator(Boolean flag1, Boolean flag2, Boolean flag3, Boolean flag4, Boolean flag5,
                                          Boolean flag6, Boolean flag7, Boolean flag8, Boolean flag9, Boolean flag10,
                                          String upc) {
        List<String> indicators = new ArrayList<>();

        if (Boolean.TRUE.equals(flag1)) indicators.add("PC_FLAG1");
        if (Boolean.TRUE.equals(flag2)) indicators.add("PC_FLAG2");
        if (Boolean.TRUE.equals(flag3)) indicators.add("PC_FLAG3");
        if (Boolean.TRUE.equals(flag4)) indicators.add("PC_FLAG4");
        if (Boolean.TRUE.equals(flag5)) indicators.add("PC_FLAG5");
        if (Boolean.TRUE.equals(flag6)) indicators.add("PC_FLAG6");
        if (Boolean.TRUE.equals(flag7)) indicators.add("PC_FLAG7");
        if (Boolean.TRUE.equals(flag8)) indicators.add("PC_FLAG8");
        if (Boolean.TRUE.equals(flag9)) indicators.add("PC_FLAG9");
        if (Boolean.TRUE.equals(flag10)) indicators.add("PC_FLAG10");

        if (upc != null && !upc.trim().isEmpty()) {
            indicators.add(upc);
        }

        return String.join(",", indicators);
    }
}
