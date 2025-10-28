package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_PRODUCT_SHT_CODE")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductShortCode {

    @Id
    @Column(name = "PRD_SHT_CD", length = 3, nullable = false)
    private String productShortCode;

    @Column(name = "PRODUCT", length = 50, nullable = false)
    private String product;
}
