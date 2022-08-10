package com.esb.myntdeliveryservice.config;

import com.esb.myntdeliveryservice.core.gateway.VoucherApiGateway;
import com.esb.myntdeliveryservice.core.service.DefaultDeliveryService;
import com.esb.myntdeliveryservice.core.service.interfaces.DeliveryService;
import com.esb.myntdeliveryservice.infrastructure.voucherAPI.VoucherErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean("voucherAPiRest")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new VoucherErrorHandler());
        return restTemplate;
    }

    @Bean
    DeliveryService deliveryService(DeliveryProperties deliveryProperties, VoucherApiGateway voucherApiGateway) {
        return new DefaultDeliveryService(deliveryProperties, voucherApiGateway);
    }

}
