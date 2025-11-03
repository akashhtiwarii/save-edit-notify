package com.example.cd_create_edit_save.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing the TBL_CHA_CODE table.
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TBL_CHA_CODE")
public class ChaCode {

	/**
	 * The unique channel code identifier.
	 */
	@Id
	private String chaCode;

	/**
	 * The description of the channel code.
	 */
	private String description;

	/**
	 * The current status of the channel code (e.g., ACTIVE, INACTIVE).
	 */
	private String status;
}
