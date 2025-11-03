package com.example.cd_create_edit_save.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.PrinCodeOutDTO;
import com.example.cd_create_edit_save.model.entity.PrinCode;
import com.example.cd_create_edit_save.repository.PrinCodeRepository;
import com.example.cd_create_edit_save.service.PrinCodeService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of PrinCodeService interface. Handles business logic for
 * principal code operations.
 *
 * @author Krishna
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PrinCodeServiceImpl implements PrinCodeService {

	private final PrinCodeRepository repository;

	/**
	 * Retrieves all principal codes.
	 *
	 * @return list of all principal codes
	 */
	@Override
	@Transactional(readOnly = true)
	public ApiResponseOutDto<List<PrinCodeOutDTO>> getAllPrinCodes() {
		log.info("Fetching all principal codes");

		List<PrinCode> entities = repository.findAll();

		List<PrinCodeOutDTO> prinCodeDTOs = entities.stream().map(this::mapToDTO).collect(Collectors.toList());

		return ApiResponseOutDto.<List<PrinCodeOutDTO>>builder().status("SUCCESS")
				.message("Principal codes retrieved successfully").data(prinCodeDTOs).timestamp(Instant.now()).build();
	}

	/**
	 * Converts PrinCode entity to DTO.
	 *
	 * @param entity the entity to convert
	 * @return the converted DTO
	 */
	private PrinCodeOutDTO mapToDTO(PrinCode entity) {
		return PrinCodeOutDTO.builder().prinCode(entity.getPrinCode()).description(entity.getDescription())
				.status(entity.getStatus()).build();
	}

}