package com.example.cd_create_edit_save.validator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.example.cd_create_edit_save.enums.APRValueType;
import com.example.cd_create_edit_save.exception.InvalidRequestException;
import com.example.cd_create_edit_save.model.dto.in.ProductInDTO;

@Component
public class ProductValidatorService {

	/**
	 * Validates the product input details by performing multiple checks:
	 *
	 * @param dto the {@link ProductInDTO} object containing product input details
	 * @throws InvalidRequestException if any validation rule fails
	 */
	public void validateProduct(ProductInDTO dto) {
		validateApr(dto);
		validateDate(dto);
	}


	public void validateApr(ProductInDTO dto) {

		if (dto.getAprValueType().equals(APRValueType.SPECIFIC)) {

			if (dto.getPurchaseAprMin().compareTo(dto.getPurchaseAprMax()) != 0
					|| dto.getCashAprMin().compareTo(dto.getCashAprMax()) != 0) {
				throw new InvalidRequestException(
						"For Specific APR type, Purchase APR and Cash APR min and max values must be the same.");
			}

			if (dto.getPurchaseAprMin().compareTo(dto.getCashAprMin()) != 0
					|| dto.getPurchaseAprMax().compareTo(dto.getCashAprMax()) != 0) {
				throw new InvalidRequestException(
						"For Specific APR type, Purchase APR and Cash APR values must be identical.");
			}
		}
	}

	public void validateDate(ProductInDTO dto) {

		LocalDateTime start = dto.getStartDate();
		LocalDateTime end = dto.getEndDate();

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime minStart = now.plusWeeks(1);

		if (start.isBefore(minStart)) {
			throw new InvalidRequestException("Start date must be at least one week after today");
		}

		if (!end.isAfter(start)) {
			throw new InvalidRequestException("End date must be after the start date");
		}

		long daysBetween = ChronoUnit.DAYS.between(start, end);
		if (daysBetween < 30) {
			throw new InvalidRequestException("End date must be at least 30 days after the start date");
		}
	}
}
