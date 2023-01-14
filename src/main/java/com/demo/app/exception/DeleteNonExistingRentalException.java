package com.demo.app.exception;

public class DeleteNonExistingRentalException extends RuntimeException{

    public DeleteNonExistingRentalException(String message) {
        super(message);
    }
}
