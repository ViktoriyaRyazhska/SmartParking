package com.smartparking.security.utils.validation;

import com.smartparking.security.exception.*;
import com.smartparking.security.utils.validation.validators.EmailValidator;
import com.smartparking.security.utils.validation.validators.NameValidator;
import com.smartparking.security.utils.validation.validators.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtilImpl implements ValidationUtil {
    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private NameValidator nameValidator;

    @Autowired
    private PasswordValidator passwordValidator;

    @Override
    public String validateEmailOnRegistration(String email) throws EmailValidationEx, DuplicateEmailEx {
        return emailValidator.validateEmailOnRegistration(email);
    }

    @Override
    public String validateEmailOnLogin(String email) throws EmailValidationEx, NonExistantEmailEx {
        return emailValidator.validateEmailOnLogin(email);
    }

    @Override
    public String validatePassword(String password) throws PasswordValidationEx {
        return passwordValidator.validatePassword(password);
    }

    @Override
    public String checkPasswords(String password, String confirmPassword) throws NonMatchingPasswordsEx, PasswordValidationEx {
        return passwordValidator.checkPasswords(password, confirmPassword);
    }

    @Override
    public String validateFirstname(String firstname) throws FirstnameValidationEx {
        return nameValidator.validateFirstname(firstname);
    }

    @Override
    public String validateLastname(String lastname) throws LastnameValidationEx {
        return nameValidator.validateLastname(lastname);
    }


}
