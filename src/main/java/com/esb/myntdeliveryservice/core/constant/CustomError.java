package com.esb.myntdeliveryservice.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum CustomError {

    EXCEED_WEIGHT_EXCEPTION("001400", "Delivery exceed weight"),
    RULE_ENGINE_EXCEPTION("001500", "Error encountered on rule engine"),
    VOUCHER_API_EXCEPTION("002500", "Error encountered on voucher API"),
    INVALID_VOUCHER_EXCEPTION("002400", "Invalid voucher code"),
    EXCEED_DISCOUNT_ON_COST_EXCEPTION("003400","Discount exceeds parcel cost");

    private String errCode;
    private String message;

}
