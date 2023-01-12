package com.demo.app.exception;

public class HaveNoRentalsException extends RuntimeException{

    public HaveNoRentalsException(String message) {
        super(message);
    }
}
