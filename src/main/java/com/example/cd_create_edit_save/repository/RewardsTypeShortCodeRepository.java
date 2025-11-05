package com.example.cd_create_edit_save.repository;

import com.example.cd_create_edit_save.model.entity.RewardsTypeShortCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardsTypeShortCodeRepository extends JpaRepository<RewardsTypeShortCode, String> {

	/**
	 * Checks whether a rewards type short code exists in the database.
	 *
	 * @param rewardsTypeShortCode the rewards type short code to check
	 * @return true if the code exists, false otherwise
	 */
	boolean existsByRewardsTypeShortCode(String rewardsTypeShortCode);
}
