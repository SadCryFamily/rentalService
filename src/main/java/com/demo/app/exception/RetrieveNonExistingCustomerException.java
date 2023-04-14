package com.demo.app.exception;

public class RetrieveNonExistingCustomerException extends RuntimeException {

    public RetrieveNonExistingCustomerException(String message) {
        super(message);
    }
}
