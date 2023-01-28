package com.demo.app.enums;

public enum ExceptionMessage {

    NOT_ACTIVATED("Customer is not confirmed account by activation code. Check mail for instructions"),

    NOT_AUTHORIZED("Cant reach resource while unauthorized"),

    ALREADY_EXISTED_CUSTOMER("Customer already exists by given email or username"),

    NULL_DTO_ACTIVATION("Activation entity cant be null"),

    NULL_CUSTOMER_CREATION("Customer cant be null"),

    NULL_CUSTOMER_UPDATE("Customer cant be null"),

    NULL_RENTAL_CREATION("Rental cant be null"),

    RENTAL_UNIQUE_CREATION("Rental already exists by given city and address"),

    ERROR_MAIL_MESSAGE("Cant sent message to invalid addressee or mail attachment is corrupted"),

    NO_MY_RENTALS("Have no any rental to show"),

    NON_EXISTED_CUSTOMER("Cant update data for non-existed customer"),

    NON_EXISTED_RENTAL("Can't delete someone else's rental"),

    DELETE_NON_EXISTED_CUSTOMER("Cant delete non-existed customer"),

    ALREADY_DELETED_CUSTOMER("Given customer already deleted"),

    LOCKED_CUSTOMER_ACCOUNT("Given account is locked. Create new or try to login again"),

    WRONG_ACTIVATION_DATA("Activation failed. Wrong username or activation code");

    private String exceptionMessage;

    ExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return this.exceptionMessage;
    }
}
