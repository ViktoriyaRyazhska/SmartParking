package com.smartparking.service;

import com.smartparking.entity.PasswordConfirmation;
import com.smartparking.repository.PasswordConfirmationRepository;

public interface PasswordConfirmationService extends Service<PasswordConfirmation, Long, PasswordConfirmationRepository> {

    PasswordConfirmation findByUuid(String uuid);

    PasswordConfirmation makePasswordConfirmationEntity(String uuid, String newPassword);
}
