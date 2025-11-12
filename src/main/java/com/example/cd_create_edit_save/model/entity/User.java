package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a user entity mapped to the database table {@code TBL_USER}.
 */
@Entity
@Table(name = "TBL_USER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Primary key of the user table. Auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer userId;

    /**
     * First name of the user.
     */
    @Column(name = "FIRST_NAME", nullable = false, length = 100)
    private String firstName;

    /**
     * Last name of the user.
     */
    @Column(name = "LAST_NAME", nullable = false, length = 100)
    private String lastName;

    /**
     * Unique username used for login.
     */
    @Column(name = "USERNAME", nullable = false, unique = true, length = 100)
    private String username;

    /**
     * Unique email address of the user.
     */
    @Column(name = "EMAIL", nullable = false, unique = true, length = 255)
    private String email;

    /**
     * Role assigned to the user (e.g., ADMIN, MANAGER, EDITOR).
     */
    @Column(name = "ROLE", nullable = false, length = 50)
    private String role;

    /**
     * Encrypted password for the user account.
     */
    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;

    /**
     * Status of the user account (default: ACTIVE).
     */
    @Builder.Default
    @Column(name = "STATUS", length = 50)
    private String status = "ACTIVE";

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

    /**
     * Automatically updates {@code updatedDatetime} before entity update.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedDatetime = LocalDateTime.now();
    }
}
