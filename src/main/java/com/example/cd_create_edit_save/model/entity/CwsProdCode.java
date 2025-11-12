package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing the product.TBL_CWS_PROD_CODE table.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TBL_CWS_PROD_CODE", schema = "product")
public class CwsProdCode {

	/**
	 * The unique product code identifier used in the CWS.
	 */
	@Id
	@Column(name = "CWS_PROD_CODE", length = 50, nullable = false)
	private String cwsProdCode;

	/**
	 * The description of the CWS product code.
	 */
	@Column(name = "DESCRIPTION", length = 255)
	private String description;

	/**
	 * The current status of the CWS product code.
	 */
	@Column(name = "STATUS", length = 50)
	private String status;

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
