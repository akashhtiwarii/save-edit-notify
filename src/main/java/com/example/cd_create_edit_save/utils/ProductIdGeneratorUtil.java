package com.example.cd_create_edit_save.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.example.cd_create_edit_save.repository.ProductRepository;

/**
 * Utility class for generating Product IDs. Generates unique product IDs based
 * on product short code, feature 1 short code, feature 2 short code, and a
 * 7-digit version key.
 *
 * @author Krishna
 * @version 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ProductIdGeneratorUtil {

	private final ProductRepository productRepository;

	public String generateProductId(String productShortCode, String featureShortCode, String rewardShortCode) {

		String prefix = String.format("%s-%s-%s", productShortCode, featureShortCode, rewardShortCode);
		long existingCount = productRepository.countByFinalProductIdPrefix(prefix);

		long nextSequence = existingCount + 1;
		String formattedSequence = String.format("%07d", nextSequence);

		return String.format("%s-%s", prefix, formattedSequence);
	}

//	/**
//	 * Generates version key from the sequence number
//	 * 
//	 * @param sequenceNumber The 7-digit sequence number
//	 * @return Version key (e.g., 0000001)
//	 */
//	public String generateVersionKey(long sequenceNumber) {
//		return String.format("%07d", sequenceNumber);
//	}
//
//	/**
//	 * Extracts the sequence number from a product ID
//	 * 
//	 * @param productId The full product ID (e.g., LEG-AF-PR-0000001)
//	 * @return The sequence number as long
//	 */
//	public long extractSequenceNumber(String productId) {
//		if (productId == null || productId.isEmpty()) {
//			throw new IllegalArgumentException("Product ID cannot be null or empty");
//		}
//
//		String[] parts = productId.split("-");
//		if (parts.length != 4) {
//			throw new IllegalArgumentException("Invalid product ID format: " + productId);
//		}
//
//		return Long.parseLong(parts[3]);
//	}

}