package com.example.cd_create_edit_save.service.serviceImpl;

import com.example.cd_create_edit_save.enums.DateChangeEnum;
import com.example.cd_create_edit_save.service.ReferenceDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReferenceDataServiceImpl implements ReferenceDataService {

    @Override
    public List<Map<String, String>> getDateChangeReasons() {
        log.info("Fetching list of date change reasons from enum: {}", DateChangeEnum.class.getSimpleName());

        List<Map<String, String>> reasons = Arrays.stream(DateChangeEnum.values())
                .map(reason -> Map.of(
                        "code", reason.name(),
                        "label", reason.getDisplayName()
                ))
                .collect(Collectors.toList());

        log.debug("Fetched {} date change reasons: {}", reasons.size(), reasons);

        return reasons;
    }
}