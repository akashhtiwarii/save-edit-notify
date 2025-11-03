package com.example.cd_create_edit_save.validator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.example.cd_create_edit_save.exception.InvalidRequestException;
import com.example.cd_create_edit_save.model.dto.in.ProductInDTO;

@Component
public class ProductValidatorService {

	public void validDate(ProductInDTO dto) {

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
