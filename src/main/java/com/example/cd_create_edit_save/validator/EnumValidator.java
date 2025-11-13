package com.example.cd_create_edit_save.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum annotation) {
        this.enumClass = annotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        for (Enum<?> enumVal : enumClass.getEnumConstants()) {
            if (enumVal.name().equalsIgnoreCase(value.replace(" ", "_")))
                return true;
        }
        return false;
    }
}