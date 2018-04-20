package com.smartparking.service.impl;

import com.smartparking.entity.TemporaryDataConfirmation;
import com.smartparking.service.TemporaryDataConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class ExpirationCheckService {

    @Value("${expiration-time-password-change-confirmation}")
    Long passwordChangeExpiredTime;

    @Autowired
    private TemporaryDataConfirmationService temporaryDataConfirmationService;

    public Optional<TemporaryDataConfirmation> getTemporaryDataConfirmationWithExpirationChecking(String uuidFromUrl) {
        Optional<TemporaryDataConfirmation> uncheckedTemporaryDataConfirmation =
                Optional.of(temporaryDataConfirmationService.findByUuid(uuidFromUrl));
        if (LocalDateTime.now().toInstant(ZoneOffset.UTC).isAfter(
                uncheckedTemporaryDataConfirmation.get().getTimeStamp().plusSeconds(passwordChangeExpiredTime))) {
            temporaryDataConfirmationService.delete(uncheckedTemporaryDataConfirmation.get());
            return Optional.empty();
        }
        return uncheckedTemporaryDataConfirmation;
    }
}
