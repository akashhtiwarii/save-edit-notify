package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_FEE_TYPE_SHT_CODE")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FeeTypeShortCode {

    @Id
    @Column(name = "FEE_TYPE_SHT_CD", length = 2, nullable = false)
    private String feeTypeShortCode;

    @Column(name = "FEE_TYPE", length = 50, nullable = false)
    private String feeType;
}