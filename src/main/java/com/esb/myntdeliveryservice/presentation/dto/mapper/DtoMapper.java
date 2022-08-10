package com.esb.myntdeliveryservice.presentation.dto.mapper;

import com.esb.myntdeliveryservice.core.dto.CostOfDeliveryDto;
import com.esb.myntdeliveryservice.core.dto.DeliveryDto;
import com.esb.myntdeliveryservice.presentation.dto.DeliveryRequest;

public class DtoMapper {

    public static DeliveryDto toDeliveryDto(DeliveryRequest deliveryRequest) {
        return DeliveryDto.builder()
                .weight(deliveryRequest.getWeight())
                .height(deliveryRequest.getHeight())
                .width(deliveryRequest.getWidth())
                .length(deliveryRequest.getLength())
                .voucherCode(deliveryRequest.getVoucherCode())
                .build();
    }

}
