package com.jpmc.salesreport.exception;

public class ResourcePathException extends RuntimeException{

    public ResourcePathException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public ResourcePathException(String errorMessage) {
        super(errorMessage);
    }
}
