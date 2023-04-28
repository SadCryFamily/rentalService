package com.demo.app.exception;

public class ExpiredActivationCodeException extends RuntimeException {

    public ExpiredActivationCodeException(String message) {
        super(message);
    }
}
