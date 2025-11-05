package com.example.cd_create_edit_save.model.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSummaryOutDTO {

    /**
     * The descriptive name of the summary types
     */
    private long totalProducts;
    private long activeProducts;
    private long pendingApproval;
    private long expiredProducts;

}
