package com.example.cd_create_edit_save.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.outDto.ChaCodeOutDTO;
import com.example.cd_create_edit_save.model.entity.ChaCode;
import com.example.cd_create_edit_save.repository.ChaCodeRepository;
import com.example.cd_create_edit_save.service.ChaCodeService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of ChaCodeService interface. Handles business logic for
 * channel code operations.
 *
 * @author Krishna
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChaCodeServiceImpl implements ChaCodeService {

	private final ChaCodeRepository repository;

	/**
	 * Retrieves all channel codes.
	 *
	 * @return ApiResponseOutDto containing list of all channel codes with status
	 *         and message
	 */
	@Override
	public ApiResponseOutDto<List<ChaCodeOutDTO>> getAllChaCodes() {

		List<ChaCode> entities = repository.findAll();

		List<ChaCodeOutDTO> chaCodeDTOs = entities.stream().map(this::mapToDTO).collect(Collectors.toList());

		return ApiResponseOutDto.<List<ChaCodeOutDTO>>builder().status("SUCCESS")
				.message("Channel codes retrieved successfully").data(chaCodeDTOs).timestamp(Instant.now()).build();
	}

	/**
	 * Converts ChaCode entity to DTO.
	 *
	 * @param entity the entity to convert
	 * @return the converted DTO
	 */
	private ChaCodeOutDTO mapToDTO(ChaCode entity) {
		return ChaCodeOutDTO.builder().chaCode(entity.getChaCode()).description(entity.getDescription())
				.status(entity.getStatus()).build();
	}
}