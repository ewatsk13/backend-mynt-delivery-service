package com.esb.myntdeliveryservice.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CostOfDeliveryDto {

    private BigDecimal costOfDelivery;
}
