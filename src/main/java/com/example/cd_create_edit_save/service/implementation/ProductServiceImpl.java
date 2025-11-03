package com.example.cd_create_edit_save.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.in.ProductInDTO;
import com.example.cd_create_edit_save.model.entity.Product;
import com.example.cd_create_edit_save.repository.ProductRepository;
import com.example.cd_create_edit_save.service.ProductService;
import com.example.cd_create_edit_save.utils.ProductIdGeneratorUtil;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Implementation of ProductService interface. Handles business logic for
 * product operations including creation and validation.
 * 
 * @author Krishna
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	private final ProductIdGeneratorUtil productGenerator;

	/**
	 * Creates a new product in the database. Validates that the product ID is
	 * unique before creation. Sets creation timestamp and converts DTO to entity.
	 *
	 * @param productInDTO The product data to create
	 * @return ApiResponseOutDto containing the created product details
	 * @throws DuplicateResourceException if product ID already exists
	 */
	@Override
	@Transactional
	public ApiResponseOutDto<Void> createProduct(ProductInDTO productInDTO) {

		Product product = convertToEntity(productInDTO);
		final String productId = productGenerator.generateProductId(productInDTO.getProductShtCd(),
				productInDTO.getFeeTypeShtCd(), productInDTO.getRewardsTypeShtCd());

		product.setProductId(productId);
		product.setCreatedDatetime(LocalDateTime.now());

		Product savedProduct = productRepository.save(product);

		return ApiResponseOutDto.<Void>builder().status("SUCCESS")
				.message("Product created successfully with ID: " + savedProduct.getProductId())
				.timestamp(Instant.now()).build();
	}

	/**
     * Converts ProductInDTO to Product entity.
     *
     * @param dto The ProductInDTO to convert
     * @return Product entity with data from DTO
     */
    private Product convertToEntity(ProductInDTO dto) {
        return Product.builder()
                
                .productShtCd(dto.getProductShtCd())
                .feeTypeShtCd(dto.getFeeTypeShtCd())
                .rewardsTypeShtCd(dto.getRewardsTypeShtCd())
                .aprType(dto.getAprType().toString())
                .creditLineMin(dto.getCreditLineMin())
                .creditLineMax(dto.getCreditLineMax())
                .securityDepositIndicator(dto.getSecurityDepositIndicator())
                .depositMin(dto.getDepositMin())
                .depositMax(dto.getDepositMax())
                .termsConditions(dto.getTermsConditions())
    			.purchaseAprMin(dto.getPurchaseAprMin())
    			.purchaseAprMax(dto.getPurchaseAprMax())
    			.cashAprMin(dto.getCashAprMin())
    			.cashAprMax(dto.getCashAprMax())
                .cardholderAgreement(dto.getCardholderAgreement())
                .cardImage(dto.getCardImage())
                .status(dto.getStatus())
                .createdBy(dto.getCreatedBy())
                .createdDatetime(dto.getCreatedDatetime())
                .reviewedBy(dto.getReviewedBy())
                .reviewedDatetime(dto.getReviewedDatetime())
                .overrideBy(dto.getOverrideBy())
                .overrideDatetime(dto.getOverrideDatetime())
                .overrideJustification(dto.getOverrideJustification())
                .reviewComments(dto.getReviewComments())
                .prin(dto.getPrin())
                .cwsProductId(dto.getCwsProductId())
                .chaCode(dto.getChaCode())
                .boardingIndicator(dto.getBoardingIndicator())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }

}