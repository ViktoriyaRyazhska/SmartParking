package com.smartparking.security.utils.validation.validators;

import com.smartparking.security.exception.NonMatchingPasswordsEx;
import com.smartparking.security.exception.PasswordValidationEx;
import org.springframework.stereotype.Component;

@Component
public interface PasswordValidator {
    String validatePassword(String password) throws PasswordValidationEx;

    String checkPasswords(String password, String confirmPassword) throws NonMatchingPasswordsEx, PasswordValidationEx;
}
