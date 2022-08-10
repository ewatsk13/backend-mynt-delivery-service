package com.esb.myntdeliveryservice.config;

import com.esb.myntdeliveryservice.core.gateway.VoucherApiGateway;
import com.esb.myntdeliveryservice.infrastructure.voucherAPI.DefaultVoucherApiGateway;
import com.esb.myntdeliveryservice.infrastructure.voucherAPI.dto.VoucherApiResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class VoucherApiGatewayConfig {

    @ConditionalOnProperty(name = "voucher.connection", havingValue = "gateway", matchIfMissing = true)
    static class VoucherApiGatewayConfiguration {

        @Primary
        @Bean
        VoucherApiGateway voucherApiGateway() {
            return new DefaultVoucherApiGateway();
        }
    }

    @Bean
    @ConditionalOnProperty(name = "voucher.connection", havingValue = "fake")
    public VoucherApiGateway fakeVoucherApiGateway() {
        return voucherCode -> VoucherApiResponse.builder()
                .code("MYNT")
                .discount("20.00")
                .expiry("2022-12-31")
                .build();
    }
}
