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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Entity class representing Fee Values in the system.
 * 
 */
@Entity
@Table(name = "TBL_FEE_VALUES", schema = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeValues {

	/**
	 * Unique identifier for the fee value record. Auto-generated using database
	 * identity strategy.
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    /**
	 * Description of the fee. Cannot be null or blank.
	 */
    @Column(name = "DESCRIPTION", length = 50)
    private String description;

    /**
	 * Type of fee - represents the category or classification of the fee. Cannot be
	 * null or blank.
	 */
    @Column(name = "FEE_TYPE", length = 50)
    private String feeType;

    /**
	 * Fee amount in decimal format. Must be a positive value (>= 0).
	 */
    @Column(name = "FEE_VALUE")
    private BigDecimal feeValue;

    /**
	 * Username or identifier of the user who created this record.
	 */
    @Column(name = "CREATED_BY", length = 255)
    private String createdBy;

    /**
	 * Timestamp when the record was created. Automatically set on insert.
	 */
    @CreationTimestamp
    @Column(name = "CREATED_DATETIME")
    private LocalDateTime createdDatetime;

    /**
	 * Username or identifier of the user who last updated this record.
	 */
    @Column(name = "UPDATED_BY", length = 255)
    private String updatedBy;

    /**
	 * Timestamp when the record was last updated. Automatically updated on
	 * modification.
	 */
    @UpdateTimestamp
    @Column(name = "UPDATED_DATETIME")
    private LocalDateTime updatedDatetime;
}