package com.sda.practical.exceptions;

public class DatabaseConnectionException extends Exception {
    private String exceptionMessage;

    public DatabaseConnectionException(String message, Throwable cause, String exceptionMessage) {
        super(message, cause);
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
