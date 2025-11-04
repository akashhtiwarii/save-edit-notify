package com.example.cd_create_edit_save.validator;

import com.example.cd_create_edit_save.model.dto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.ProductUpdateInDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SecurityDepositValidator implements ConstraintValidator<ValidSecurityDeposit, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String securityDepositIndicator = null;
        Integer securityDepositMin = null;
        Integer securityDepositMax = null;

        if (value instanceof ProductCreateInDto) {
            ProductCreateInDto dto = (ProductCreateInDto) value;
            securityDepositIndicator = dto.getSecurityDepositIndicator();
            securityDepositMin = dto.getSecurityDepositMin();
            securityDepositMax = dto.getSecurityDepositMax();
        } else if (value instanceof ProductUpdateInDto) {
            ProductUpdateInDto dto = (ProductUpdateInDto) value;
            securityDepositIndicator = dto.getSecurityDepositIndicator();
            securityDepositMin = dto.getSecurityDepositMin();
            securityDepositMax = dto.getSecurityDepositMax();
        } else {
            return true;
        }

        if (securityDepositIndicator == null) {
            return true;
        }

        if ("Y".equalsIgnoreCase(securityDepositIndicator)) {

            if (securityDepositMin == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Security deposit min is required when security deposit is enabled")
                        .addPropertyNode("securityDepositMin")
                        .addConstraintViolation();
                return false;
            }

            if (securityDepositMax == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Security deposit max is required when security deposit is enabled")
                        .addPropertyNode("securityDepositMax")
                        .addConstraintViolation();
                return false;
            }

            if (securityDepositMin >= securityDepositMax) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Security deposit max must be greater than security deposit min")
                        .addPropertyNode("securityDepositMax")
                        .addConstraintViolation();
                return false;
            }
        }

        if ("N".equalsIgnoreCase(securityDepositIndicator)) {
            if (securityDepositMin != null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Security deposit min must be null when security deposit is not required")
                        .addPropertyNode("securityDepositMin")
                        .addConstraintViolation();
                return false;
            }

            if (securityDepositMax != null) {
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