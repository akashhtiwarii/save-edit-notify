package com.example.cd_create_edit_save.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ProductShortCodeOutDTO;
import com.example.cd_create_edit_save.model.entity.ProductShortCode;
import com.example.cd_create_edit_save.repository.ProductShortCodeRepository;
import com.example.cd_create_edit_save.service.ProductShortCodeService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of ProductShortCodeService interface. Handles business logic
 * for product short code operations.
 * 
 * @author Krishna
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProductShortCodeServiceImpl implements ProductShortCodeService {

	private final ProductShortCodeRepository repository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public ApiResponseOutDto<List<ProductShortCodeOutDTO>> getAllProductShortCodes() {

		List<ProductShortCode> entities = repository.findAll();

		List<ProductShortCodeOutDTO> productShortCodeDTOs = entities.stream().map(this::mapToDTO)
				.collect(Collectors.toList());

		return ApiResponseOutDto.<List<ProductShortCodeOutDTO>>builder().status("SUCCESS")
				.message("Product short codes retrieved successfully").data(productShortCodeDTOs)
				.timestamp(Instant.now()).build();
	}

	/**
	 * Converts ProductShortCode entity to DTO.
	 *
	 * @param entity the entity to convert
	 * @return the converted DTO
	 */
	private ProductShortCodeOutDTO mapToDTO(ProductShortCode entity) {
		return ProductShortCodeOutDTO.builder().productShortCode(entity.getProductShortCode())
				.product(entity.getProduct()).build();
	}

}