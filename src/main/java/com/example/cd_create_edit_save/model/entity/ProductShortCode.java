package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_PRODUCT_SHT_CODE", schema = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductShortCode {

    /**
     * The short code for the product.
     */
    @Id
    @Column(name = "PRD_SHT_CD", length = 3, nullable = false)
    private String productShortCode;

    /**
     * The product name or description.
     */
    @Column(name = "PRODUCT", length = 50, nullable = false)
    private String product;

    /**
     * The user who created this record.
     */
    @Column(name = "CREATED_BY", length = 255)
    private String createdBy;

    /**
     * The timestamp when the record was created.
     */
    @Column(name = "CREATED_DATETIME")
    private LocalDateTime createdDatetime;

    /**
     * The user who last updated this record.
     */
    @Column(name = "UPDATED_BY", length = 255)
    private String updatedBy;

    /**
     * The timestamp when the record was last updated.
     */
    @Column(name = "UPDATED_DATETIME")
    private LocalDateTime updatedDatetime;
}
