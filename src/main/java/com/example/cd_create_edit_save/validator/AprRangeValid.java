package com.example.cd_create_edit_save.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AprRangeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AprRangeValid {
    String message() default "Minimum APR cannot be greater than Maximum APR";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
