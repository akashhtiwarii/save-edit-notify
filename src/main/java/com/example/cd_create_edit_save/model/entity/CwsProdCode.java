package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing the TBL_CWS_PROD_CODE table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TBL_CWS_PROD_CODE")
public class CwsProdCode {

	/**
	 * The unique product code identifier used in the CWS.
	 */
	@Id
	private String cwsProdCode;

	/**
	 * The description of the CWS product code.
	 */
	private String description;

	/**
	 * The current status of the CWS product code.
	 */
	private String status;
}
