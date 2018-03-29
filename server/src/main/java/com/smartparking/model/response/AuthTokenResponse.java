package com.smartparking.model.response;

public class AuthTokenResponse {
    private String token;

    public AuthTokenResponse() {}

    public AuthTokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
