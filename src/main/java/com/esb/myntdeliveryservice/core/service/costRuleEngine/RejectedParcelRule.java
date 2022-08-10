package com.esb.myntdeliveryservice.core.service.costRuleEngine;

import com.esb.myntdeliveryservice.config.DeliveryProperties;
import com.esb.myntdeliveryservice.core.constant.CustomError;
import com.esb.myntdeliveryservice.core.dto.DeliveryDto;
import com.esb.myntdeliveryservice.core.exception.ExceedWeightException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
public class RejectedParcelRule implements RuleEngine{

    private final DeliveryProperties deliveryProperties;

    @Override
    public boolean isValid(DeliveryDto deliveryDto) {
        log.info("Check Rule: Weight exceeds maximum weight of {}kg", deliveryProperties.getRejectParcelWeight());
        return deliveryDto.getWeight().compareTo(deliveryProperties.getRejectParcelWeight()) > 0;
    }

    @Override
    public BigDecimal calculateCost(DeliveryDto deliveryDto) {
        log.info("#### Weight exceed maximum weight: {}", deliveryDto.getWeight());
        throw new ExceedWeightException(CustomError.EXCEED_WEIGHT_EXCEPTION);
    }

}
