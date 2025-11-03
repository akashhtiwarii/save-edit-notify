package com.example.cd_create_edit_save.service;

import java.util.List;

import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.ChaCodeOutDTO;

/**
 * Service interface for managing Channel Code operations. Defines business
 * logic operations for channel codes.
 *
 * @author Krishna
 * @version 1.0
 */
public interface ChaCodeService {

    /**
     * Retrieves all channel codes.
     *
     * @return ApiResponseOutDto containing list of all channel codes with status and message
     */
    ApiResponseOutDto<List<ChaCodeOutDTO>> getAllChaCodes();
}