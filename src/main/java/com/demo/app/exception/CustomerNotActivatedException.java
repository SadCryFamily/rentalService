package com.demo.app.exception;

public class CustomerNotActivatedException extends RuntimeException{

    public CustomerNotActivatedException(String message) {
        super(message);
    }
}
