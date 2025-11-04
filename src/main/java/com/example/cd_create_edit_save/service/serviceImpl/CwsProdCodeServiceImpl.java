package com.example.cd_create_edit_save.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.CwsProdCodeOutDTO;
import com.example.cd_create_edit_save.model.entity.CwsProdCode;
import com.example.cd_create_edit_save.repository.CwsProdCodeRepository;
import com.example.cd_create_edit_save.service.CwsProdCodeService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of CwsProdCodeService interface.
 * 
 * @author Krishna
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CwsProdCodeServiceImpl implements CwsProdCodeService {

	private final CwsProdCodeRepository cwsProdCodeRepository;

	/**
	 * Retrieves all CWS product codes from the database. Converts entity objects to
	 * DTOs and wraps in ApiResponseOutDto.
	 *
	 * @return ApiResponseOutDto containing list of CwsProdCodeOutDTO with status
	 *         and timestamp
	 */
	@Override
	public ApiResponseOutDto<List<CwsProdCodeOutDTO>> getAllCwsProdCodes() {

		List<CwsProdCode> cwsProdCodes = cwsProdCodeRepository.findAll();

		List<CwsProdCodeOutDTO> cwsProdCodeDTOs = cwsProdCodes.stream().map(this::convertToDTO)
				.collect(Collectors.toList());

		return ApiResponseOutDto.<List<CwsProdCodeOutDTO>>builder().status("SUCCESS")
				.message("CWS product codes retrieved successfully").data(cwsProdCodeDTOs).timestamp(Instant.now())
				.build();
	}

	/**
	 * Converts CwsProdCode entity to CwsProdCodeOutDTO.
	 *
	 * @param entity The CwsProdCode entity to convert
	 * @return CwsProdCodeOutDTO containing the entity data
	 */
	private CwsProdCodeOutDTO convertToDTO(CwsProdCode entity) {
		return CwsProdCodeOutDTO.builder().cwsProdCode(entity.getCwsProdCode()).description(entity.getDescription())
				.status(entity.getStatus()).build();
	}
}