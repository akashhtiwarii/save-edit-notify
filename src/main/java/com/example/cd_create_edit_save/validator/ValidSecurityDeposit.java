package com.example.cd_create_edit_save.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SecurityDepositValidator.class)
@Documented
public @interface ValidSecurityDeposit {
    String message() default "Invalid security deposit configuration";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}