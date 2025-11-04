package com.example.cd_create_edit_save.model.dto;


import lombok.Data;

@Data

public class ProductRequestInDto {

    String text ;

    Double min_apr ;

    Double max_apr ;

    String status ;

    Long offset ;

    Long limit ;



}
