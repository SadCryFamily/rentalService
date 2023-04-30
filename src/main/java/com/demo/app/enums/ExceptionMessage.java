package com.demo.app.enums;

public enum ExceptionMessage {

    NOT_ACTIVATED("Customer is not confirmed account by activation code. Check mail for instructions"),

    NOT_AUTHORIZED("Cant reach resource while unauthorized"),

    NULL_DTO_ACTIVATION("Activation entity cant be null"),

    NULL_CUSTOMER_CREATION("Customer cant be null"),

    NULL_CUSTOMER_UPDATE("Customer cant be null"),

    NULL_RENTAL_CREATION("Rental cant be null"),

    NO_ALL_RENTALS("No one active rentals available"),

    RETRIEVE_NULL_RENTAL("Cant retrieve non-existed rental"),

    NOT_CUSTOMER_RENTAL("Cant delete someone's rental"),

    RENTAL_UNIQUE_CREATION("Rental already exists by given city and address"),

    ERROR_MAIL_MESSAGE("Cant sent message to invalid addressee or mail attachment is corrupted"),

    IMAGE_FILE_NOT_FOUND("Cant find an additional email message file"),

    NO_MY_RENTALS("Have no any rental to show"),

    NON_EXISTED_CUSTOMER("Cant update data for non-existed customer"),

    RETRIEVE_NON_EXISTED_CUSTOMER("Cant get data for non-existed customer"),

    NON_EXISTED_RENTAL("Can't delete someone else's rental"),

    DELETE_NON_EXISTED_CUSTOMER("Cant delete non-existed customer"),

    ALREADY_DELETED_CUSTOMER("Given customer already deleted"),

    ALREADY_ACTIVATED_CUSTOMER("Customer already activated by given email"),

    ALREADY_EXIST_CUSTOMER("Customer with username and email already exists"),

    LOCKED_CUSTOMER_ACCOUNT("Your account has been locked. Please contact customer support for further assistance."),

    WRONG_ACTIVATION_DATA("Activation failed. Check activation code or given customer already activated"),

    WRONG_ACTIVATION_CODE("Invalid activation code. Please make sure you have entered the correct activation code and try again."),

    EXPIRED_ACTIVATION_CODE("The activation code has expired. Please request a new activation code to activate your account");

    private String exceptionMessage;

    ExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return this.exceptionMessage;
    }
}
