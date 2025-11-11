package com.example.cd_create_edit_save.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cd_create_edit_save.enums.FeeType;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.FeeValueOutDTO;
import com.example.cd_create_edit_save.model.entity.FeeValue;
import com.example.cd_create_edit_save.repository.FeeValueRepository;
import com.example.cd_create_edit_save.service.FeeValueService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link FeeValueService} interface.
 * 
 * @author Krishna
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeeValueServiceImpl implements FeeValueService {

	private final FeeValueRepository monthlyFeeValueRepository;

	/**
	 * {@inheritDoc}
	 * 
	 * @param feeType the type of fee to filter by
	 * @return a list of fee values matching the specified fee type
	 */
	@Override
	public ApiResponseOutDto<List<FeeValueOutDTO>> getFeeValuesByType(FeeType feeType) {

		List<FeeValue> feeValues = monthlyFeeValueRepository.findByFeeType(feeType);

		List<FeeValueOutDTO> monthlyFeeValueOutDTOs = feeValues.stream().map(this::mapToDTO)
				.collect(Collectors.toList());
		return ApiResponseOutDto.<List<FeeValueOutDTO>>builder().status("SUCCESS")
				.message("Fee vlaue type retrieved successfully.").data(monthlyFeeValueOutDTOs).timestamp(Instant.now())
				.build();
	}

	/**
	 * Maps an {@link FeeValue} entity to an {@link FeeValueOutDTO}.
	 *
	 * @param entity the {@link FeeValue} entity to convert
	 * @return a corresponding {@link FeeValueOutDTO} instance
	 */
	private FeeValueOutDTO mapToDTO(FeeValue entity) {
		return FeeValueOutDTO.builder().flagName(entity.getFlagName()).amount(entity.getAmount())
				.feeType(entity.getFeeType()).build();
	}

}
