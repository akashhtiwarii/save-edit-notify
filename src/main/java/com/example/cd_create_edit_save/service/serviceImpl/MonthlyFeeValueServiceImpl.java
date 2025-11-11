package com.example.cd_create_edit_save.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.MonthlyFeeValueOutDTO;
import com.example.cd_create_edit_save.model.entity.MonthlyFeeValue;
import com.example.cd_create_edit_save.repository.MonthlyFeeValueRepository;
import com.example.cd_create_edit_save.service.MonthlyFeeValueService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link MonthlyFeeValueService} interface.
 * 
 * @author Krishna
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MonthlyFeeValueServiceImpl implements MonthlyFeeValueService {

	private final MonthlyFeeValueRepository monthlyFeeValueRepository;

	/**
	 * Retrieves all monthly fee values from the database.
	 * 
	 * @return a list of all monthly fee values
	 */
	@Override
	@Transactional(readOnly = true)
	public ApiResponseOutDto<List<MonthlyFeeValueOutDTO>> getAllMonthlyFeeValues() {

		List<MonthlyFeeValue> feeValues = monthlyFeeValueRepository.findAll();
		List<MonthlyFeeValueOutDTO> monthlyFeeValueOutDTOs = feeValues.stream().map(this::mapToDTO)
				.collect(Collectors.toList());
		return ApiResponseOutDto.<List<MonthlyFeeValueOutDTO>>builder().status("SUCCESS")
				.message("Principal codes retrieved successfully").data(monthlyFeeValueOutDTOs).timestamp(Instant.now())
				.build();

	}
	
	
	/**
	 * Maps an {@link MonthlyFeeValue} entity to an {@link MonthlyFeeValueOutDTO}.
	 *
	 * @param entity the {@link MonthlyFeeValue} entity to convert
	 * @return a corresponding {@link MonthlyFeeValueOutDTO} instance
	 */
	private MonthlyFeeValueOutDTO mapToDTO(MonthlyFeeValue entity) {
		return MonthlyFeeValueOutDTO.builder().flagName(entity.getFlagName()).amount(entity.getAmount()).build();
	}

}
