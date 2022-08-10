package com.esb.myntdeliveryservice.core.service.costRuleEngine;

import com.esb.myntdeliveryservice.core.dto.DeliveryDto;

import java.math.BigDecimal;

public interface RuleEngine {

    boolean isValid(DeliveryDto deliveryDto);

    BigDecimal calculateCost(DeliveryDto deliveryDto);
}
