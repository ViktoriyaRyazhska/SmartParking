package com.smartparking.exceptions.security;

public class AuthorizationEx extends SecurityEx {
    public AuthorizationEx() {
        super();
    }
    public AuthorizationEx(String message) {
        super(message);
    }
    public AuthorizationEx(String message, Throwable cause) {
        super(message, cause);
    }

}
