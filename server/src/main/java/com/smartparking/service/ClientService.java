package com.smartparking.service;

import com.smartparking.entity.Client;

public interface ClientService {
    Client updateClient(Client client);
    void deleteClient(Client client);
    void saveClient(Client client);
}
