package com.demo.app.enums;

public enum ExceptionMessage {

    NOT_ACTIVATED("Customer is not confirmed account by activation code. Check mail for instructions"),

    NOT_AUTHORIZED("Cant reach resource while unauthorized"),

    NULL_DTO_ACTIVATION("Activation entity cant be null"),

    NULL_CUSTOMER_CREATION("Customer cant be null"),

    NULL_RENTAL_CREATION("Rental cant be null"),

    RENTAL_UNIQUE_CREATION("Rental already exists by given city and address"),

    ERROR_MAIL_MESSAGE("Cant sent message to invalid addressee or mail attachment is corrupted"),

    NO_MY_RENTALS("Have no any rental to show"),

    WRONG_ACTIVATION_DATA("Activation failed. Wrong username or activation code");

    private String exceptionMessage;

    ExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return this.exceptionMessage;
    }
}
