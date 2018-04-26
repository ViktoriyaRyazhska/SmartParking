package com.smartparking.security.utils.validation.validators.impls;

import com.smartparking.security.exception.FirstnameValidationEx;
import com.smartparking.security.exception.LastnameValidationEx;
import com.smartparking.security.utils.validation.validators.NameValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NameValidatorImpl implements NameValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(NameValidatorImpl.class);

    @Value("${validation.firstname.min}")
    private int firstnameMin;
    @Value("${validation.firstname.max}")
    private int firstnameMax;

    @Value("${validation.lastname.min}")
    private int lastnameMin;
    @Value("${validation.lastname.max}")
    private int lastnameMax;

    @Override
    public String validateFirstname(String firstname) throws FirstnameValidationEx {
        if (firstname == null || firstname.length() < firstnameMin || firstname.length() > firstnameMax) {
            LOGGER.warn("Invalid firstname " + firstname);
            throw new FirstnameValidationEx("Firstname is invalid");
        }
        return firstname;
    }

    @Override
    public String validateLastname(String lastname) throws LastnameValidationEx {
        if (lastname == null || lastname.length() < lastnameMin || lastname.length() > lastnameMax) {
            LOGGER.warn("Invalid lastname " + lastname);
            throw new LastnameValidationEx("Lastname is invlaid");
        }
        return lastname;
    }
}
