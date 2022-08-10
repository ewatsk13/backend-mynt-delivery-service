package com.esb.myntdeliveryservice.core.exception;

import com.esb.myntdeliveryservice.core.constant.CustomError;

public class RuleEngineException extends RuntimeException{

    private final String errCode;

    public RuleEngineException() {
        this.errCode = CustomError.RULE_ENGINE_EXCEPTION.getErrCode();
    }

    public String getErrCode() {
        return errCode;
    }

    public RuleEngineException(CustomError customError) {
        super(customError.getMessage());
        this.errCode = customError.getErrCode();
    }

    public RuleEngineException(CustomError customError, Throwable cause) {
        super(customError.getMessage(), cause);
        this.errCode = customError.getErrCode();
    }
}
