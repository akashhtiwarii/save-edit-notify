package com.example.cd_create_edit_save.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOutDto {

    String ProductName;
    String ProductShtCD;
    String fee_type;
    String apr ; // range or specific
    String status;
    LocalDateTime startDate;
    LocalDateTime endDate;




}
