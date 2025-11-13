package com.example.cd_create_edit_save.service.serviceImpl;

import com.example.cd_create_edit_save.repository.FeeValuesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.FeeValueOutDTO;
import com.example.cd_create_edit_save.model.entity.FeeValues;
import com.example.cd_create_edit_save.service.FeeValuesService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link FeeValuesService} interface.
 * 
 * @author Krishna
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class FeeValuesServiceImpl implements FeeValuesService {

	private final FeeValuesRepository monthlyFeeValueRepository;

	/**
	 * Retrieves all fee values filtered by fee type.
	 * 
	 * @param feeType the type of fee (ANNUAL or MONTHLY)
	 * @return a list of fee values matching the specified fee type
	 */
	@Transactional(readOnly = true)
	@Override
	public ApiResponseOutDto<List<FeeValueOutDTO>> getFeeValuesByType(String feeType) {

		List<FeeValues> feeValues = monthlyFeeValueRepository.findByFeeType(feeType);

		List<FeeValueOutDTO> monthlyFeeValueOutDTOs = feeValues.stream().map(this::mapToDTO)
				.collect(Collectors.toList());
		return ApiResponseOutDto.<List<FeeValueOutDTO>>builder().status("SUCCESS")
				.message("Fee value type retrieved successfully.").data(monthlyFeeValueOutDTOs).timestamp(Instant.now())
				.build();
	}

	/**
	 * Maps an {@link FeeValues} entity to an {@link FeeValueOutDTO}.
	 *
	 * @param entity the {@link FeeValues} entity to convert
	 * @return a corresponding {@link FeeValueOutDTO} instance
	 */
	private FeeValueOutDTO mapToDTO(FeeValues entity) {
		return FeeValueOutDTO.builder().description(entity.getDescription()).feeValue(entity.getFeeValue())
				.feeType(entity.getFeeType()).build();
	}

}
