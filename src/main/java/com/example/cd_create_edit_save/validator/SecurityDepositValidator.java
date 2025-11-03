package com.example.cd_create_edit_save.validator;

import com.example.cd_create_edit_save.model.dto.ProductCreateInDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SecurityDepositValidator implements ConstraintValidator<ValidSecurityDeposit, ProductCreateInDto> {

    @Override
    public boolean isValid(ProductCreateInDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        if (dto.getSecurityDepositIndicator() == null) {
            return true;
        }

        if ("Y".equalsIgnoreCase(dto.getSecurityDepositIndicator())) {

            if (dto.getSecurityDepositMin() == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Security deposit min is required when security deposit is enabled")
                        .addPropertyNode("securityDepositMin")
                        .addConstraintViolation();
                return false;
            }

            if (dto.getSecurityDepositMax() == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Security deposit max is required when security deposit is enabled")
                        .addPropertyNode("securityDepositMax")
                        .addConstraintViolation();
                return false;
            }

            if (dto.getSecurityDepositMin() >= dto.getSecurityDepositMax()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Security deposit max must be greater than security deposit min")
                        .addPropertyNode("securityDepositMax")
                        .addConstraintViolation();
                return false;
            }
        }


        if ("N".equalsIgnoreCase(dto.getSecurityDepositIndicator())) {
            if (dto.getSecurityDepositMin() != null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Security deposit min must be null when security deposit is not required")
                        .addPropertyNode("securityDepositMin")
                        .addConstraintViolation();
                return false;
            }

            if (dto.getSecurityDepositMax() != null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Security deposit max must be null when security deposit is not required")
                        .addPropertyNode("securityDepositMax")
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}