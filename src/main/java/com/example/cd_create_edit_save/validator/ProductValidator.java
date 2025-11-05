package com.example.cd_create_edit_save.validator;

import com.example.cd_create_edit_save.exception.InvalidRequestException;
import com.example.cd_create_edit_save.exception.ResourceNotFoundException;
import com.example.cd_create_edit_save.model.dto.inDto.ProductCreateInDto;
import com.example.cd_create_edit_save.model.dto.inDto.ProductUpdateInDto;
import com.example.cd_create_edit_save.model.entity.Product;
import com.example.cd_create_edit_save.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductValidator {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductShortCodeRepository productShortCodeRepository;
    private final FeeTypeShortCodeRepository feeTypeShortCodeRepository;
    private final RewardsTypeShortCodeRepository rewardsTypeShortCodeRepository;
    private final PrinCodeRepository prinCodeRepository;
    private final CwsProdCodeRepository cwsProdCodeRepository;
    private final ChaCodeRepository chaCodeRepository;

    private static final BigDecimal CASH_APR_MULTIPLIER = new BigDecimal("1.05");
    private static final BigDecimal TOLERANCE = new BigDecimal("0.01");

    public Product validateProductIdAndGetProduct(String productId) {
        log.info("Validating product ID: {}", productId);
        if (productId == null || productId.trim().isEmpty()) {
            log.error("Invalid product ID: {}", productId);
            throw new InvalidRequestException("Product ID cannot be null or empty");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", productId);
                    return new ResourceNotFoundException("Product not found with ID: " + productId);
                });

        log.info("Product validation successful for ID: {}", productId);
        return product;
    }

    /**
     * Validate product create request
     */
    public void validateProductCreateRequest(ProductCreateInDto dto) {
        log.info("Starting validation for product create request");

        validateProductShtCode(dto.getProductShtCd());
        validateFeeTypeShtCode(dto.getFeeTypeShtCd());
        validateRewardsTypeShtCode(dto.getRewardsTypeShtCd());
        validatePrinCode(dto.getPrin());
        validateCwsProductCode(dto.getCwsProductId());
        validateChaCode(dto.getChaCode());
        validateAprTypeAndValueType(dto.getAprType(), dto.getAprValueType());
        validateAprMinMax(dto.getAprValueType(), dto.getPurchaseAprMin(), dto.getPurchaseAprMax());
        verifyCashApr(dto.getPurchaseAprMin(), dto.getPurchaseAprMax(), dto.getCashAprMin(), dto.getCashAprMax());
        validateCreditLine(dto.getCreditLineMin(), dto.getCreditLineMax());
        validateSecurityDeposit(dto.getSecurityDepositIndicator(), dto.getSecurityDepositMin(), dto.getSecurityDepositMax());
        validateDates(dto.getStartDate(), dto.getEndDate());
        validateApprover(dto.getToBeApprovedBy());

        log.info("All validations passed successfully");
    }

    /**
     * Validate product update request
     */
    public void validateProductUpdateRequest(ProductUpdateInDto dto) {
        log.info("Starting validation for product update request");

        validatePrinCode(dto.getPrin());
        validateCwsProductCode(dto.getCwsProductId());
        validateChaCode(dto.getChaCode());
        validateAprTypeAndValueType(dto.getAprType(), dto.getAprValueType());
        validateAprMinMax(dto.getAprValueType(), dto.getPurchaseAprMin(), dto.getPurchaseAprMax());
        verifyCashApr(dto.getPurchaseAprMin(), dto.getPurchaseAprMax(), dto.getCashAprMin(), dto.getCashAprMax());
        validateCreditLine(dto.getCreditLineMin(), dto.getCreditLineMax());
        validateSecurityDeposit(dto.getSecurityDepositIndicator(), dto.getSecurityDepositMin(), dto.getSecurityDepositMax());
        validateDates(dto.getStartDate(), dto.getEndDate());
        validateApprover(dto.getToBeApprovedBy());

        log.info("All validations passed successfully");
    }

    /**
     * Validate non-editable fields
     */
    public void validateNonEditableFields(Product existingProduct, ProductUpdateInDto requestDto) {
        log.info("Checking if non-editable fields have been modified");

        if (!existingProduct.getProductShtCd().equals(requestDto.getProductShtCd())) {
            log.error("Attempt to change Product Short Code from {} to {}",
                    existingProduct.getProductShtCd(), requestDto.getProductShtCd());
            throw new InvalidRequestException(
                    "Product Short Code cannot be changed. Original: " + existingProduct.getProductShtCd() +
                            ", Attempted: " + requestDto.getProductShtCd()
            );
        }

        if (!existingProduct.getFeeTypeShtCd().equals(requestDto.getFeeTypeShtCd())) {
            log.error("Attempt to change Fee Type Short Code from {} to {}",
                    existingProduct.getFeeTypeShtCd(), requestDto.getFeeTypeShtCd());
            throw new InvalidRequestException(
                    "Fee Type Short Code cannot be changed. Original: " + existingProduct.getFeeTypeShtCd() +
                            ", Attempted: " + requestDto.getFeeTypeShtCd()
            );
        }

        if (!existingProduct.getRewardsTypeShtCd().equals(requestDto.getRewardsTypeShtCd())) {
            log.error("Attempt to change Rewards Type Short Code from {} to {}",
                    existingProduct.getRewardsTypeShtCd(), requestDto.getRewardsTypeShtCd());
            throw new InvalidRequestException(
                    "Rewards Type Short Code cannot be changed. Original: " + existingProduct.getRewardsTypeShtCd() +
                            ", Attempted: " + requestDto.getRewardsTypeShtCd()
            );
        }

        log.info("Non-editable fields validation passed");
    }

    /**
     * Validate Product Short Code exists in master data
     */
    private void validateProductShtCode(String productShtCd) {
        log.info("Validating Product Short Code: {}", productShtCd);
        boolean exists = productShortCodeRepository.existsByProductShortCode(productShtCd);

        if (!exists) {
            log.error("Product Short Code not found: {}", productShtCd);
            throw new InvalidRequestException(
                    "Product Short Code '" + productShtCd + "' does not exist in the system. Please select a valid product category."
            );
        }

        log.info("Product Short Code validation passed");
    }

    /**
     * Validate Fee Type Short Code exists in master data
     */
    private void validateFeeTypeShtCode(String feeTypeShtCd) {
        log.info("Validating Fee Type Short Code: {}", feeTypeShtCd);
        boolean exists = feeTypeShortCodeRepository.existsByFeeTypeShortCode(feeTypeShtCd);

        if (!exists) {
            log.error("Fee Type Short Code not found: {}", feeTypeShtCd);
            throw new InvalidRequestException(
                    "Fee Type Short Code '" + feeTypeShtCd + "' does not exist in the system. Please select a valid fee type."
            );
        }

        log.info("Fee Type Short Code validation passed");
    }

    /**
     * Validate Rewards Type Short Code exists in master data
     */
    private void validateRewardsTypeShtCode(String rewardsTypeShtCd) {
        log.info("Validating Rewards Type Short Code: {}", rewardsTypeShtCd);
        boolean exists = rewardsTypeShortCodeRepository.existsByRewardsTypeShortCode(rewardsTypeShtCd);

        if (!exists) {
            log.error("Rewards Type Short Code not found: {}", rewardsTypeShtCd);
            throw new InvalidRequestException(
                    "Rewards Type Short Code '" + rewardsTypeShtCd + "' does not exist in the system. Please select a valid rewards type."
            );
        }

        log.info("Rewards Type Short Code validation passed");
    }

    /**
     * Validate PRIN Code exists in master data
     */
    private void validatePrinCode(String prinCode) {
        log.info("Validating PRIN Code: {}", prinCode);
        boolean exists = prinCodeRepository.existsByPrinCode(prinCode);

        if (!exists) {
            log.error("PRIN Code not found: {}", prinCode);
            throw new InvalidRequestException(
                    "PRIN Code '" + prinCode + "' does not exist in the system. Please select a valid PRIN code."
            );
        }

        log.info("PRIN Code validation passed");
    }

    /**
     * Validate CWS Product Code exists in master data
     */
    private void validateCwsProductCode(String cwsProductId) {
        log.info("Validating CWS Product Code: {}", cwsProductId);
        boolean exists = cwsProdCodeRepository.existsByCwsProdCode(cwsProductId);

        if (!exists) {
            log.error("CWS Product Code not found: {}", cwsProductId);
            throw new InvalidRequestException(
                    "CWS Product Code '" + cwsProductId + "' does not exist in the system. Please select a valid CWS product Code."
            );
        }

        log.info("CWS Product Code validation passed");
    }

    /**
     * Validate CHA Code exists in master data
     */
    private void validateChaCode(String chaCode) {
        log.info("Validating CHA Code: {}", chaCode);
        boolean exists = chaCodeRepository.existsByChaCode(chaCode);

        if (!exists) {
            log.error("CHA Code not found {}", chaCode);
            throw new InvalidRequestException(
                    "CHA Code '" + chaCode + "' does not exist in the system. Please select a valid CHA code."
            );
        }

        log.info("CHA Code validation passed");
    }

    /**
     * Validate APR type and value type consistency
     */
    private void validateAprTypeAndValueType(String aprType, String aprValueType) {
        log.info("Validating APR type: {} and value type: {}", aprType, aprValueType);

        if ("FIXED".equalsIgnoreCase(aprType)) {
            if (!"SPECIFIC".equalsIgnoreCase(aprValueType)) {
                log.error("APR type is FIXED but value type is not SPECIFIC: {}", aprValueType);
                throw new InvalidRequestException(
                        "When APR type is FIXED, APR value type must be SPECIFIC"
                );
            }
        }

        if ("VARIABLE".equalsIgnoreCase(aprType)) {
            if (!"RANGE".equalsIgnoreCase(aprValueType)) {
                log.error("APR type is VARIABLE but value type is not RANGE: {}", aprValueType);
                throw new InvalidRequestException(
                        "When APR type is VARIABLE, APR value type must be RANGE"
                );
            }
        }

        log.info("APR type and value type validation passed");
    }

    /**
     * Validate APR min and max values
     */
    private void validateAprMinMax(String aprValueType, BigDecimal min, BigDecimal max) {
        log.info("Validating APR min/max values");

        if ("SPECIFIC".equalsIgnoreCase(aprValueType)) {
            if (min.compareTo(max) != 0) {
                log.error("APR value type is SPECIFIC but min ({}) != max ({})", min, max);
                throw new InvalidRequestException(
                        "For SPECIFIC APR value type, Purchase APR Min and Max must be equal"
                );
            }
        }

        if ("RANGE".equalsIgnoreCase(aprValueType)) {
            if (min.compareTo(max) >= 0) {
                log.error("APR value type is RANGE but min ({}) >= max ({})", min, max);
                throw new InvalidRequestException(
                        "For RANGE APR value type, Purchase APR Max must be greater than Min"
                );
            }
        }

        log.info("APR min/max validation passed");
    }

    /**
     * Verify cash APR calculation (Purchase APR + 5%)
     */
    private void verifyCashApr(BigDecimal purchaseAprMin, BigDecimal purchaseAprMax,
                               BigDecimal cashAprMin, BigDecimal cashAprMax) {
        log.info("Verifying Cash APR calculation");

        BigDecimal expectedCashAprMin = purchaseAprMin
                .multiply(CASH_APR_MULTIPLIER)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal diffMin = cashAprMin.subtract(expectedCashAprMin).abs();

        if (diffMin.compareTo(TOLERANCE) > 0) {
            log.error("Cash APR Min is incorrect. Expected: {}, Received: {}",
                    expectedCashAprMin, cashAprMin);
            throw new InvalidRequestException(
                    String.format("Cash APR Min is incorrect. Expected: %s (Purchase APR Min + 5%%), but received: %s",
                            expectedCashAprMin, cashAprMin)
            );
        }

        BigDecimal expectedCashAprMax = purchaseAprMax
                .multiply(CASH_APR_MULTIPLIER)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal diffMax = cashAprMax.subtract(expectedCashAprMax).abs();

        if (diffMax.compareTo(TOLERANCE) > 0) {
            log.error("Cash APR Max is incorrect. Expected: {}, Received: {}",
                    expectedCashAprMax, cashAprMax);
            throw new InvalidRequestException(
                    String.format("Cash APR Max is incorrect. Expected: %s (Purchase APR Max + 5%%), but received: %s",
                            expectedCashAprMax, cashAprMax)
            );
        }

        log.info("Cash APR verification passed");
    }

    /**
     * Validate credit line min and max
     */
    private void validateCreditLine(Integer min, Integer max) {
        log.info("Validating credit line min: {}, max: {}", min, max);

        if (min >= max) {
            log.error("Credit line validation failed: min ({}) >= max ({})", min, max);
            throw new InvalidRequestException(
                    "Credit line max must be greater than credit line min"
            );
        }

        log.info("Credit line validation passed");
    }

    /**
     * Validate security deposit configuration
     * - If indicator = 'Y', min and max are required and max > min
     * - If indicator = 'N', min and max must be null
     */
    private void validateSecurityDeposit(String indicator, Integer min, Integer max) {
        log.info("Validating security deposit: indicator={}, min={}, max={}", indicator, min, max);

        if (indicator == null) {
            return;
        }

        if ("Y".equalsIgnoreCase(indicator)) {
            if (min == null) {
                log.error("Security deposit min is null when indicator is Y");
                throw new InvalidRequestException(
                        "Security deposit min is required when security deposit is enabled"
                );
            }

            if (max == null) {
                log.error("Security deposit max is null when indicator is Y");
                throw new InvalidRequestException(
                        "Security deposit max is required when security deposit is enabled"
                );
            }

            if (min >= max) {
                log.error("Security deposit min ({}) >= max ({})", min, max);
                throw new InvalidRequestException(
                        "Security deposit max must be greater than security deposit min"
                );
            }
        }

        if ("N".equalsIgnoreCase(indicator)) {
            if (min != null) {
                log.error("Security deposit min is not null when indicator is N");
                throw new InvalidRequestException(
                        "Security deposit min must be null when security deposit is not required"
                );
            }

            if (max != null) {
                log.error("Security deposit max is not null when indicator is N");
                throw new InvalidRequestException(
                        "Security deposit max must be null when security deposit is not required"
                );
            }
        }

        log.info("Security deposit validation passed");
    }

    /**
     * Validate start and end dates
     * - Start date must be at least 1 week from today
     * - End date must be AFTER start date (not equal)
     */
    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Validating start date: {}, end date: {}", startDate, endDate);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekFromNow = now.plusWeeks(1);

        if (startDate.isBefore(oneWeekFromNow)) {
            log.error("Start date ({}) is before one week from now ({})", startDate, oneWeekFromNow);
            throw new InvalidRequestException(
                    "Start date must be at least one week from today (" +
                            oneWeekFromNow.toLocalDate() + ")"
            );
        }

        if (endDate.isBefore(startDate) || endDate.isEqual(startDate)) {
            log.error("End date ({}) is not after start date ({})", endDate, startDate);
            throw new InvalidRequestException(
                    "End date must be after start date (not equal)"
            );
        }

        log.info("Date validation passed");
    }

    /**
     * Validate that the approver exists in the user table
     */
    private void validateApprover(String toBeApprovedBy) {
        log.info("Validating approver: {}", toBeApprovedBy);

        boolean approverExists = userRepository.existsByUsername(toBeApprovedBy);

        if (!approverExists) {
            log.error("Approver not found in user table: {}", toBeApprovedBy);
            throw new InvalidRequestException(
                    "Approver '" + toBeApprovedBy + "' does not exist in the system. Please select a valid user."
            );
        }

        log.info("Approver validation passed");
    }
}