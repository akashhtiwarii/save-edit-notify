package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing the product.TBL_CHA_CODE table.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TBL_CHA_CODE", schema = "product")
public class ChaCode {

	/**
	 * The unique channel code identifier.
	 */
	@Id
	@Column(name = "CHA_CODE", length = 50, nullable = false)
	private String chaCode;

	/**
	 * The description of the channel code.
	 */
	@Column(name = "DESCRIPTION", length = 255)
	private String description;

	/**
	 * The current status of the channel code.
	 */
	@Column(name = "STATUS", length = 50)
	private String status;

	/**
	 * The user who created the record.
	 */
	@Column(name = "CREATED_BY", length = 255)
	private String createdBy;

	/**
	 * The timestamp when the record was created.
	 */
	@Column(name = "CREATED_DATETIME")
	private LocalDateTime createdDatetime;

	/**
	 * The user who last updated the record.
	 */
	@Column(name = "UPDATED_BY", length = 255)
	private String updatedBy;

	/**
	 * The timestamp when the record was last updated.
	 */
	@Column(name = "UPDATED_DATETIME")
	private LocalDateTime updatedDatetime;
}
