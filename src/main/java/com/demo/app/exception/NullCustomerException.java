package com.demo.app.exception;

public class NullCustomerException extends RuntimeException {

    public NullCustomerException(String message) {
        super(message);
    }
}
