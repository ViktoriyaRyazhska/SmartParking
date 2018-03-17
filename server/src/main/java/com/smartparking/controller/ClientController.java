package com.smartparking.controller;

import com.smartparking.dto.AddressDto;
import com.smartparking.dto.ClientDto;
import com.smartparking.dto.ProviderDto;
import com.smartparking.entity.Client;
import com.smartparking.entity.Role;
import com.smartparking.model.response.ClientDetailResponse;
import com.smartparking.model.response.ClientItemResponse;
import com.smartparking.model.response.ProviderDetailResponse;
import com.smartparking.model.response.ProviderItemResponse;
import com.smartparking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("clients")
    List<ClientItemResponse> findAll() {
        List<Client> clients = clientService.findAll();
        List<ClientItemResponse> clientItemResponses = new ArrayList<>();
        for (Client client : clients) {
            clientItemResponses.add(ClientItemResponse.of(client));
        }
        return clientItemResponses;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("clients/{id}")
    ClientDetailResponse find(@PathVariable Long id) {
        Client provider = clientService.findById(id);
        return ClientDetailResponse.of(provider);
    }

}
