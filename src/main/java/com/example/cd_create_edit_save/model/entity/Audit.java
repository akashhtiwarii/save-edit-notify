package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TBL_AUDIT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Audit {

    /**
     * Primary key of the audit table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUDIT_ID")
    private Integer auditId;

    /**
     * Username of the user who performed the action.
     */
    @Column(name = "USERNAME", length = 255)
    private String username;

    /**
     * Description of the action performed.
     */
    @Column(name = "ACTION", length = 255)
    private String action;

    /**
     * The timestamp when the action occurred.
     */
    @Column(name = "UPDATED_TIME")
    private LocalDateTime updatedTime;

    /**
     * Additional notes related to the action.
     */
    @Column(name = "NOTE", length = 500)
    private String note;

    /**
     * The role of the user who performed the action.
     */
    @Column(name = "ROLE", length = 50)
    private String role;

    /**
     * The module or section of the application where the action took place.
     */
    @Column(name = "MODULE", length = 50)
    private String module;
}
