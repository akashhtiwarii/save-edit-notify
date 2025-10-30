package com.example.cd_create_edit_save.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.FeeTypeShortCodeOutDTO;
import com.example.cd_create_edit_save.model.entity.FeeTypeShortCode;
import com.example.cd_create_edit_save.repository.FeeTypeShortCodeRepository;
import com.example.cd_create_edit_save.service.FeeTypeShortCodeService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link FeeTypeShortCodeService} interface. Provides
 * concrete implementation for fee type short code business operations.
 * 
 */
@Service
@RequiredArgsConstructor
public class FeeTypeShortCodeServiceImpl implements FeeTypeShortCodeService {

	private final FeeTypeShortCodeRepository feeTypeShortCodeRepository;

	/**
	 * Retrieves all fee type short codes from the database.
	 * 
	 * @return a list of {@link FeeTypeShortCodeOutDTO} containing all fee type
	 *         short codes. Returns an empty list if no records are found.
	 */
	@Override
	@Transactional(readOnly = true)
	public ApiResponseOutDto<List<FeeTypeShortCodeOutDTO>> getAllFeeTypeShortCodes() {
		List<FeeTypeShortCode> feeTypeShortCodes = feeTypeShortCodeRepository.findAll();

		List<FeeTypeShortCodeOutDTO> feeTypeShortCodeDTOs = feeTypeShortCodes.stream().map(this::convertToDTO)
				.collect(Collectors.toList());

		return ApiResponseOutDto.<List<FeeTypeShortCodeOutDTO>>builder().status("SUCCESS")
				.message("Fee type short codes retrieved successfully").data(feeTypeShortCodeDTOs)
				.timestamp(Instant.now()).build();
	}

	/**
	 * Converts a FeeTypeShortCode entity to FeeTypeShortCodeOutDTO.
	 * 
	 * @param entity the {@link FeeTypeShortCode} entity to convert
	 * @return the converted {@link FeeTypeShortCodeOutDTO}
	 */
	private FeeTypeShortCodeOutDTO convertToDTO(FeeTypeShortCode entity) {
		return FeeTypeShortCodeOutDTO.builder().feeTypeShortCode(entity.getFeeTypeShortCode())
				.feeType(entity.getFeeType()).build();
	}
}