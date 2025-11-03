package com.example.cd_create_edit_save.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.RewardsTypeShortCodeOutDTO;
import com.example.cd_create_edit_save.model.entity.RewardsTypeShortCode;
import com.example.cd_create_edit_save.repository.RewardsTypeShortCodeRepository;
import com.example.cd_create_edit_save.service.RewardsTypeShortCodeService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link RewardsTypeShortCodeService} interface. Provides
 * concrete implementation for rewards type short code business operations.
 * 
 * <p>
 * This service class handles the business logic for managing rewards type short
 * codes, including data retrieval and entity-to-DTO conversion.
 * </p>
 * 
 * @author Krishna
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RewardsTypeShortCodeServiceImpl implements RewardsTypeShortCodeService {

	private final RewardsTypeShortCodeRepository rewardsTypeShortCodeRepository;

	/**
	 * {@inheritDoc}
	 * 
	 * @return a list of {@link RewardsTypeShortCodeOutDTO} containing all rewards
	 *         type short codes
	 */
	@Override
	@Transactional(readOnly = true)
	public ApiResponseOutDto<List<RewardsTypeShortCodeOutDTO>> getAllRewardsTypeShortCodes() {
		List<RewardsTypeShortCode> rewardsTypeShortCodes = rewardsTypeShortCodeRepository.findAll();
		log.info("Retrieved {} rewards type short codes", rewardsTypeShortCodes.size());

		List<RewardsTypeShortCodeOutDTO> rewardsTypeShortCodeDTOs = rewardsTypeShortCodes.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());

		return ApiResponseOutDto.<List<RewardsTypeShortCodeOutDTO>>builder()
				.status("SUCCESS")
				.message("Rewards type short codes retrieved successfully")
				.data(rewardsTypeShortCodeDTOs)
				.timestamp(Instant.now())
				.build();
	}

	/**
	 * Converts a RewardsTypeShortCode entity to RewardsTypeShortCodeOutDTO.
	 * 
	 * <p>
	 * This is a private utility method used for entity-to-DTO conversion. It maps
	 * all relevant fields from the entity to the DTO.
	 * </p>
	 * 
	 * @param entity the {@link RewardsTypeShortCode} entity to convert
	 * @return the converted {@link RewardsTypeShortCodeOutDTO}
	 */
	private RewardsTypeShortCodeOutDTO convertToDTO(RewardsTypeShortCode entity) {
		return RewardsTypeShortCodeOutDTO.builder().rewardsTypeShortCode(entity.getRewardsTypeShortCode())
				.rewardsType(entity.getRewardsType()).build();
	}
}