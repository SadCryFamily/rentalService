package com.demo.app.exception;

public class WrongUsernameOrCodeException extends RuntimeException {

    public WrongUsernameOrCodeException(String message) {
        super(message);
    }
}
