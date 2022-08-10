package com.esb.myntdeliveryservice.core.exception;

import com.esb.myntdeliveryservice.core.constant.CustomError;

public class ExceedWeightException extends RuntimeException{

    private final String errCode;

    public ExceedWeightException() {
        this.errCode = CustomError.EXCEED_WEIGHT_EXCEPTION.getErrCode();
    }

    public String getErrCode() {
        return errCode;
    }

    public ExceedWeightException(CustomError customError) {
        super(customError.getMessage());
        this.errCode = customError.getErrCode();
    }

    public ExceedWeightException(CustomError customError, Throwable cause) {
        super(customError.getMessage(), cause);
        this.errCode = customError.getErrCode();
    }
}
