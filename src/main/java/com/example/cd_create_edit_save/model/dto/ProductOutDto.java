package com.example.cd_create_edit_save.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOutDto {
    String productName;
    String shortCode;
    String fee_type;
    Double min_apr;
    Double max_apr;// range or specific
    String  status;
    LocalDateTime startDate;
    LocalDateTime endDate;



    //============================================
    //Converters
    //============================================
    public static ProductOutDto fromEntity(Object[] obj) {
        return ProductOutDto.builder()
                .shortCode((String) obj[0])
                .productName((String) obj[1])
                .fee_type((String) obj[2])
                .status((String) obj[3])
                .startDate(obj[4] != null ? ((Timestamp) obj[4]).toLocalDateTime() : null)
                .endDate(obj[5] != null ? ((Timestamp) obj[5]).toLocalDateTime() : null)
                .min_apr(obj[6] != null ? ((Number) obj[6]).doubleValue() : null)
                .max_apr(obj[7] != null ? ((Number) obj[7]).doubleValue() : null)
                .build();
    }



}
