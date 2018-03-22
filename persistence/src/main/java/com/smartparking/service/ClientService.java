package com.smartparking.service;

import com.smartparking.entity.Client;
import com.smartparking.entity.Role;
import com.smartparking.model.request.ClientRequest;
import com.smartparking.repository.ClientRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService extends Service<Client, Long, ClientRepository> {

    Client findOne(String email);

    List<Client> findClientsByAnyMatch(String input);

    List<Client> findLimitNumberOfClients(Pageable pageable);

    void updateFromRequest(Long id, ClientRequest clientRequest);
}
