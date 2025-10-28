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

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_AUDIT")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUDIT_ID")
    private Integer auditId;

    @Column(name = "USERNAME", length = 255)
    private String username;

    @Column(name = "ACTION", length = 255)
    private String action;

    @Column(name = "UPDATED_TIME")
    private LocalDateTime updatedTime;

    @Column(name = "NOTE", length = 500)
    private String note;
}
