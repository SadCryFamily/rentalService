package com.demo.app.exception;

public class WrongActivationCodeException extends RuntimeException {

    public WrongActivationCodeException(String message) {
        super(message);
    }
}
