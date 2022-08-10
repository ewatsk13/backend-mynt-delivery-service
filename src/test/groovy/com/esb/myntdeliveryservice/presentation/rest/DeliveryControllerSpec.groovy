package com.esb.myntdeliveryservice.presentation.rest

import com.esb.myntdeliveryservice.MyntDeliveryServiceApplication
import com.esb.myntdeliveryservice.core.dto.CostOfDeliveryDto
import com.esb.myntdeliveryservice.core.exception.InvalidVoucherException
import com.esb.myntdeliveryservice.core.exception.VoucherAPIException
import com.esb.myntdeliveryservice.core.gateway.VoucherApiGateway
import com.esb.myntdeliveryservice.infrastructure.voucherAPI.dto.VoucherApiResponse
import com.esb.myntdeliveryservice.presentation.dto.DeliveryRequest
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(classes = [MyntDeliveryServiceApplication], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@ActiveProfiles('test')
class DeliveryControllerSpec extends Specification{

    @Autowired
    TestRestTemplate restTemplate

    @SpringBean
    VoucherApiGateway voucherApiGateway = Mock()

    def DELIVERY_COST_URL = "/delivery/calculate-cost"

    @Unroll
    def 'Parcel #condition should process successfully' (){
        given:
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        def request = DeliveryRequest.builder()
                .weight(new BigDecimal(weight))
                .height(new BigDecimal(height))
                .width(new BigDecimal(width))
                .length(new BigDecimal(length))
                .voucherCode(voucherCode)
                .build()

        and:
        voucherApiGateway.getVoucher(voucherCode) >> {
            return VoucherApiResponse.builder()
            .code("MYNT")
            .discount("12.25")
            .expiry("2020-09-16")
            .build();
        }

        when:
        def requestEntity = new HttpEntity(request, headers)
        def response = restTemplate.exchange(
                DELIVERY_COST_URL,
                HttpMethod.POST,
                requestEntity,
                CostOfDeliveryDto.class
        )

        then:
        def cost = response.getBody().getCostOfDelivery();
        response.statusCode == httpStatus

        where:
        condition                                                   | weight    | height    | width | length    | voucherCode   | httpStatus
        "Rejected Parcel - Exceed exceed 50kg"                      | 51        | 10        | 15    | 10        | null          | HttpStatus.BAD_REQUEST
        "Heavy Parcel - Exceed exceed 10kg"                         | 15        | 10        | 15    | 10        | null          | HttpStatus.OK
        "Small Parcel - Volume less than 1500cm3"                   | 10        | 2.9       | 50    | 10        | null          | HttpStatus.OK
        "Medium Parcel -Volume less than 2500cm3"                   | 10        | 3.9       | 50    | 10        | null          | HttpStatus.OK
        "Large Parcel - Volume greater than equal to 2500cm3"       | 10        | 5.0       | 50    | 10        | null          | HttpStatus.OK
        "With Voucher Code - Parcel cost will be discounted"        | 10        | 5.0       | 50    | 10        | "MYNT"        | HttpStatus.OK
        "With Voucher Code - Discount is greater than parcel cost"  | 10        | 1.0       | 10    | 10        | "MYNT"        | HttpStatus.BAD_REQUEST

    }

    def 'Voucher is invalid should encountered an InvalidVoucherException' (){
        given:
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        def request = DeliveryRequest.builder()
                .weight(new BigDecimal(15))
                .height(new BigDecimal(10))
                .width(new BigDecimal(15))
                .length(new BigDecimal(10))
                .voucherCode("XXXX")
                .build()

        and:
        voucherApiGateway.getVoucher(_) >> { throw new InvalidVoucherException() }

        when:
        def requestEntity = new HttpEntity(request, headers)
        def response = restTemplate.exchange(
                DELIVERY_COST_URL,
                HttpMethod.POST,
                requestEntity,
                CostOfDeliveryDto.class
        )

        then:
        def cost = response.getBody().getCostOfDelivery()
        response.statusCode == HttpStatus.BAD_REQUEST

    }

    def 'Voucher API has an error should encountered an VoucherAPIException' (){
        given:
        def headers = new HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)
        def request = DeliveryRequest.builder()
                .weight(new BigDecimal(15))
                .height(new BigDecimal(10))
                .width(new BigDecimal(15))
                .length(new BigDecimal(10))
                .voucherCode("XXXX")
                .build()

        and:
        voucherApiGateway.getVoucher(_) >> { throw new VoucherAPIException() }

        when:
        def requestEntity = new HttpEntity(request, headers)
        def response = restTemplate.exchange(
                DELIVERY_COST_URL,
                HttpMethod.POST,
                requestEntity,
                CostOfDeliveryDto.class
        )

        then:
        def cost = response.getBody().getCostOfDelivery()
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR

    }
}

