package com.smartparking.service;

import com.smartparking.entity.Client;
import com.smartparking.model.request.ClientRequest;
import com.smartparking.repository.ClientRepository;

public interface ClientService extends Service<Client, Long, ClientRepository> {
    void updateFromRequest(Long id, ClientRequest clientRequest);
}
