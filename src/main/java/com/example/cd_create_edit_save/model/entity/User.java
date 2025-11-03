package com.example.cd_create_edit_save.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * Represents a user entity mapped to the database table {@code TBL_USER}.
 * 
 * @author Krishna
 * @version 1.0
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
     * Role assigned to the user (e.g., ADMIN, USER).
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
    private String status = "INACTIVE";

    /**
     * Timestamp when the record was created.
     */
    @Builder.Default
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Timestamp when the record was last updated.
     */
    @Builder.Default
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt = LocalDateTime.now();

    /**
     * Automatically updates the {@code updatedAt} field
     * whenever the entity is modified.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}