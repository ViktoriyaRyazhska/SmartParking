package com.smartparking.exceptions.security;

public class SecurityEx extends Exception {
    public SecurityEx() {
        super();
    }
    public SecurityEx(String message) {
        super(message);
    }
    public SecurityEx(String message, Throwable cause) {
        super(message, cause);
    }
}
