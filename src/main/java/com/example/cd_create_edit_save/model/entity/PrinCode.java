package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing the TBL_PRIN_CODE table.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TBL_PRIN_CODE")
public class PrinCode {

	/**
	 * The unique principal code identifier.
	 */
	@Id
	private String prinCode;

	/**
	 * The description associated with the principal code.
	 */
	private String description;

	/**
	 * The current status of the principal code (e.g., ACTIVE, INACTIVE).
	 */
	private String status;
}
