package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_FEE_VALUES", schema = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeValues {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DESCRIPTION", length = 50)
    private String description;

    @Column(name = "FEE_TYPE", length = 50)
    private String feeType;

    @Column(name = "FEE_VALUE")
    private BigDecimal feeValue;

    @Column(name = "CREATED_BY", length = 255)
    private String createdBy;

    @Column(name = "CREATED_DATETIME")
    private LocalDateTime createdDatetime;

    @Column(name = "UPDATED_BY", length = 255)
    private String updatedBy;

    @Column(name = "UPDATED_DATETIME")
    private LocalDateTime updatedDatetime;
}