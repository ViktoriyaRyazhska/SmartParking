package com.smartparking.security.utils.validation.validators.impls;

import com.smartparking.entity.Client;
import com.smartparking.repository.ClientRepository;
import com.smartparking.security.exception.DuplicateEmailEx;
import com.smartparking.security.exception.EmailValidationEx;
import com.smartparking.security.exception.NonExistantEmailEx;
import com.smartparking.security.utils.validation.validators.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailValidatorImpl implements EmailValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailValidatorImpl.class);

    @Autowired
    private ClientRepository clientRepository;

    @Value("${validation.email.regex}")
    private String regex;
    @Value("${validation.email.min}")
    private int emailMin;
    @Value("${validation.email.max}")
    private int emailMax;

    @Override
    public String validateEmailOnRegistration(String email) throws EmailValidationEx, DuplicateEmailEx {
        if (email == null || !email.matches(regex) || email.length() < emailMin || email.length() > emailMax) {
            LOGGER.warn("Invalid email " + email);
            throw new EmailValidationEx("Entered e-mail adress is not valid");
        }
        Client client = clientRepository.findClientByEmail(email);
        if (client != null && client.getActivated()) {
            throw new DuplicateEmailEx("User with this e-mail alredy exists");
        }
        return email;
    }

    @Override
    public String validateEmailOnLogin(String email) throws EmailValidationEx, NonExistantEmailEx {
        if (email == null || !email.matches(regex) || email.length() < emailMin || email.length() > emailMax) {
            LOGGER.warn("Invalid email " + email);
            throw new EmailValidationEx("Entered e-mail adress is not valid");
        }
        if (clientRepository.findClientByEmail(email) == null) {
            throw new NonExistantEmailEx("User with this e-mail doesn`t exist");
        }
        return email;
    }
}
