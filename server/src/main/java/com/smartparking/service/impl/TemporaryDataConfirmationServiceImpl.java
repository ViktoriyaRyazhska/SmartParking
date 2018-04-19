package com.smartparking.service.impl;

import com.smartparking.entity.TemporaryDataConfirmation;
import com.smartparking.repository.TemporaryDataConfirmationRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.TemporaryDataConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TemporaryDataConfirmationServiceImpl
        extends AbstractService<TemporaryDataConfirmation, Long, TemporaryDataConfirmationRepository>
        implements TemporaryDataConfirmationService {

    @Autowired
    private TemporaryDataConfirmationRepository repository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    protected TemporaryDataConfirmationServiceImpl(@Autowired TemporaryDataConfirmationRepository repository) {
        super(repository);
    }

    @Override
    public TemporaryDataConfirmation findByUuid(String uuid) {
        return repository.findByUuid(uuid);
    }

    @Override
    public TemporaryDataConfirmation makePasswordConfirmationEntity(String uuid, String newPassword) {
        TemporaryDataConfirmation temporaryDataConfirmation = new TemporaryDataConfirmation();
        temporaryDataConfirmation.setUuid(uuid);
        temporaryDataConfirmation.setTimeStamp(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        temporaryDataConfirmation.setNewPassword(bcryptEncoder.encode(newPassword));
        return temporaryDataConfirmation;
    }

    @Override
    public TemporaryDataConfirmation makeRegistrationConfirmationEntity(String uuid, String userEmail) {
        TemporaryDataConfirmation temporaryDataConfirmation = new TemporaryDataConfirmation();
        temporaryDataConfirmation.setUuid(uuid);
        temporaryDataConfirmation.setUserEmail(userEmail);
        temporaryDataConfirmation.setTimeStamp(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        return temporaryDataConfirmation;
    }


}
