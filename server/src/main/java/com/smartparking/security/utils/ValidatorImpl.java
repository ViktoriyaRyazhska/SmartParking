package com.smartparking.security.utils;

import com.smartparking.exceptions.security.EmailValidationEx;
import com.smartparking.exceptions.security.FirstnameValidationEx;
import com.smartparking.exceptions.security.LastnameValidationEx;
import com.smartparking.exceptions.security.PasswordValidationEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ValidatorImpl implements Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidatorImpl.class);

    @Value("${auth.email.regex}")
    private String regex;
    @Value("${auth.email_min}")
    private int emailMin;
    @Value("${auth.email_max}")
    private int emailMax;

    @Value("${auth.password_min}")
    private int passwordMin;
    @Value("${auth.password_max}")
    private int passwordMax;

    @Value("${auth.firstname_min}")
    private int firstnameMin;
    @Value("${auth.firstname_max}")
    private int firstnameMax;

    @Value("${auth.lastname_min}")
    private int lastnameMin;
    @Value("${auth.lastname_max}")
    private int lastnameMax;

    @Override
    public String validateEmail(String email) throws EmailValidationEx {
        if(email == null || !email.matches(regex) || email.length() < emailMin || email.length() > emailMax) {
            LOGGER.warn("Invalid email");
            throw new EmailValidationEx();
        }
        return email;
    }

    @Override
    public String validatePassword(String password) throws PasswordValidationEx {
        if(password == null || password.length() < passwordMin || password.length() > passwordMax){
            LOGGER.warn("Invalid password");
            throw new PasswordValidationEx();
        }
        return password;
    }

    @Override
    public String validateFirstname(String firstname) throws FirstnameValidationEx {
        if(firstname == null || firstname.length() < firstnameMin || firstname.length() > firstnameMax){
            LOGGER.warn("Invalid firstname");
            throw new FirstnameValidationEx();
        }
        return firstname;
    }

    @Override
    public String validateLastname(String lastname) throws LastnameValidationEx {
        if(lastname == null || lastname.length() < lastnameMin || lastname.length() > lastnameMax){
            LOGGER.warn("Invalid lastname");
            throw new LastnameValidationEx();
        }
        return lastname;
    }
}