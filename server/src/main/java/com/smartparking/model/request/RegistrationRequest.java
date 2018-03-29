package com.smartparking.model.request;

import com.smartparking.exceptions.security.*;

public class RegistrationRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String confirmPassword;

    public RegistrationRequest(){}

    public RegistrationRequest(String firstname, String lastname, String email, String password, String confirmPassword) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getFirstname(){
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname(){
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() throws PasswordValidationEx, NonMatchingPasswordsEx {
        if(this.password == null || this.confirmPassword == null){
            throw new PasswordValidationEx("Fields password and confirm password can`t be null");
        }
        if(!this.password.equals(this.confirmPassword)) {
            throw new NonMatchingPasswordsEx("Passwords doesn`t match");
        }
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
