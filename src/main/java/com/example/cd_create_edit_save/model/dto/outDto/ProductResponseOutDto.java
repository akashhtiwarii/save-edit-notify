package com.example.cd_create_edit_save.model.dto.outDto;


import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponseOutDto {
    String productName;
    String shortCode;
    String fee_type;
    Double purchase_apr_min;
    Double purchase_apr_max;
    String  status;
    LocalDateTime startDate;
    LocalDateTime endDate;

    //============================================
    //Converters
    //============================================
    public static ProductResponseOutDto fromEntity(Object[] obj) {
        if (obj == null) return null;

        ProductResponseOutDto dto = new ProductResponseOutDto();

        dto.setShortCode(obj[0] != null ? obj[0].toString() : null);
        dto.setProductName(obj[1] != null ? obj[1].toString() : null);
        dto.setFee_type(obj[2] != null ? obj[2].toString() : null);
        dto.setStatus(obj[3] != null ? obj[3].toString() : null);

        if (obj[4] instanceof Timestamp ts)
            dto.setStartDate(ts.toLocalDateTime());
        else if (obj[4] instanceof LocalDateTime ldt)
            dto.setStartDate(ldt);
        else if (obj[4] != null)
            dto.setStartDate(LocalDateTime.parse(obj[4].toString()));

        if (obj[5] instanceof Timestamp ts)
            dto.setEndDate(ts.toLocalDateTime());
        else if (obj[5] instanceof LocalDateTime ldt)
            dto.setEndDate(ldt);
        else if (obj[5] != null)
            dto.setEndDate(LocalDateTime.parse(obj[5].toString()));

        dto.setPurchase_apr_min(obj[6] != null ? ((Number) obj[6]).doubleValue() : null);
        dto.setPurchase_apr_max(obj[7] != null ? ((Number) obj[7]).doubleValue() : null);

        return dto;
    }




}
