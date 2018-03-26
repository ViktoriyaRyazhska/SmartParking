package com.smartparking.controller;

import com.smartparking.entity.Client;
import com.smartparking.entity.Provider;
import com.smartparking.model.request.ClientRequest;
import com.smartparking.model.response.*;
import com.smartparking.service.ClientService;
import com.smartparking.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    ProviderService providerService;

    @GetMapping("clients")
    List<ClientItemResponse> getAllClients() {
        List<Client> clients = clientService.findAll();
        List<ClientItemResponse> clientItemResponses = new ArrayList<>();
        for (Client client : clients) {
            clientItemResponses.add(ClientItemResponse.of(client));
        }
        return clientItemResponses;
    }


    @GetMapping("clients/clientslimit")
    List<ClientItemResponse> getLimitNumberOfClients() {
        List<Client> clients = clientService.findLimitNumberOfClients(new PageRequest(0, 50));
        List<ClientItemResponse> clientItemResponses = new ArrayList<>();
        for (Client client : clients) {
            clientItemResponses.add(ClientItemResponse.of(client));
        }
        return clientItemResponses;
    }


    @GetMapping("/clients/{id}")
    ClientDetailResponse getClientDetails(@PathVariable Long id) {
        Client client = clientService.findById(id);
        return ClientDetailResponse.of(client);
    }

    @PostMapping("/clients/update/{id}")
    ResponseEntity updateClient(@PathVariable Long id, @RequestBody ClientRequest clientRequest) {
        if (!clientRequest.getFirstName().equals("") && !clientRequest.getLastName().equals("") &&
                !clientRequest.getEmail().equals("")) {
            clientService.updateFromRequest(id, clientRequest);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Empty data input.", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("clients/findprovider/{id}")
    ProviderDetailResponse getProviderById(@PathVariable Long id) {
        Provider provider = providerService.findProviderByClientId(id);
        return ProviderDetailResponse.of(provider);
    }

    @GetMapping("clients/findclients/{input}")
    List<ClientItemResponse> getClientsByAnyMatch(@PathVariable String input) {
        if (input != "") {
            List<Client> clients = clientService.findClientsByAnyMatch(input);
            List<ClientItemResponse> clientItemResponses = new ArrayList<>();
            for (Client client : clients) {
                clientItemResponses.add(ClientItemResponse.of(client));
            }
            return clientItemResponses;
        } else return getAllClients();
    }

    @GetMapping("clients/findbyrole/{input}")
    List<ClientItemResponse> getClientsByRole(@PathVariable String input) {
        List<Client> clients = clientService.findClientsByRole(input);
        List<ClientItemResponse> clientItemResponses = new ArrayList<>();
        for (Client client : clients) {
            clientItemResponses.add(ClientItemResponse.of(client));
        }
        return clientItemResponses;
    }

    @GetMapping("clients/getproviders")
    List<ProviderItemResponse> getAllProviders() {
        List<Provider> providers = providerService.findAll();
        List<ProviderItemResponse> providerItemResponses = new ArrayList<>();
        for (Provider provider : providers) {
            providerItemResponses.add(ProviderItemResponse.of(provider));
        }
        return providerItemResponses;
    }
}
