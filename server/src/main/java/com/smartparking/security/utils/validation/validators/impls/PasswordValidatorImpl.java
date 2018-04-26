package com.smartparking.security.utils.validation.validators.impls;

import com.smartparking.security.exception.NonMatchingPasswordsEx;
import com.smartparking.security.exception.PasswordValidationEx;
import com.smartparking.security.utils.validation.validators.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidatorImpl implements PasswordValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordValidatorImpl.class);

    @Value("${validation.password.min}")
    private int passwordMin;
    @Value("${validation.password.max}")
    private int passwordMax;

    @Override
    public String validatePassword(String password) throws PasswordValidationEx {
        if (password == null || password.length() < passwordMin || password.length() > passwordMax) {
            LOGGER.warn("Invalid password");
            throw new PasswordValidationEx("Password is invalid");
        }
        return password;
    }

    @Override
    public String checkPasswords(String password, String confirmPassword) throws NonMatchingPasswordsEx, PasswordValidationEx {
        if (!validatePassword(password).equals(validatePassword(confirmPassword))) {
            LOGGER.warn("Passwords doesnt match " + password + " " + confirmPassword);
            throw new NonMatchingPasswordsEx("Passwords doesn`t match");
        }
        return password;
    }
}
