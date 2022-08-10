package com.esb.myntdeliveryservice.core.service.interfaces;

import com.esb.myntdeliveryservice.core.dto.CostOfDeliveryDto;
import com.esb.myntdeliveryservice.core.dto.DeliveryDto;

public interface DeliveryService {

    CostOfDeliveryDto calculateCostOfDelivery(DeliveryDto deliveryDto);

}
