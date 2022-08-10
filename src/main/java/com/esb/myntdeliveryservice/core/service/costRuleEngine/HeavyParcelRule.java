package com.esb.myntdeliveryservice.core.service.costRuleEngine;

import com.esb.myntdeliveryservice.config.DeliveryProperties;
import com.esb.myntdeliveryservice.core.dto.DeliveryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
public class HeavyParcelRule implements RuleEngine{

    private final DeliveryProperties deliveryProperties;

    @Override
    public boolean isValid(DeliveryDto deliveryDto) {
        BigDecimal heavyParcelWeight = deliveryProperties.getHeavyParcelWeight();
        BigDecimal rejectedParcelWeight = deliveryProperties.getRejectParcelWeight();
        log.info("Check Rule: Weight exceeds heavy parcel weight of {}kg", heavyParcelWeight);
        return (deliveryDto.getWeight().compareTo(heavyParcelWeight) > 0 &&
                deliveryDto.getWeight().compareTo(rejectedParcelWeight) <= 0);
    }

    @Override
    public BigDecimal calculateCost(DeliveryDto deliveryDto) {
        log.info("#### Execute HeavyParcelRule for weight: {}", deliveryDto.getWeight());
        BigDecimal cost = deliveryDto.getWeight().multiply(deliveryProperties.getHeavyParcelFee());
        return cost;
    }
}
