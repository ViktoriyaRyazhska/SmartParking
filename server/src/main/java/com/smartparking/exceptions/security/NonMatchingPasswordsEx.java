package com.smartparking.exceptions.security;

public class NonMatchingPasswordsEx extends AuthorizationEx {
    public NonMatchingPasswordsEx() {
        super();
    }
    public NonMatchingPasswordsEx(String message) {
        super(message);
    }
    public NonMatchingPasswordsEx(String message, Throwable cause) {
        super(message, cause);
    }
}
