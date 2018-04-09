package com.smartparking.controller;

import com.smartparking.entity.Client;
import com.smartparking.entity.Provider;
import com.smartparking.model.request.ClientRequest;
import com.smartparking.model.response.ClientDetailResponse;
import com.smartparking.model.response.ClientItemResponse;
import com.smartparking.model.response.ProviderDetailResponse;
import com.smartparking.model.response.ProviderItemResponse;
import com.smartparking.service.ClientService;
import com.smartparking.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    ProviderService providerService;

    @GetMapping("")
    ResponseEntity<List<ClientItemResponse>> getAllClients() {
        List<Client> clients = clientService.findAll();
        List<ClientItemResponse> clientItemResponses = new ArrayList<>();
        clients.forEach(client -> clientItemResponses.add(ClientItemResponse.of(client)));
        return new ResponseEntity<>(clientItemResponses, HttpStatus.OK);
    }

    @GetMapping("/clientslimit")
    ResponseEntity<List<ClientItemResponse>> getLimitNumberOfClients() {
        List<Client> clients = clientService.findLimitNumberOfClients(PageRequest.of(0, 50));
        List<ClientItemResponse> clientItemResponses = new ArrayList<>();
        clients.forEach(client -> clientItemResponses.add(ClientItemResponse.of(client)));
        return new ResponseEntity<>(clientItemResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ClientDetailResponse getClientDetails(@PathVariable Long id) {
        Client client = clientService.findById(id).orElse(null);
        return ClientDetailResponse.of(client);
    }

    @PostMapping("/update/{id}")
    ResponseEntity updateClient(@PathVariable Long id, @RequestBody ClientRequest clientRequest) {
        if (!clientRequest.getFirstName().equals("") && !clientRequest.getLastName().equals("")) {
            clientService.updateFromRequest(id, clientRequest);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Empty data input.", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/findprovider/{id}")
    ProviderDetailResponse getProviderById(@PathVariable Long id) {
        Provider provider = providerService.findProviderByClientId(id);
        return ProviderDetailResponse.of(provider);
    }

    @GetMapping("/findclients/{input}")
    ResponseEntity<List<ClientItemResponse>> getClientsByAnyMatch(@PathVariable String input) {
        if (input != "") {
            List<Client> clients = clientService.findClientsByAnyMatch(input);
            List<ClientItemResponse> clientItemResponses = new ArrayList<>();
            clients.forEach(client -> clientItemResponses.add(ClientItemResponse.of(client)));
            return new ResponseEntity<>(clientItemResponses, HttpStatus.OK);
        } else return getAllClients();
    }

    @GetMapping("/findbyrole/{input}")
    ResponseEntity<List<ClientItemResponse>> getClientsByRole(@PathVariable String input) {
        List<Client> clients = clientService.findClientsByRole(input);
        List<ClientItemResponse> clientItemResponses = new ArrayList<>();
        clients.forEach(client -> clientItemResponses.add(ClientItemResponse.of(client)));
        return new ResponseEntity<>(clientItemResponses, HttpStatus.OK);
    }

    @GetMapping("/getproviders")
    ResponseEntity<List<ProviderItemResponse>> getAllProviders() {
        List<Provider> providers = providerService.findAll();
        List<ProviderItemResponse> providerItemResponses = new ArrayList<>();
        providers.forEach(provider -> providerItemResponses.add(ProviderItemResponse.of(provider)));
        return new ResponseEntity<>(providerItemResponses, HttpStatus.OK);
    }
}
