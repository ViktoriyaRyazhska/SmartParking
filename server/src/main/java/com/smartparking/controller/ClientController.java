package com.smartparking.controller;

import com.smartparking.dto.AddressDto;
import com.smartparking.dto.ClientDto;
import com.smartparking.dto.ProviderDto;
import com.smartparking.dto.RegistrationClient;
import com.smartparking.entity.Client;
import com.smartparking.entity.Role;
import com.smartparking.model.response.ClientDetailResponse;
import com.smartparking.model.response.ProviderDetailResponse;
import com.smartparking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("clients")
    List<ClientDto> findAll() {
        List<Client> clients = clientService.findAll();
        List<ClientDto> clientsDto = new ArrayList<>();
        ProviderDto providerDto;
        ClientDto clientDto;
        for (Client client : clients) {
            providerDto = ProviderDto.builder()
                    .setId(client.getProvider().getId())
                    .setName(client.getProvider().getName())
                    .setActive(client.getProvider().getActive());
            clientDto = ClientDto.builder().setId(client.getId())
                    .setFirstName(client.getFirstName())
                    .setLastName(client.getLastName())
                    .setEmail(client.getEmail())
                    .setProviderDto(providerDto);
            clientsDto.add(clientDto);
        }
        return clientsDto;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("clients/{id}")
    ClientDetailResponse find(@PathVariable Long id){
        Client provider = clientService.findById(id);
        return ClientDetailResponse.of(provider);
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public void saveUser(@RequestBody RegistrationClient regClient){
        Client client = new Client();
        client.setEmail(regClient.getEmail());
        client.setPassword(bcryptEncoder.encode(regClient.getPassword()));
        client.setFirstName(regClient.getFirstName());
        client.setLastName(regClient.getLastName());
        client.setRole(Role.DRIVER);
        clientService.save(client);
    }

}
