package com.esb.myntdeliveryservice.core.gateway;

import com.esb.myntdeliveryservice.infrastructure.voucherAPI.dto.VoucherApiResponse;

public interface VoucherApiGateway {
    VoucherApiResponse getVoucher(String voucherCode);
}
