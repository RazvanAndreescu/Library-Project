package com.sda.practical.exceptions;

public class UnknownException extends Exception {
    private String exceptionMessage;

    public UnknownException(String message, Throwable cause, String exceptionMessage) {
        super(message, cause);
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
