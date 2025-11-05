package com.example.cd_create_edit_save.model.dto;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRequestInDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidatorInstance() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private ProductRequestInDto createValidDto() {
        ProductRequestInDto dto = new ProductRequestInDto();
        dto.setText("Valid Search");
        dto.setMin_apr(10.0);
        dto.setMax_apr(20.0);
        dto.setStatus("ACTIVE");
        dto.setOffset(0L);
        dto.setLimit(10L);
        return dto;
    }

    @Test
    void testValidDto_ShouldPassValidation() {
        ProductRequestInDto dto = createValidDto();
        Set<ConstraintViolation<ProductRequestInDto>> violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }

    @Test
    void testTextTooLong_ShouldFailValidation() {
        ProductRequestInDto dto = createValidDto();
        dto.setText("a".repeat(101)); // exceed max size 100
        Set<ConstraintViolation<ProductRequestInDto>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getMessage().contains("Search text must not exceed 100 characters"));
    }

    @Test
    void testMinAprOutOfRange_ShouldFailValidation() {
        ProductRequestInDto dto = createValidDto();
        dto.setMin_apr(-1.0);
        Set<ConstraintViolation<ProductRequestInDto>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getMessage().contains("Minimum APR must be greater than or equal to 0"));
    }

    @Test
    void testMaxAprOutOfRange_ShouldFailValidation() {
        ProductRequestInDto dto = createValidDto();
        dto.setMax_apr(200.0);
        Set<ConstraintViolation<ProductRequestInDto>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getMessage().contains("Maximum APR cannot exceed 100"));
    }

    @Test
    void testInvalidStatus_ShouldFailValidation() {
        ProductRequestInDto dto = createValidDto();
        dto.setStatus("INVALID");
        Set<ConstraintViolation<ProductRequestInDto>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getMessage().contains("Status must be one of"));
    }

    @Test
    void testNegativeOffset_ShouldFailValidation() {
        ProductRequestInDto dto = createValidDto();
        dto.setOffset(-5L);
        Set<ConstraintViolation<ProductRequestInDto>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getMessage().contains("Offset must be greater than or equal to 0"));
    }

    @Test
    void testZeroLimit_ShouldFailValidation() {
        ProductRequestInDto dto = createValidDto();
        dto.setLimit(0L);
        Set<ConstraintViolation<ProductRequestInDto>> violations = validator.validate(dto);
        assertThat(violations).anyMatch(v -> v.getMessage().contains("Limit must be greater than 0"));
    }

    @Test
    void testNullFields_ShouldPassBecauseOptional() {
        ProductRequestInDto dto = new ProductRequestInDto();
        Set<ConstraintViolation<ProductRequestInDto>> violations = validator.validate(dto);
        // text, apr, status, limit, offset are optional
        assertThat(violations).isEmpty();
    }

    @Test
    void testToString_ShouldContainClassName() {
        ProductRequestInDto dto = new ProductRequestInDto();
        dto.setText("Gold Loan");
        dto.setStatus("ACTIVE");

        assertThat(dto.toString())
                .contains("ProductRequestInDto")
                .contains("Gold Loan")
                .contains("ACTIVE");
    }

}
