package com.demo.app.exception;

public class DeleteNonExistingCustomerException extends RuntimeException {

    public DeleteNonExistingCustomerException(String message) {
        super(message);
    }
}
