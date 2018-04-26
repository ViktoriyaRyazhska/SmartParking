package com.smartparking.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRequest {
    private String email;
    private String password;
    private String confirmPassword;

    public PasswordRequest() {
    }

    public PasswordRequest(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public PasswordRequest (String password, String confirmPassword, String email){
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
    }

}
