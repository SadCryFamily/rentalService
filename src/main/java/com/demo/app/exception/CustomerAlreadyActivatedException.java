package com.demo.app.exception;

public class CustomerAlreadyActivatedException extends RuntimeException {

    public CustomerAlreadyActivatedException(String message) {
        super(message);
    }
}
