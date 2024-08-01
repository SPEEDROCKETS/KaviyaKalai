package com.ford.ces.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



public class LAServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String errorCode;

    public LAServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return this.errorCode;
    }
}
