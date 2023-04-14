package com.demo.app.exception;

public class NonExistingAllRentalsException extends RuntimeException {

    public NonExistingAllRentalsException(String message) {
        super(message);
    }
}
