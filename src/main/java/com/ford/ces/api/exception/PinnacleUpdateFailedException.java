package com.ford.ces.api.exception;

public class PinnacleUpdateFailedException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final String errorCode;

    public PinnacleUpdateFailedException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return this.errorCode;
    }
}