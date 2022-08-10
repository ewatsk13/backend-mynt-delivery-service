package com.esb.myntdeliveryservice.infrastructure

import com.esb.myntdeliveryservice.core.exception.VoucherAPIException
import com.esb.myntdeliveryservice.core.gateway.VoucherApiGateway
import com.esb.myntdeliveryservice.infrastructure.voucherAPI.DefaultVoucherApiGateway
import com.esb.myntdeliveryservice.infrastructure.voucherAPI.dto.VoucherApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.mock.DetachedMockFactory

@DirtiesContext
@ActiveProfiles('test')
@ContextConfiguration(classes = [TestConfig])
class DefaultVoucherApiGatewaySpec extends Specification{

    @Autowired
    @Qualifier("voucherAPiRest")
    private RestTemplate restTemplate

    @Autowired
    private DefaultVoucherApiGateway defaultVoucherApiGateway

    def "Should i call voucher api with valid voucher code and return success status" () {
        given:
        def response =  VoucherApiResponse.builder()
                .code("MYNT")
                .discount("12.75")
                .expiry("2022-13-31")
                .build()

        restTemplate.exchange(*_) >> {
            new ResponseEntity<VoucherApiResponse>(response, HttpStatus.OK)
        }

        when:
        def result = defaultVoucherApiGateway.getVoucher("MYNT")

        then:
        result != null

    }

    @TestConfiguration
    static class TestConfig {

        def mockFactory = new DetachedMockFactory()

        @Bean("voucherAPiRest")
        RestTemplate restTemplate() {
            mockFactory.Mock(RestTemplate)
        }

        @Bean
        static VoucherApiGateway voucherApiGateway() {
            new DefaultVoucherApiGateway()
        }

    }
}
