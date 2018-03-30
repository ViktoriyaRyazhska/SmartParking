package com.smartparking.controller;

import com.smartparking.entity.Client;
import com.smartparking.model.response.ClientDetailResponse;
import com.smartparking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientProfileController {

    @Autowired
    ClientService clientService;

    @RequestMapping("/profile")
    ClientDetailResponse getClientDetails() {
        Client client = clientService.findOne(SecurityContextHolder.getContext().getAuthentication().getName());
        return ClientDetailResponse.of(client);
    }
}
