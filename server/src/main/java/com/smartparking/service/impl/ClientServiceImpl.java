package com.smartparking.service.impl;

import com.smartparking.entity.Client;
import com.smartparking.repository.ClientRepository;
import com.smartparking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public void deleteClient(Client client) {
        clientRepository.delete(client);
    }

    @Override
    @Transactional
    public void saveClient(Client client) {
        clientRepository.save(client);
    }
}
