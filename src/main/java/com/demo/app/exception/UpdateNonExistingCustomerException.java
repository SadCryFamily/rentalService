package com.demo.app.exception;

public class UpdateNonExistingCustomerException extends RuntimeException {

    public UpdateNonExistingCustomerException(String message) {
        super(message);
    }
}
