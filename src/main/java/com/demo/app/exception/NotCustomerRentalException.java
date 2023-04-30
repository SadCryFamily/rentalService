package com.demo.app.exception;

public class NotCustomerRentalException extends RuntimeException {

    public NotCustomerRentalException(String message) {
        super(message);
    }
}
