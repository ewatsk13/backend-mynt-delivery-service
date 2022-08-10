package com.esb.myntdeliveryservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Getter
@Setter
@Component
@ConfigurationProperties("delivery")
public class DeliveryProperties {

    private BigDecimal rejectParcelWeight;

    private BigDecimal heavyParcelWeight;

    private BigDecimal heavyParcelFee;

    private BigDecimal smallParcelVolume;

    private BigDecimal smallParcelFee;

    private BigDecimal mediumParcelVolume;

    private BigDecimal mediumParcelFee;

    private BigDecimal largeParcelFee;

}
