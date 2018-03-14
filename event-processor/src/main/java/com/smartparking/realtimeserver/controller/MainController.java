package com.smartparking.realtimeserver.controller;

import com.smartparking.entity.Provider;
import com.smartparking.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MainController {

    private Map<Long, Provider> providers;

    @Autowired
    private ProviderService providerService;

    @PostConstruct
    public void init() {
        providers = providerService.findAll().stream()
                .collect(Collectors.toMap(Provider::getId, p -> p));
    }

    @RequestMapping("provider/update")
    public ResponseEntity providerUpdated(@RequestParam String id) {
        final Long providerId;
        try {
            providerId = Long.valueOf(id);
        } catch (NumberFormatException ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Provider provider = providers.get(providerId);
        if (provider == null) {
            provider = providerService.findById(providerId);
            if (provider == null) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            } else {
                providers.put(providerId, provider);
                return new ResponseEntity(HttpStatus.OK);
            }
        } else {
            providerService.refresh(provider);
            return new ResponseEntity(HttpStatus.OK);
        }
    }
}