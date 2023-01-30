package com.demo.app.exception;

public class ImageFileNotFoundException extends RuntimeException {

    public ImageFileNotFoundException(String message) {
        super(message);
    }
}
