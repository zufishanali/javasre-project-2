package com.project2.restaurantApi.controllers.exceptions;

public class InvalidStatusArgumentException extends RuntimeException {
    public InvalidStatusArgumentException() {
        super();
    }

    public InvalidStatusArgumentException(String message) {
        super(message);
    }

    public InvalidStatusArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidStatusArgumentException(Throwable cause) {
        super(cause);
    }

    protected InvalidStatusArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
