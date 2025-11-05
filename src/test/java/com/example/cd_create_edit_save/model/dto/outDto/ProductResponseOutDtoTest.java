package com.example.cd_create_edit_save.model.dto.outDto;



import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseOutDtoTest {

    @Test
    void testFromEntity_WithAllValuesAsExpectedTypes() {
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();

        Object[] obj = new Object[]{
                "SC001", "Credit Card", "FEE", "ACTIVE",
                Timestamp.valueOf(start),
                Timestamp.valueOf(end),
                12.5, 15.75
        };

        ProductResponseOutDto dto = ProductResponseOutDto.fromEntity(obj);

        assertThat(dto).isNotNull();
        assertThat(dto.getShortCode()).isEqualTo("SC001");
        assertThat(dto.getProductName()).isEqualTo("Credit Card");
        assertThat(dto.getFee_type()).isEqualTo("FEE");
        assertThat(dto.getStatus()).isEqualTo("ACTIVE");
        assertThat(dto.getStartDate()).isEqualTo(start);
        assertThat(dto.getEndDate()).isEqualTo(end);
        assertThat(dto.getPurchase_apr_min()).isEqualTo(12.5);
        assertThat(dto.getPurchase_apr_max()).isEqualTo(15.75);
    }

    @Test
    void testFromEntity_WithLocalDateTimeInstances() {
        LocalDateTime start = LocalDateTime.now().minusDays(3);
        LocalDateTime end = LocalDateTime.now().plusDays(3);

        Object[] obj = new Object[]{
                "SC002", "Loan", "PROC", "PENDING",
                start, end,
                1.0, 2.0
        };

        ProductResponseOutDto dto = ProductResponseOutDto.fromEntity(obj);

        assertThat(dto.getStartDate()).isEqualTo(start);
        assertThat(dto.getEndDate()).isEqualTo(end);
    }

    @Test
    void testFromEntity_WithStringDates() {
        LocalDateTime start = LocalDateTime.of(2023, 10, 10, 10, 10);
        LocalDateTime end = LocalDateTime.of(2023, 12, 12, 12, 12);

        Object[] obj = new Object[]{
                "SC003", "Savings", "ADMIN", "APPROVED",
                start.toString(), end.toString(),
                3.3, 9.9
        };

        ProductResponseOutDto dto = ProductResponseOutDto.fromEntity(obj);

        assertThat(dto.getStartDate()).isEqualTo(start);
        assertThat(dto.getEndDate()).isEqualTo(end);
    }

    @Test
    void testFromEntity_WithNullArray() {
        assertThat(ProductResponseOutDto.fromEntity(null)).isNull();
    }

    @Test
    void testFromEntity_WithNullElements() {
        Object[] obj = new Object[8]; // all nulls

        ProductResponseOutDto dto = ProductResponseOutDto.fromEntity(obj);

        assertThat(dto.getShortCode()).isNull();
        assertThat(dto.getProductName()).isNull();
        assertThat(dto.getFee_type()).isNull();
        assertThat(dto.getStatus()).isNull();
        assertThat(dto.getStartDate()).isNull();
        assertThat(dto.getEndDate()).isNull();
        assertThat(dto.getPurchase_apr_min()).isNull();
        assertThat(dto.getPurchase_apr_max()).isNull();
    }


}

