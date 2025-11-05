package com.example.cd_create_edit_save.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Documented
@Constraint(validatedBy = AprRangeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AprRangeValid {
    /**
     * To check min and max apr range in the request.
     * @return String
     */
    String message() default "Minimum APR cannot be greater than Maximum APR";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
