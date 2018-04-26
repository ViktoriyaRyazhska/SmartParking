package com.smartparking.security.utils.validation.validators;

import com.smartparking.security.exception.DuplicateEmailEx;
import com.smartparking.security.exception.EmailValidationEx;
import com.smartparking.security.exception.NonExistantEmailEx;
import org.springframework.stereotype.Component;

@Component
public interface EmailValidator {
    String validateEmailOnRegistration(String email) throws EmailValidationEx, DuplicateEmailEx;

    String validateEmailOnLogin(String email) throws EmailValidationEx, NonExistantEmailEx;
}
