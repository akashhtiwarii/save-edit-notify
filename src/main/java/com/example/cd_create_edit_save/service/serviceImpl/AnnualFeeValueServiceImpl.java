package com.example.cd_create_edit_save.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.cd_create_edit_save.model.dto.outDto.AnnualFeeValueOutDTO;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.entity.AnnualFeeValue;
import com.example.cd_create_edit_save.repository.AnnualFeeValueRepository;
import com.example.cd_create_edit_save.service.AnnualFeeValueService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link AnnualFeeValueService} interface.
 * 
 * @author Krishna
 * @version 1.0
 * @since 2025-11-10
 */
@Service
@RequiredArgsConstructor
public class AnnualFeeValueServiceImpl implements AnnualFeeValueService {

	private final AnnualFeeValueRepository annualFeeValueRepository;

	/**
	 * Retrieves all annual fee values from the database.
	 * 
	 * @return a list of all annual fee values
	 */
	@Override
	public ApiResponseOutDto<List<AnnualFeeValueOutDTO>> getAllAnnualFeeValues() {

		List<AnnualFeeValue> feeValues = annualFeeValueRepository.findAll();

		List<AnnualFeeValueOutDTO> annualFeeValueOutDTOs = feeValues.stream().map(this::mapToDTO)
				.collect(Collectors.toList());

		return ApiResponseOutDto.<List<AnnualFeeValueOutDTO>>builder().status("SUCCESS")
				.message("Principal codes retrieved successfully").data(annualFeeValueOutDTOs).timestamp(Instant.now())
				.build();
	}

	/**
	 * Maps an {@link AnnualFeeValue} entity to an {@link AnnualFeeValueOutDTO}.
	 *
	 * @param entity the {@link AnnualFeeValue} entity to convert
	 * @return a corresponding {@link AnnualFeeValueOutDTO} instance
	 */
	private AnnualFeeValueOutDTO mapToDTO(AnnualFeeValue entity) {
		return AnnualFeeValueOutDTO.builder().flagName(entity.getFlagName()).amount(entity.getAmount()).build();
	}
}
