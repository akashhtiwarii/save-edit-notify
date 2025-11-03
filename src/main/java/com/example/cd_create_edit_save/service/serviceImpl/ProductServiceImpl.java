package com.example.cd_create_edit_save.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductOutDto;
import com.example.cd_create_edit_save.exception.InvalidRequestException;
import com.example.cd_create_edit_save.exception.ResourceNotFoundException;
import com.example.cd_create_edit_save.mapper.ProductMapper;
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

	private final ProductMapper productMapper;

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
	            .aprValueType(dto.getAprValueType().toString())
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
	            .status(dto.getStatus())
	            .createdBy(dto.getCreatedBy())
	            .createdDatetime(dto.getCreatedDatetime())
	            .reviewedBy(dto.getReviewedBy())
	            .reviewedDatetime(dto.getReviewedDatetime())
	            .reviewComments(dto.getReviewComments())
	            .overrideBy(dto.getOverrideBy())
	            .overrideDatetime(dto.getOverrideDatetime())
	            .overrideJustification(dto.getOverrideJustification())
	            .prin(dto.getPrin())
	            .cwsProductId(dto.getCwsProductId())
	            .chaCode(dto.getChaCode())
	            .boardingIndicator(dto.getBoardingIndicator().toString())
	            .startDate(dto.getStartDate())
	            .endDate(dto.getEndDate())
	            .build();
	}


    

	 @Override
	    @Transactional(readOnly = true)
	    public ProductOutDto getProductById(String productId) {
	        log.info("Fetching product with ID: {}", productId);

	        if (productId == null || productId.trim().isEmpty()) {
	            log.error("Invalid product ID provided: {}", productId);
	            throw new InvalidRequestException("Product ID cannot be null or empty");
	        }

	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> {
	                    log.error("Product not found with ID: {}", productId);
	                    return new ResourceNotFoundException(
	                            String.format("Product not found with ID: %s", productId)
	                    );
	                });

	        log.info("Successfully retrieved product with ID: {}", productId);

	        return productMapper.toDto(product);
	    }

}