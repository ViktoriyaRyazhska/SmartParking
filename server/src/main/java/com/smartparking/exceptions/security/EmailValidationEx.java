package com.smartparking.exceptions.security;

public class EmailValidationEx extends AuthorizationEx {
    public EmailValidationEx() {
        super();
    }
    public EmailValidationEx(String message) {
        super(message);
    }
    public EmailValidationEx(String message, Throwable cause) {
        super(message, cause);
    }
}
