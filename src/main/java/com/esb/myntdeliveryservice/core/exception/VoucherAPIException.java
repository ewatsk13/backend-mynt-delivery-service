package com.esb.myntdeliveryservice.core.exception;

import com.esb.myntdeliveryservice.core.constant.CustomError;

public class VoucherAPIException extends RuntimeException{

    private final String errCode;

    public VoucherAPIException() {
        this.errCode = CustomError.VOUCHER_API_EXCEPTION.getErrCode();
    }

    public String getErrCode() {
        return errCode;
    }

    public VoucherAPIException(CustomError customError) {
        super(customError.getMessage());
        this.errCode = customError.getErrCode();
    }

    public VoucherAPIException(CustomError customError, Throwable cause) {
        super(customError.getMessage(), cause);
        this.errCode = customError.getErrCode();
    }
}
