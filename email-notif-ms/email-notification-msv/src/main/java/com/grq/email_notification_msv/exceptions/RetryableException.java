package com.grq.email_notification_msv.exceptions;

public class RetryableException extends RuntimeException{

    public RetryableException(String message) {
        super(message);
    }

    public RetryableException(String message, Throwable cause) {
        super(message, cause);
    }

}
