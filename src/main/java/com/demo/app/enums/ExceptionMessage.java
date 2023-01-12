package com.demo.app.enums;

public enum ExceptionMessage {

    NOT_ACTIVATED("Customer is not confirmed account by activation code. Check mail for instructions"),
    NOT_AUTHORIZED("Cant reach resource while unauthorized"),

    NULL_DTO_ACTIVATION("Activation entity cant be null"),

    NULL_DTO_CREATION("Customer cant be null"),

    WRONG_ACTIVATION_DATA("Activation failed. Wrong username or activation code");

    private String exceptionMessage;

    ExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return this.exceptionMessage;
    }
}
