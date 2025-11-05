package com.example.cd_create_edit_save.validator;

import com.example.cd_create_edit_save.model.dto.ProductRequestInDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AprRangeValidator implements ConstraintValidator<AprRangeValid, ProductRequestInDto> {

    @Override
    public boolean isValid(ProductRequestInDto dto, ConstraintValidatorContext context) {
        if (dto == null) return true;

        Double minApr = dto.getMin_apr();
        Double maxApr = dto.getMax_apr();

        // ✅ Only check when both values are provided
        if (minApr != null && maxApr != null) {
            return minApr <= maxApr;
        }

        return true;
    }
}

