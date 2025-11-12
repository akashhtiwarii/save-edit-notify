package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_FEE_TYPE_SHT_CODE", schema = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeTypeShortCode {

    /**
     * The short code representing the fee type.
     */
    @Id
    @Column(name = "FEE_TYPE_SHT_CD", length = 2, nullable = false)
    private String feeTypeShortCode;

    /**
     * The full name or description of the fee type.
     */
    @Column(name = "FEE_TYPE", length = 50, nullable = false)
    private String feeType;

    /**
     * The user who created this record.
     */
    @Column(name = "CREATED_BY", length = 255)
    private String createdBy;

    /**
     * The timestamp when this record was created.
     */
    @Column(name = "CREATED_DATETIME")
    private LocalDateTime createdDatetime;

    /**
     * The user who last updated this record.
     */
    @Column(name = "UPDATED_BY", length = 255)
    private String updatedBy;

    /**
     * The timestamp when this record was last updated.
     */
    @Column(name = "UPDATED_DATETIME")
    private LocalDateTime updatedDatetime;
}
