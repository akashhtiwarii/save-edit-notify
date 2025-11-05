package com.example.cd_create_edit_save.validator;

import com.example.cd_create_edit_save.model.dto.ProductRequestInDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AprRangeValidator implements ConstraintValidator<AprRangeValid, ProductRequestInDto> {


    /**
     * To validate the apr range.
     * @param dto
     * @param context
     * @return boolean
     */
    @Override
    public boolean isValid(final ProductRequestInDto dto, final ConstraintValidatorContext context) {
        if (dto == null) return true;

        Double minApr = dto.getMin_apr();
        Double maxApr = dto.getMax_apr();

        if (minApr != null && maxApr != null){
            return minApr <= maxApr;
        }

        return true;
    }
}
