package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_REWARDS_TYPE_SHT_CODE", schema = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RewardsTypeShortCode {

    /**
     * The short code representing the rewards type.
     */
    @Id
    @Column(name = "REWARDS_TYPE_SHT_CD", length = 2, nullable = false)
    private String rewardsTypeShortCode;

    /**
     * The full name or description of the rewards type.
     */
    @Column(name = "REWARDS_TYPE", length = 50, nullable = false)
    private String rewardsType;

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
