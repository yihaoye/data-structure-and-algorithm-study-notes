package com.bank.exception;

public class DataAccessException extends RuntimeException {

    public DataAccessException(String errorMessage) {
        super(errorMessage);
    }
}
