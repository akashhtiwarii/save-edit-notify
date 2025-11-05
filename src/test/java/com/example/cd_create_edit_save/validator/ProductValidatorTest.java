package com.example.cd_create_edit_save.validator;

import com.example.cd_create_edit_save.exception.InvalidRequestException;
import com.example.cd_create_edit_save.exception.ResourceNotFoundException;
import com.example.cd_create_edit_save.model.dto.inDto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.entity.Product;
import com.example.cd_create_edit_save.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductValidatorTest {

    @Mock private UserRepository userRepository;
    @Mock private ProductRepository productRepository;
    @Mock private ProductShortCodeRepository productShortCodeRepository;
    @Mock private FeeTypeShortCodeRepository feeTypeShortCodeRepository;
    @Mock private RewardsTypeShortCodeRepository rewardsTypeShortCodeRepository;
    @Mock private PrinCodeRepository prinCodeRepository;
    @Mock private CwsProdCodeRepository cwsProdCodeRepository;
    @Mock private ChaCodeRepository chaCodeRepository;

    @InjectMocks
    private ProductValidator productValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateProductIdAndGetProduct_Success() {
        Product product = new Product();
        when(productRepository.findById("P123")).thenReturn(Optional.of(product));

        Product result = productValidator.validateProductIdAndGetProduct("P123");

        assertNotNull(result);
        verify(productRepository).findById("P123");
    }

    @Test
    void testValidateProductIdAndGetProduct_NullId_ThrowsException() {
        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductIdAndGetProduct(null));
        assertEquals("Product ID cannot be null or empty", ex.getMessage());
    }

    @Test
    void testValidateProductIdAndGetProduct_NotFound_ThrowsException() {
        when(productRepository.findById("P123")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> productValidator.validateProductIdAndGetProduct("P123"));
    }

    @Test
    void testValidateNonEditableFields_Success() {
        Product existing = new Product();
        existing.setProductShtCd("A");
        existing.setFeeTypeShtCd("F");
        existing.setRewardsTypeShtCd("R");

        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd("A");
        dto.setFeeTypeShtCd("F");
        dto.setRewardsTypeShtCd("R");

        assertDoesNotThrow(() -> productValidator.validateNonEditableFields(existing, dto));
    }

    @Test
    void testValidateNonEditableFields_ProductShortCodeChanged_ThrowsException() {
        Product existing = new Product();
        existing.setProductShtCd("OLD");
        existing.setFeeTypeShtCd("F");
        existing.setRewardsTypeShtCd("R");

        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setProductShtCd("NEW");
        dto.setFeeTypeShtCd("F");
        dto.setRewardsTypeShtCd("R");

        InvalidRequestException ex = assertThrows(InvalidRequestException.class,
                () -> productValidator.validateNonEditableFields(existing, dto));
        assertTrue(ex.getMessage().contains("Product Short Code cannot be changed"));
    }

    @Test
    void testValidateAprTypeAndValueType_InvalidCombination_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprType("FIXED");
        dto.setAprValueType("RANGE");

        assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateAprMinMax_InvalidRange_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setAprType("VARIABLE");
        dto.setAprValueType("RANGE");
        dto.setPurchaseAprMin(BigDecimal.valueOf(20));
        dto.setPurchaseAprMax(BigDecimal.valueOf(15)); // invalid

        assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testVerifyCashApr_Invalid_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setCashAprMin(BigDecimal.valueOf(10)); // wrong
        dto.setCashAprMax(BigDecimal.valueOf(10));

        assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateCreditLine_Invalid_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setCreditLineMin(300);
        dto.setCreditLineMax(100);

        assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateDates_StartDateTooSoon_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setStartDate(LocalDateTime.now().plusDays(2));
        dto.setEndDate(LocalDateTime.now().plusDays(10));

        assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateDates_EndDateBeforeStartDate_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        dto.setStartDate(LocalDateTime.now().plusWeeks(2));
        dto.setEndDate(LocalDateTime.now().plusWeeks(1));

        assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateApprover_NotFound_ThrowsException() {
        ProductUpdateInDto dto = validUpdateDto();
        when(userRepository.existsByUsername("admin")).thenReturn(false);
        assertThrows(InvalidRequestException.class,
                () -> productValidator.validateProductUpdateRequest(dto));
    }

    @Test
    void testValidateApprover_Success() {
        when(prinCodeRepository.existsByPrinCode(anyString())).thenReturn(true);
        when(cwsProdCodeRepository.existsByCwsProdCode(anyString())).thenReturn(true);
        when(chaCodeRepository.existsByChaCode(anyString())).thenReturn(true);
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        ProductUpdateInDto dto = validUpdateDto();

        assertDoesNotThrow(() -> productValidator.validateProductUpdateRequest(dto));
    }

    private ProductUpdateInDto validUpdateDto() {
        ProductUpdateInDto dto = new ProductUpdateInDto();
        dto.setPrin("PRIN01");
        dto.setCwsProductId("CWS01");
        dto.setChaCode("CHA01");
        dto.setAprType("FIXED");
        dto.setAprValueType("SPECIFIC");
        dto.setPurchaseAprMin(BigDecimal.valueOf(10));
        dto.setPurchaseAprMax(BigDecimal.valueOf(10));
        dto.setCashAprMin(BigDecimal.valueOf(10.5));
        dto.setCashAprMax(BigDecimal.valueOf(10.5));
        dto.setCreditLineMin(100);
        dto.setCreditLineMax(200);
        dto.setSecurityDepositIndicator("N");
        dto.setStartDate(LocalDateTime.now().plusWeeks(2));
        dto.setEndDate(LocalDateTime.now().plusWeeks(3));
        dto.setToBeApprovedBy("admin");
        return dto;
    }
}

