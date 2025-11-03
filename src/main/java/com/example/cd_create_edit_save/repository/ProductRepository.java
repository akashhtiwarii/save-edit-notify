package com.example.cd_create_edit_save.repository;

import com.example.cd_create_edit_save.model.dto.ProductOutDto;
import com.example.cd_create_edit_save.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(value = """
    SELECT
        ps.PRD_SHT_CD AS shortCode,
        ps.PRODUCT AS productName,
        p.FEE_TYPE_SHT_CD AS fee_type,
        p.status AS status,
        p.START_DATE AS startDate,
        p.END_DATE AS endDate,
        p.purchase_apr_min,
        p.purchase_apr_max
    FROM TBL_PRODUCT p
    JOIN TBL_PRODUCT_SHT_CODE ps 
      ON p.PRODUCT_SHT_CD = ps.PRD_SHT_CD
   OFFSET COALESCE(:offset, 0)
               LIMIT COALESCE(:limit, 9223372036854775807)
""", nativeQuery = true)
    List<Object[]> getProducts(@Param("offset") Long offset, @Param("limit") Long limit);





    @Query(value = """
        SELECT 
            ps.PRD_SHT_CD AS shortCode,
            ps.PRODUCT AS productName,
            p.FEE_TYPE_SHT_CD AS fee_type,
            p.status AS status,
            p.START_DATE AS startDate,
            p.END_DATE AS endDate,
            p.purchase_apr_min,
            p.purchase_apr_max
        FROM (
            SELECT p.product_sht_cd, p.fee_type_sht_cd, p.purchase_apr_min, p.purchase_apr_max,
                   p.status, p.start_date, p.end_date
            FROM tbl_product p
            WHERE (:status IS NULL OR p.status = :status)
              AND (:apr_min IS NULL OR p.purchase_apr_min >= :apr_min)
              AND (:apr_max IS NULL OR p.purchase_apr_max <= :apr_max)
        ) p
        LEFT JOIN tbl_product_sht_code ps ON p.product_sht_cd = ps.prd_sht_cd
        WHERE (
            COALESCE(:text, '') = ''
            OR LOWER(p.product_sht_cd) LIKE LOWER(CONCAT('%', COALESCE(:text, ''), '%'))
            OR LOWER(ps.product) LIKE LOWER(CONCAT('%', COALESCE(:text, ''), '%'))
        )
               OFFSET COALESCE(:offset, 0)
               LIMIT COALESCE(:limit, 9223372036854775807)
        """,
            nativeQuery = true)
    List<Object[]> findProductsFiltered(
            @Param("text") String text,
            @Param("status") String status,
            @Param("apr_min") Double purchaseAprMin,
            @Param("apr_max") Double purchaseAprMax,
            @Param("limit") Long limit,
            @Param("offset") Long offset
    );

}


