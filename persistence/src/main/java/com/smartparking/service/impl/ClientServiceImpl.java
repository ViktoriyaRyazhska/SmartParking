package com.smartparking.service.impl;

import com.smartparking.entity.Client;
import com.smartparking.repository.ClientRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl extends AbstractService<Client, Long, ClientRepository> implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    protected ClientServiceImpl(@Autowired ClientRepository repository) {
        super(repository);
    }

    @Override
    public Client findOne(String email) {
        return clientRepository.findClientByEmail(email).get();
    }
}
