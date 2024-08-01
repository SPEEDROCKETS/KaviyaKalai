package com.ford.ces.api.exception;

public class LaSequenceUpdateFailedException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private final String errorCode;

    public LaSequenceUpdateFailedException(String message, String errorCode) {
        super((message));
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return this.errorCode;
    }
}
