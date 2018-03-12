package com.smartparking.service.impl;

import com.smartparking.entity.Client;
import com.smartparking.service.ClientService;

import javax.transaction.Transactional;

public class ClientServiceImpl implements ClientService {
    @Override
    @Transactional
    public Client updateClient(Client client) {
        return null;
    }

    @Override
    @Transactional
    public void deleteClient(Client client) {

    }

    @Override
    @Transactional
    public void saveClient(Client client) {

    }
}
