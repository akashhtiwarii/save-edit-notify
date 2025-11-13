package com.example.cd_create_edit_save.controller;

import com.example.cd_create_edit_save.constants.AppConstants;
import com.example.cd_create_edit_save.model.dto.outDto.ApiResponseOutDto;
import com.example.cd_create_edit_save.service.ReferenceDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(AppConstants.PRODUCT_REFERENCE_API_BASE_PATH)
@RequiredArgsConstructor
@Slf4j
public class ReferenceDataController {

    private final ReferenceDataService referenceDataService;

    /**
     * Fetch all available date change reasons.
     */
    @GetMapping(AppConstants.DATE_CHANGE_REASONS)
    public ResponseEntity<ApiResponseOutDto<List<Map<String, String>>>> getDateChangeReasons() {
        log.info("Received request to fetch date change reasons");

        List<Map<String, String>> reasons = referenceDataService.getDateChangeReasons();

        ApiResponseOutDto<List<Map<String, String>>> response = ApiResponseOutDto.<List<Map<String, String>>>builder()
                .status("SUCCESS")
                .message("Date change reasons fetched successfully.")
                .data(reasons)
                .timestamp(Instant.now())
                .build();

        log.info("Returning {} date change reasons", reasons.size());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}