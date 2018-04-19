package com.smartparking.service;

import com.smartparking.entity.TemporaryDataConfirmation;
import com.smartparking.repository.TemporaryDataConfirmationRepository;

public interface TemporaryDataConfirmationService extends Service<TemporaryDataConfirmation, Long, TemporaryDataConfirmationRepository> {

    TemporaryDataConfirmation findByUuid(String uuid);

    TemporaryDataConfirmation makePasswordConfirmationEntity(String uuid, String newPassword);

    TemporaryDataConfirmation makeRegistrationConfirmationEntity(String uuid, String userEmail);
}
