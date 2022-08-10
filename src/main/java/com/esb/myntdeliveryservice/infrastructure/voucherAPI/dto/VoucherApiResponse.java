package com.esb.myntdeliveryservice.infrastructure.voucherAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VoucherApiResponse {

    private String code;

    private String discount;

    private String expiry;
}
