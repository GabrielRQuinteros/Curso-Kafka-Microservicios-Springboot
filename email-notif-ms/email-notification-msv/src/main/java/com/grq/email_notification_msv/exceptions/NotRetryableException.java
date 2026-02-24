package com.grq.email_notification_msv.exceptions;

public class NotRetryableException extends RuntimeException {

    public NotRetryableException(String message) {
        super(message);
    }

    public NotRetryableException(String message, Throwable cause) {
        super(message, cause);
    }
}
