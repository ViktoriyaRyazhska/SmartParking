package com.smartparking.service.impl;

import com.smartparking.entity.PasswordConfirmation;
import com.smartparking.repository.PasswordConfirmationRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.PasswordConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class PasswordConfirmationServiceImpl extends AbstractService<PasswordConfirmation, Long, PasswordConfirmationRepository> implements PasswordConfirmationService {

    @Autowired
    private PasswordConfirmationRepository repository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    protected PasswordConfirmationServiceImpl(@Autowired PasswordConfirmationRepository repository) {
        super(repository);
    }

    @Override
    public PasswordConfirmation findByUuid(String uuid) {
        return repository.findByUuid(uuid);
    }

    @Override
    public PasswordConfirmation makePasswordConfirmationEntity(String uuid, String newPassword) {
        PasswordConfirmation passwordConfirmation = new PasswordConfirmation();
        passwordConfirmation.setUuid(uuid);
        passwordConfirmation.setTimeStamp(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        passwordConfirmation.setNewPassword(bcryptEncoder.encode(newPassword));
        return passwordConfirmation;
    }


}
