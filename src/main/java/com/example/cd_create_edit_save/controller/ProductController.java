package com.example.cd_create_edit_save.controller;


import com.example.cd_create_edit_save.model.dto.ApiResponseOutDto;
import com.example.cd_create_edit_save.model.dto.ProductOutDto;
import com.example.cd_create_edit_save.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    @Autowired
    private ProductService productService;

    @GetMapping("getProducts")
    public ResponseEntity<ApiResponseOutDto<List<ProductOutDto>>> getProducts(@RequestParam(required = false) Long offset , @RequestParam(required = false) Long limit) {
        ApiResponseOutDto<List<ProductOutDto>> products = productService.getProducts(offset, limit);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping("getProductsByParameters")
    public ResponseEntity<ApiResponseOutDto<Map<String , Object>>> getProductByParameters(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Double min_apr,
            @RequestParam(required = false) Double max_apr,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long offset ,
            @RequestParam(required = false) Long limit
    ) {
        ApiResponseOutDto<Map<String , Object>> products = productService.getProductByParameters(text, min_apr, max_apr, status ,offset,limit);
        return new ResponseEntity<>(products, HttpStatus.OK);

    }


    @GetMapping("export")
    public ResponseEntity<InputStreamResource> exportProductsToCsv() {
        InputStreamResource file = new InputStreamResource(productService.exportProductsToCsv());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }

}
