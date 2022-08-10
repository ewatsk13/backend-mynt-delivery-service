package com.esb.myntdeliveryservice.core.exception;

import com.esb.myntdeliveryservice.core.constant.CustomError;

public class ExceedDiscountOnCostException extends RuntimeException {

    private final String errCode;

    public ExceedDiscountOnCostException() {
        this.errCode = CustomError.EXCEED_DISCOUNT_ON_COST_EXCEPTION.getErrCode();
    }

    public String getErrCode() {
        return errCode;
    }

    public ExceedDiscountOnCostException(CustomError customError) {
        super(customError.getMessage());
        this.errCode = customError.getErrCode();
    }

    public ExceedDiscountOnCostException(CustomError customError, Throwable cause) {
        super(customError.getMessage(), cause);
        this.errCode = customError.getErrCode();
    }
}
