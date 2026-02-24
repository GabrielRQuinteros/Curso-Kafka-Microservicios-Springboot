package com.grq.email_notification_msv.exceptions;

public class ProcessedEventStoringException extends RuntimeException{

    public ProcessedEventStoringException(String s, Exception e) {
        super(s, e);
    }
}
