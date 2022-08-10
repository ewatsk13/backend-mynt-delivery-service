package com.esb.myntdeliveryservice.core.exception;

import com.esb.myntdeliveryservice.core.constant.CustomError;

public class InvalidVoucherException extends RuntimeException{

    private final String errCode;

    public InvalidVoucherException() {
        this.errCode = CustomError.INVALID_VOUCHER_EXCEPTION.getErrCode();
    }

    public String getErrCode() {
        return errCode;
    }

    public InvalidVoucherException(CustomError customError) {
        super(customError.getMessage());
        this.errCode = customError.getErrCode();
    }

    public InvalidVoucherException(CustomError customError, Throwable cause) {
        super(customError.getMessage(), cause);
        this.errCode = customError.getErrCode();
    }
}
