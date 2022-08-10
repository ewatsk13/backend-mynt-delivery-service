package com.esb.myntdeliveryservice.infrastructure.voucherAPI;

import com.esb.myntdeliveryservice.core.constant.CustomError;
import com.esb.myntdeliveryservice.core.exception.VoucherAPIException;
import com.esb.myntdeliveryservice.core.gateway.VoucherApiGateway;
import com.esb.myntdeliveryservice.infrastructure.voucherAPI.dto.VoucherApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class DefaultVoucherApiGateway implements VoucherApiGateway {

    @Autowired
    @Qualifier("voucherAPiRest")
    private RestTemplate restTemplate;

    @Value("${delivery.voucher.url}")
    private String voucherApiURL;

    @Value("${delivery.voucher.key}")
    private String apiKey;

    @Override
    public VoucherApiResponse getVoucher(String voucherCode) {
        try {
            log.info("### Getting voucher for {}", voucherCode);
            ResponseEntity<VoucherApiResponse> response = restTemplate.exchange(
                    voucherApiURL + "/" + voucherCode + "?key=" + apiKey,
                    HttpMethod.GET,
                    new HttpEntity<>(getHeaders()),
                    new ParameterizedTypeReference<VoucherApiResponse>() {
                    }
            );
            log.info("### Response: {}", response);
            return response.getBody();
        }catch (ResourceAccessException ex) {
            log.error(CustomError.INVALID_VOUCHER_EXCEPTION.getMessage());
            throw new VoucherAPIException(CustomError.VOUCHER_API_EXCEPTION);
        }
    }

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }
}
