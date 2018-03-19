package com.smartparking.service;

import com.smartparking.entity.Client;
import com.smartparking.repository.ClientRepository;

public interface ClientService extends Service<Client, Long, ClientRepository> {
    Client findOne(String email);
}
