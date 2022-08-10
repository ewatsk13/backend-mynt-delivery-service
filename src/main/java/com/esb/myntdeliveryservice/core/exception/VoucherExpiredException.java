package com.esb.myntdeliveryservice.core.exception;

import com.esb.myntdeliveryservice.core.constant.CustomError;

public class VoucherExpiredException extends RuntimeException{

    private final String errCode;

    public VoucherExpiredException() {
        this.errCode = CustomError.VOUCHER_EXPIRED_EXCEPTION.getErrCode();
    }

    public String getErrCode() {
        return errCode;
    }

    public VoucherExpiredException(CustomError customError) {
        super(customError.getMessage());
        this.errCode = customError.getErrCode();
    }

    public VoucherExpiredException(CustomError customError, Throwable cause) {
        super(customError.getMessage(), cause);
        this.errCode = customError.getErrCode();
    }
}