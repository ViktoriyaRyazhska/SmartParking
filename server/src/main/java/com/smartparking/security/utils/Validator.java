package com.smartparking.security.utils;

import com.smartparking.exceptions.security.EmailValidationEx;
import com.smartparking.exceptions.security.FirstnameValidationEx;
import com.smartparking.exceptions.security.LastnameValidationEx;
import com.smartparking.exceptions.security.PasswordValidationEx;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public interface Validator {
    String validateEmail(String email) throws EmailValidationEx;
    String validatePassword(String password) throws PasswordValidationEx;
    String validateFirstname(String firstname) throws FirstnameValidationEx;
    String validateLastname(String lastname) throws LastnameValidationEx;
}
