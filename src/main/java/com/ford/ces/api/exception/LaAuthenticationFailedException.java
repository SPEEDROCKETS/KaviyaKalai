package com.ford.ces.api.exception;

public class LaAuthenticationFailedException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private final String errorCode;

    public LaAuthenticationFailedException(String message, String errorCode) {
        super((message));
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return this.errorCode;
    }
}
