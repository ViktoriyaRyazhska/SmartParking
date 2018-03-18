package com.smartparking.controller;

import com.smartparking.entity.Client;
import com.smartparking.model.request.ClientRequest;
import com.smartparking.model.request.ProviderRequest;
import com.smartparking.model.response.ClientDetailResponse;
import com.smartparking.model.response.ClientItemResponse;
import com.smartparking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/clients/update/{id}")
    ResponseEntity update(@PathVariable Long id, @RequestBody ClientRequest clientRequest) {
//        if (clientRequest.getFirsName() != "" && clientRequest.getLastName() != "" &&
//                clientRequest.getEmail() != "") {
        System.out.println(clientRequest.getFirsName());
        clientService.updateFromRequest(id, clientRequest);
        return new ResponseEntity(HttpStatus.OK);
//        } else {
//            return new ResponseEntity(HttpStatus.NO_CONTENT.valueOf("Bad data input."));
//        }
    }

}
