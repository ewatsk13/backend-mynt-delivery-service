package com.esb.myntdeliveryservice.presentation.rest;

import com.esb.myntdeliveryservice.core.exception.*;
import com.esb.myntdeliveryservice.presentation.dto.ErrorResource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionHandlingAdvice {

    @ExceptionHandler(ExceedWeightException.class)
    public ResponseEntity handleExceedWeightException(ExceedWeightException ex){
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getErrorResource(ex.getMessage(), ex.getErrCode()));
    }

    @ExceptionHandler(RuleEngineException.class)
    public ResponseEntity handleRuleEngineException(RuleEngineException ex){
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getErrorResource(ex.getMessage(), ex.getErrCode()));
    }

    @ExceptionHandler(VoucherAPIException.class)
    public ResponseEntity handleVoucherAPIException(VoucherAPIException ex){
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getErrorResource(ex.getMessage(), ex.getErrCode()));
    }

    @ExceptionHandler(InvalidVoucherException.class)
    public ResponseEntity handleVoucherAPIException(InvalidVoucherException ex){
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getErrorResource(ex.getMessage(), ex.getErrCode()));
    }

    @ExceptionHandler(ExceedDiscountOnCostException.class)
    public ResponseEntity handleVoucherAPIException(ExceedDiscountOnCostException ex){
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getErrorResource(ex.getMessage(), ex.getErrCode()));
    }

    private static ErrorResource getErrorResource(String message, String code) {
        return ErrorResource.builder()
                .code(code)
                .message(message)
                .build();
    }
}
