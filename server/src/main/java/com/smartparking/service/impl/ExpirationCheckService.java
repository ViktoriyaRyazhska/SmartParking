package com.smartparking.service.impl;

import com.smartparking.entity.PasswordConfirmation;
import com.smartparking.service.PasswordConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class ExpirationCheckService {

    @Value("${expiration-time-password-change-confirmation}")
    Long passwordChangeExpiredTime;

    @Autowired
    private PasswordConfirmationService passwordConfirmationService;

    public PasswordConfirmation getPasswordConfirmationWithExpirationChecking(String uuidFromUrl) {
        PasswordConfirmation uncheckedPasswordConfirmation = passwordConfirmationService.findByUuid(uuidFromUrl);
        if (LocalDateTime.now().toInstant(ZoneOffset.UTC).isAfter(
                uncheckedPasswordConfirmation.getTimeStamp().plusSeconds(passwordChangeExpiredTime))) {
            passwordConfirmationService.delete(uncheckedPasswordConfirmation);
            return null;
        }
        return uncheckedPasswordConfirmation;
    }
}
