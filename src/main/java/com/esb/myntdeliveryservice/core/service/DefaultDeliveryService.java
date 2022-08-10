package com.esb.myntdeliveryservice.core.service;

import com.esb.myntdeliveryservice.config.DeliveryProperties;
import com.esb.myntdeliveryservice.core.constant.CustomError;
import com.esb.myntdeliveryservice.core.dto.CostOfDeliveryDto;
import com.esb.myntdeliveryservice.core.dto.DeliveryDto;
import com.esb.myntdeliveryservice.core.exception.ExceedDiscountOnCostException;
import com.esb.myntdeliveryservice.core.exception.RuleEngineException;
import com.esb.myntdeliveryservice.core.exception.VoucherExpiredException;
import com.esb.myntdeliveryservice.core.gateway.VoucherApiGateway;
import com.esb.myntdeliveryservice.core.service.costRuleEngine.*;
import com.esb.myntdeliveryservice.core.service.interfaces.DeliveryService;
import com.esb.myntdeliveryservice.infrastructure.voucherAPI.dto.VoucherApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DefaultDeliveryService implements DeliveryService {

    private final DeliveryProperties deliveryProperties;

    private final VoucherApiGateway voucherApiGateway;

    /**
     * Rules
     * Rejected: Weight > 50kg -> throws Exception
     * Heavy: Weight > 10kg and Weight <= 50kg -> PHP 20 * Weight (kg)
     * Small: Volume < 1500 cm3 and Weight <= 10kg -> PHP 0.03 * Volume
     * Medium: Volume < 2500 cm3 -> and Volume >= 1500 cm3 and Weight <= 10kg -> PHP 0.04 * Volume
     * Large: Volume >= 2500 cm3 and Weight <= 10kg -> PHP 0.05 * Volume
     */
    @Override
    public CostOfDeliveryDto calculateCostOfDelivery(DeliveryDto deliveryDto) {
        log.info("Calculate Delivery Cost: {}", deliveryDto );

        calculateDeliveryVolume(deliveryDto);
        BigDecimal parcelCost = validateAndCalculateCost(deliveryDto);
        BigDecimal discount = validateAndGetVoucher(deliveryDto.getVoucherCode());

        if (discount.compareTo(parcelCost) > 0) {
            log.error(CustomError.EXCEED_DISCOUNT_ON_COST_EXCEPTION.getMessage());
            throw new ExceedDiscountOnCostException(CustomError.EXCEED_DISCOUNT_ON_COST_EXCEPTION);
        }

        BigDecimal totalCostAmount = parcelCost.subtract(discount);

        CostOfDeliveryDto costOfDelivery = CostOfDeliveryDto.builder()
                        .costOfDelivery(totalCostAmount.setScale(2,RoundingMode.CEILING)).build();

        log.info("### Cost of Delivery: {}", costOfDelivery);
        return costOfDelivery;
    }

    private void calculateDeliveryVolume(DeliveryDto deliveryDto) {
        BigDecimal volume = deliveryDto.getHeight().multiply(deliveryDto.getWidth()).multiply(deliveryDto.getLength());
        deliveryDto.setVolume(volume.setScale(2, RoundingMode.CEILING));
    }

    private BigDecimal validateAndCalculateCost(DeliveryDto deliveryDto){
        final List<RuleEngine> ruleEngineList;
        log.info("### validateAndCalculateCost - weight: {}, volume: {}", deliveryDto.getWeight(), deliveryDto.getVolume());

        RuleEngine ruleEngine = getAllRules().stream()
                .filter(rules-> rules.isValid(deliveryDto))
                .findFirst().orElseThrow(() -> {
                    log.error("Error: {}", CustomError.RULE_ENGINE_EXCEPTION.getMessage());
                    throw new RuleEngineException(CustomError.RULE_ENGINE_EXCEPTION);
                });

        BigDecimal totalCost = ruleEngine.calculateCost(deliveryDto);

        return totalCost;
    }

    private List<RuleEngine> getAllRules() {
        log.info("### Retrieve list of rules to calculate delivery cost");
        List<RuleEngine> rulesList = Arrays.asList(
                new RejectedParcelRule(deliveryProperties),
                new HeavyParcelRule(deliveryProperties),
                new SmallParcelRule(deliveryProperties),
                new MediumParcelRule(deliveryProperties),
                new LargeParcelRule(deliveryProperties));
        return Collections.unmodifiableList(rulesList);
    }
    private BigDecimal validateAndGetVoucher(String voucherCode) {
        if (StringUtils.hasText(voucherCode)) {
            log.info("### Validate and get voucher code {}", voucherCode);
            VoucherApiResponse response = voucherApiGateway.getVoucher(voucherCode);
            validateVoucherExpiry(response);
            log.info("### Validate and get voucher result {}", response);
            return new BigDecimal(response.getDiscount());
        }
        return new BigDecimal(0);
    }

    private void validateVoucherExpiry(VoucherApiResponse response){
        if (response != null) {
            LocalDate voucherExpirydate = LocalDate.parse(response.getExpiry());
             if (isExpired(voucherExpirydate)){
                 throw new VoucherExpiredException(CustomError.VOUCHER_EXPIRED_EXCEPTION);
             }
        }
    }

    private boolean isExpired(LocalDate voucherExpirydate) {
        return LocalDate.now().isAfter(voucherExpirydate);
    }
}
