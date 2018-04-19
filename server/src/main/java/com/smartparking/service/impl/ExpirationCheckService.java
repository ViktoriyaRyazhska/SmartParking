package com.smartparking.service.impl;

import com.smartparking.entity.TemporaryDataConfirmation;
import com.smartparking.service.TemporaryDataConfirmationService;
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
    private TemporaryDataConfirmationService temporaryDataConfirmationService;

    public TemporaryDataConfirmation getTemporaryDataConfirmationWithExpirationChecking(String uuidFromUrl) {
        TemporaryDataConfirmation uncheckedTemporaryDataConfirmation = temporaryDataConfirmationService.findByUuid(uuidFromUrl);
        if (LocalDateTime.now().toInstant(ZoneOffset.UTC).isAfter(
                uncheckedTemporaryDataConfirmation.getTimeStamp().plusSeconds(passwordChangeExpiredTime))) {
            temporaryDataConfirmationService.delete(uncheckedTemporaryDataConfirmation);
            return null;
        }
        return uncheckedTemporaryDataConfirmation;
    }
}
