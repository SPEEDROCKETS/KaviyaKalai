package com.ford.ces.api.exception;

public class AffiliateAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final String errorCode;

    public AffiliateAlreadyExistException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return this.errorCode;
    }
}