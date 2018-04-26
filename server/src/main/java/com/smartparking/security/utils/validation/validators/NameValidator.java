package com.smartparking.security.utils.validation.validators;

import com.smartparking.security.exception.FirstnameValidationEx;
import com.smartparking.security.exception.LastnameValidationEx;
import org.springframework.stereotype.Component;

@Component
public interface NameValidator {
    String validateFirstname(String firstname) throws FirstnameValidationEx;

    String validateLastname(String lastname) throws LastnameValidationEx;
}
