package com.esb.myntdeliveryservice.infrastructure.voucherAPI;

import com.esb.myntdeliveryservice.core.constant.CustomError;
import com.esb.myntdeliveryservice.core.exception.InvalidVoucherException;
import com.esb.myntdeliveryservice.core.exception.VoucherAPIException;
import com.esb.myntdeliveryservice.infrastructure.voucherAPI.dto.ErrorResponseWrapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@Slf4j
public class VoucherErrorHandler extends DefaultResponseErrorHandler {

    private static final String INVALID_CODE = "Invalid code";

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode() != HttpStatus.OK;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        log.info("### Deserialize of error response from voucher api - {}", response.getBody().toString());

        ErrorResponseWrapper body = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(response.getBody(), ErrorResponseWrapper.class);

        String errorMessage = body.getError();

        log.info("### Error Message from voucher response: {}", errorMessage);

        if (errorMessage.equals(INVALID_CODE)){
            log.error(CustomError.INVALID_VOUCHER_EXCEPTION.getMessage());
            throw new InvalidVoucherException(CustomError.INVALID_VOUCHER_EXCEPTION);
        } else {
            log.error(CustomError.INVALID_VOUCHER_EXCEPTION.getMessage());
            throw new VoucherAPIException(CustomError.VOUCHER_API_EXCEPTION);
        }
    }
}
