package com.smartparking.controller;

import com.smartparking.entity.Provider;
import com.smartparking.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("providers")
    List<Provider> findAll() {
        return providerService.findAll();
    }

}
