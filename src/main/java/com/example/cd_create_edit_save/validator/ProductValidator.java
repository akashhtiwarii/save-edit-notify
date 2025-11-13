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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private final FeeValuesRepository feeValuesRepository;

    private static final BigDecimal CASH_APR_ADDITION = new BigDecimal("5.00");

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
        validateFeeValue(dto.getFeeTypeShtCd(), dto.getFeeValue());
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
        validateFeeValue(dto.getFeeTypeShtCd(), dto.getFeeValue());
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

        if (!existingProduct.getAprType().equals(requestDto.getAprType())) {
            log.error("Attempt to change APR Type from {} to {}",
                    existingProduct.getAprType(), requestDto.getAprType());
            throw new InvalidRequestException(
                    "APR Type cannot be changed. Original: " + existingProduct.getAprType() +
                            ", Attempted: " + requestDto.getAprType()
            );
        }

        log.info("Non-editable fields validation passed");
    }

    /**
     * Validate that meaningful changes were made (not just dates)
     */
    public void validateFieldChanges(Product existingProduct, ProductUpdateInDto requestDto) {
        log.info("Validating field changes to determine if new version should be created");

        boolean datesChanged = checkDatesChanged(existingProduct, requestDto);
        boolean otherFieldsChanged = checkOtherFieldsChanged(existingProduct, requestDto);

        if (!datesChanged && !otherFieldsChanged) {
            log.error("No changes detected in the update request");
            throw new InvalidRequestException(
                    "No changes detected. Please modify at least one field to create a new version."
            );
        }

        if (datesChanged && !otherFieldsChanged) {
            log.error("Only dates were modified, no other fields changed");
            throw new InvalidRequestException(
                    "Cannot create new product version with only date changes. " +
                            "Please update other fields along with dates, or keep the existing dates."
            );
        }

        log.info("Valid field changes detected, proceeding with new version creation");
    }

    private boolean checkDatesChanged(Product existing, ProductUpdateInDto dto) {
        boolean startDateChanged = !existing.getStartDate().isEqual(dto.getStartDate());
        boolean endDateChanged = !existing.getEndDate().isEqual(dto.getEndDate());

        boolean datesChanged = startDateChanged || endDateChanged;
        log.debug("Dates changed: {}", datesChanged);

        return datesChanged;
    }

    private boolean checkOtherFieldsChanged(Product existing, ProductUpdateInDto dto) {
        boolean changed = false;

        if (!existing.getAprValueType().equals(dto.getAprValueType())) {
            log.debug("APR Value Type changed");
            changed = true;
        }

        if (existing.getPurchaseAprMin().compareTo(dto.getPurchaseAprMin()) != 0) {
            log.debug("Purchase APR Min changed");
            changed = true;
        }
        if (existing.getPurchaseAprMax().compareTo(dto.getPurchaseAprMax()) != 0) {
            log.debug("Purchase APR Max changed");
            changed = true;
        }

        if (existing.getCashAprMin().compareTo(dto.getCashAprMin()) != 0) {
            log.debug("Cash APR Min changed");
            changed = true;
        }
        if (existing.getCashAprMax().compareTo(dto.getCashAprMax()) != 0) {
            log.debug("Cash APR Max changed");
            changed = true;
        }

        if (!existing.getCreditLineMin().equals(dto.getCreditLineMin())) {
            log.debug("Credit Line Min changed");
            changed = true;
        }
        if (!existing.getCreditLineMax().equals(dto.getCreditLineMax())) {
            log.debug("Credit Line Max changed");
            changed = true;
        }

        if (!existing.getSecurityDepositIndicator().equals(dto.getSecurityDepositIndicator())) {
            log.debug("Security Deposit Indicator changed");
            changed = true;
        }
        if (!safeEquals(existing.getSecurityDepositMin(), dto.getSecurityDepositMin())) {
            log.debug("Security Deposit Min changed");
            changed = true;
        }
        if (!safeEquals(existing.getSecurityDepositMax(), dto.getSecurityDepositMax())) {
            log.debug("Security Deposit Max changed");
            changed = true;
        }

        if (!existing.getTermsConditionsLink().equals(dto.getTermsConditionsLink())) {
            log.debug("Terms & Conditions Link changed");
            changed = true;
        }
        if (!existing.getCardholderAgreementLink().equals(dto.getCardholderAgreementLink())) {
            log.debug("Cardholder Agreement Link changed");
            changed = true;
        }
        if (!existing.getCardImageLink().equals(dto.getCardImageLink())) {
            log.debug("Card Image Link changed");
            changed = true;
        }

        if (!existing.getPrin().equals(dto.getPrin())) {
            log.debug("PRIN changed");
            changed = true;
        }
        if (!existing.getCwsProductId().equals(dto.getCwsProductId())) {
            log.debug("CWS Product ID changed");
            changed = true;
        }
        if (!existing.getChaCode().equals(dto.getChaCode())) {
            log.debug("CHA Code changed");
            changed = true;
        }

        String newBoardingIndicator = buildBoardingIndicator(dto);
        if (!existing.getBoardingIndicator().equals(newBoardingIndicator)) {
            log.debug("Boarding Indicator changed");
            changed = true;
        }

        if (!existing.getToBeApprovedBy().equals(dto.getToBeApprovedBy())) {
            log.debug("To Be Approved By changed");
            changed = true;
        }
        if (!safeEquals(existing.getCommentsToApprover(), dto.getCommentsToApprover())) {
            log.debug("Comments To Approver changed");
            changed = true;
        }

        if (!safeEquals(existing.getFeeValue(), dto.getFeeValue())) {
            log.debug("Fee Value changed");
            changed = true;
        }

        log.debug("Other fields changed: {}", changed);
        return changed;
    }

    private boolean safeEquals(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) return true;
        if (obj1 == null || obj2 == null) return false;
        return obj1.equals(obj2);
    }

    private String buildBoardingIndicator(ProductUpdateInDto dto) {
        List<String> indicators = new ArrayList<>();

        if (Boolean.TRUE.equals(dto.getPcFlag1())) indicators.add("PC_FLAG1");
        if (Boolean.TRUE.equals(dto.getPcFlag2())) indicators.add("PC_FLAG2");
        if (Boolean.TRUE.equals(dto.getPcFlag3())) indicators.add("PC_FLAG3");
        if (Boolean.TRUE.equals(dto.getPcFlag4())) indicators.add("PC_FLAG4");
        if (Boolean.TRUE.equals(dto.getPcFlag5())) indicators.add("PC_FLAG5");
        if (Boolean.TRUE.equals(dto.getPcFlag6())) indicators.add("PC_FLAG6");
        if (Boolean.TRUE.equals(dto.getPcFlag7())) indicators.add("PC_FLAG7");
        if (Boolean.TRUE.equals(dto.getPcFlag8())) indicators.add("PC_FLAG8");
        if (Boolean.TRUE.equals(dto.getPcFlag9())) indicators.add("PC_FLAG9");
        if (Boolean.TRUE.equals(dto.getPcFlag10())) indicators.add("PC_FLAG10");

        if (dto.getUpc() != null && !dto.getUpc().trim().isEmpty()) {
            indicators.add(dto.getUpc());
        }

        return String.join(",", indicators);
    }

    private void validateProductShtCode(String productShtCd) {
        log.info("Validating Product Short Code: {}", productShtCd);
        boolean exists = productShortCodeRepository.existsByProductShortCode(productShtCd);

        if (!exists) {
            log.error("Product Short Code not found: {}", productShtCd);
            throw new InvalidRequestException(
                    "Product Short Code '" + productShtCd + "' does not exist in the system."
            );
        }

        log.info("Product Short Code validation passed");
    }

    private void validateFeeTypeShtCode(String feeTypeShtCd) {
        log.info("Validating Fee Type Short Code: {}", feeTypeShtCd);
        boolean exists = feeTypeShortCodeRepository.existsByFeeTypeShortCode(feeTypeShtCd);

        if (!exists) {
            log.error("Fee Type Short Code not found: {}", feeTypeShtCd);
            throw new InvalidRequestException(
                    "Fee Type Short Code '" + feeTypeShtCd + "' does not exist in the system."
            );
        }

        log.info("Fee Type Short Code validation passed");
    }

    private void validateRewardsTypeShtCode(String rewardsTypeShtCd) {
        log.info("Validating Rewards Type Short Code: {}", rewardsTypeShtCd);
        boolean exists = rewardsTypeShortCodeRepository.existsByRewardsTypeShortCode(rewardsTypeShtCd);

        if (!exists) {
            log.error("Rewards Type Short Code not found: {}", rewardsTypeShtCd);
            throw new InvalidRequestException(
                    "Rewards Type Short Code '" + rewardsTypeShtCd + "' does not exist in the system."
            );
        }

        log.info("Rewards Type Short Code validation passed");
    }

    private void validatePrinCode(String prinCode) {
        log.info("Validating PRIN Code: {}", prinCode);
        boolean exists = prinCodeRepository.existsByPrinCode(prinCode);

        if (!exists) {
            log.error("PRIN Code not found: {}", prinCode);
            throw new InvalidRequestException(
                    "PRIN Code '" + prinCode + "' does not exist in the system."
            );
        }

        log.info("PRIN Code validation passed");
    }

    private void validateCwsProductCode(String cwsProductId) {
        log.info("Validating CWS Product Code: {}", cwsProductId);
        boolean exists = cwsProdCodeRepository.existsByCwsProdCode(cwsProductId);

        if (!exists) {
            log.error("CWS Product Code not found: {}", cwsProductId);
            throw new InvalidRequestException(
                    "CWS Product Code '" + cwsProductId + "' does not exist in the system."
            );
        }

        log.info("CWS Product Code validation passed");
    }

    private void validateChaCode(String chaCode) {
        log.info("Validating CHA Code: {}", chaCode);
        boolean exists = chaCodeRepository.existsByChaCode(chaCode);

        if (!exists) {
            log.error("CHA Code not found: {}", chaCode);
            throw new InvalidRequestException(
                    "CHA Code '" + chaCode + "' does not exist in the system."
            );
        }

        log.info("CHA Code validation passed");
    }

    /**
     * Validate fee value based on fee type
     */
    private void validateFeeValue(String feeTypeShtCd, BigDecimal feeValue) {
        log.info("Validating fee value for fee type: {}", feeTypeShtCd);

        if ("NF".equalsIgnoreCase(feeTypeShtCd)) {
            if (feeValue != null && feeValue.compareTo(BigDecimal.ZERO) != 0) {
                log.error("Fee value must be null or 0 for NONE fee type, but got: {}", feeValue);
                throw new InvalidRequestException(
                        "Fee value must be null or 0 when fee type is NONE"
                );
            }
        } else if ("AF".equalsIgnoreCase(feeTypeShtCd)) {
            if (feeValue == null || feeValue.compareTo(BigDecimal.ZERO) <= 0) {
                log.error("Fee value is required and must be greater than 0 for ANNUAL fee type");
                throw new InvalidRequestException(
                        "Fee value is required and must be greater than 0 for ANNUAL fee type"
                );
            }

            boolean feeValueExists = feeValuesRepository.existsByFeeValueAndFeeType(feeValue, "ANNUAL");

            if (!feeValueExists) {
                log.error("Fee value {} does not exist in database for ANNUAL fee type", feeValue);
                throw new InvalidRequestException(
                        "Fee value " + feeValue + " does not exist in the system for ANNUAL fee type"
                );
            }
        } else if ("MF".equalsIgnoreCase(feeTypeShtCd)) {
            if (feeValue == null || feeValue.compareTo(BigDecimal.ZERO) <= 0) {
                log.error("Fee value is required and must be greater than 0 for MONTHLY fee type");
                throw new InvalidRequestException(
                        "Fee value is required and must be greater than 0 for MONTHLY fee type"
                );
            }

            boolean feeValueExists = feeValuesRepository.existsByFeeValueAndFeeType(feeValue, "MONTHLY");

            if (!feeValueExists) {
                log.error("Fee value {} does not exist in database for MONTHLY fee type", feeValue);
                throw new InvalidRequestException(
                        "Fee value " + feeValue + " does not exist in the system for MONTHLY fee type"
                );
            }
        }

        log.info("Fee value validation passed");
    }

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

    private void verifyCashApr(BigDecimal purchaseAprMin, BigDecimal purchaseAprMax,
                               BigDecimal cashAprMin, BigDecimal cashAprMax) {
        log.info("Verifying Cash APR calculation");

        BigDecimal expectedCashAprMin = purchaseAprMin
                .add(CASH_APR_ADDITION)
                .setScale(2, RoundingMode.HALF_UP);

        if (cashAprMin.compareTo(expectedCashAprMin) != 0) {
            log.error("Cash APR Min is incorrect. Expected: {}, Received: {}",
                    expectedCashAprMin, cashAprMin);
            throw new InvalidRequestException(
                    String.format("Cash APR Min must be exactly %s (Purchase APR Min + 5%%), but received: %s",
                            expectedCashAprMin, cashAprMin)
            );
        }

        BigDecimal expectedCashAprMax = purchaseAprMax
                .add(CASH_APR_ADDITION)
                .setScale(2, RoundingMode.HALF_UP);

        if (cashAprMax.compareTo(expectedCashAprMax) != 0) {
            log.error("Cash APR Max is incorrect. Expected: {}, Received: {}",
                    expectedCashAprMax, cashAprMax);
            throw new InvalidRequestException(
                    String.format("Cash APR Max must be exactly %s (Purchase APR Max + 5%%), but received: %s",
                            expectedCashAprMax, cashAprMax)
            );
        }

        log.info("Cash APR verification passed");
    }

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
     * - If indicator = 'N', min and max must be null or 0
     */
    private void validateSecurityDeposit(String indicator, Integer min, Integer max) {
        log.info("Validating security deposit: indicator={}, min={}, max={}", indicator, min, max);

        if (indicator == null) {
            return;
        }

        if ("Y".equalsIgnoreCase(indicator)) {
            if (min == null || min == 0) {
                log.error("Security deposit min is null or 0 when indicator is Y");
                throw new InvalidRequestException(
                        "Security deposit min is required and must be greater than 0 when security deposit is enabled"
                );
            }

            if (max == null || max == 0) {
                log.error("Security deposit max is null or 0 when indicator is Y");
                throw new InvalidRequestException(
                        "Security deposit max is required and must be greater than 0 when security deposit is enabled"
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
            if (min != null && min != 0) {
                log.error("Security deposit min is {} when indicator is N (should be null or 0)", min);
                throw new InvalidRequestException(
                        "Security deposit min must be null or 0 when security deposit is not required"
                );
            }

            if (max != null && max != 0) {
                log.error("Security deposit max is {} when indicator is N (should be null or 0)", max);
                throw new InvalidRequestException(
                        "Security deposit max must be null or 0 when security deposit is not required"
                );
            }
        }
        log.info("Security deposit validation passed");
    }

    private void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Validating start date: {}, end date: {}", startDate, endDate);

        LocalDate today = LocalDate.now();
        LocalDate oneWeekFromToday = today.plusWeeks(1);
        LocalDate startDateOnly = startDate.toLocalDate();
        LocalDate endDateOnly = endDate.toLocalDate();

        if (startDateOnly.isBefore(oneWeekFromToday)) {
            log.error("Start date ({}) is before one week from now ({})", startDateOnly, oneWeekFromToday);
            throw new InvalidRequestException(
                    "Start date must be at least one week from today (" + oneWeekFromToday + ")"
            );
        }

        if (endDateOnly.isBefore(startDateOnly) || endDateOnly.isEqual(startDateOnly)) {
            log.error("End date ({}) is not after start date ({})", endDateOnly, startDateOnly);
            throw new InvalidRequestException(
                    "End date must be after start date (not equal)"
            );
        }

        log.info("Date validation passed");
    }

    public void validateApprover(String toBeApprovedBy) {
        log.info("Validating approver: {}", toBeApprovedBy);

        boolean approverExists = userRepository.existsByUsername(toBeApprovedBy);

        if (!approverExists) {
            log.error("Approver not found in user table: {}", toBeApprovedBy);
            throw new InvalidRequestException(
                    "Approver '" + toBeApprovedBy + "' does not exist in the system."
            );
        }

        log.info("Approver validation passed");
    }

    /**
     * Performs business rule validations for product date change.
     *
     * @param product existing product entity
     * @param newStart new start datetime
     * @param newEnd new end datetime
     */
    public void validateDateChange(Product product, LocalDateTime newStart, LocalDateTime newEnd) {

        LocalDateTime existingStart = product.getStartDate();
        LocalDateTime existingEnd = product.getEndDate();

        if (newStart.equals(existingStart) && newEnd.equals(existingEnd)) {
            throw new InvalidRequestException("No changes detected in start or end dates.");
        }

        if (newEnd.isBefore(newStart)) {
            throw new InvalidRequestException("End date and time cannot be before start date and time.");
        }

        if (newStart.isBefore(LocalDateTime.now())) {
            throw new InvalidRequestException("Start date cannot be in the past.");
        }
    }
}