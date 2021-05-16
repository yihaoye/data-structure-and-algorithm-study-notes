package com.example.simplebank.exception;

public class OperationException extends RuntimeException {

    public OperationException(String errorMessage) {
        super(errorMessage);
    }
}
