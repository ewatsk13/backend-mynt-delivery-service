package com.esb.myntdeliveryservice.core.service.costRuleEngine;

import com.esb.myntdeliveryservice.config.DeliveryProperties;
import com.esb.myntdeliveryservice.core.dto.DeliveryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
public class SmallParcelRule implements RuleEngine{

    private final DeliveryProperties deliveryProperties;

    @Override
    public boolean isValid(DeliveryDto deliveryDto) {
        BigDecimal heavyParcelWeight = deliveryProperties.getHeavyParcelWeight();
        BigDecimal smallParcelVolume = deliveryProperties.getSmallParcelVolume();
        log.info("Check Rule: Weight exceeds heavy parcel weight of {}kg and volume less than {}cm3", heavyParcelWeight, smallParcelVolume);
        return (deliveryDto.getWeight().compareTo(heavyParcelWeight) <= 0 &&
                deliveryDto.getVolume().compareTo(smallParcelVolume) < 0);
    }

    @Override
    public BigDecimal calculateCost(DeliveryDto deliveryDto) {
        log.info("#### Execute SmallParcel for volume: {}", deliveryDto.getVolume());
        BigDecimal cost = deliveryDto.getVolume().multiply(deliveryProperties.getSmallParcelFee());
        return cost;
    }

}
