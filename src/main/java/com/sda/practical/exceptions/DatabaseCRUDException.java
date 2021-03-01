package com.sda.practical.exceptions;

public class DatabaseCRUDException extends Exception {
    private String exceptionMessage;

    public DatabaseCRUDException(String message, Throwable cause, String exceptionMessage) {
        super(message, cause);
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
