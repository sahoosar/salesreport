package com.jpmc.salesreport.exception;

public class MessageOperationException extends RuntimeException {

    public MessageOperationException(String errorMessage) {
        super(errorMessage);
    }
}
