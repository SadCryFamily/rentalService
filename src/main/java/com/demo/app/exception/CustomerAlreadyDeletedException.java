package com.demo.app.exception;

public class CustomerAlreadyDeletedException extends RuntimeException {

    public CustomerAlreadyDeletedException(String message) {
        super(message);
    }
}
