package com.jpmc.salesreport.exception;

public class ParseAndProcessMessageException extends RuntimeException {
    public ParseAndProcessMessageException(String errorMessage) {
        super(errorMessage);
    }
}
