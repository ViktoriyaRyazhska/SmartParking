package com.smartparking.exceptions.security;

public class DuplicateEmailEx extends AuthorizationEx{
    public DuplicateEmailEx() {
        super();
    }
    public DuplicateEmailEx(String message) {
        super(message);
    }
    public DuplicateEmailEx(String message, Throwable cause) {
        super(message, cause);
    }
}
