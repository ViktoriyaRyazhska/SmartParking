package com.smartparking.realtimeserver.controller;

import com.smartparking.entity.Address;
import com.smartparking.entity.Provider;
import com.smartparking.service.AddressService;
import com.smartparking.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MainController {

    private List<Provider> providers;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private AddressService addressService;
//
//    @PostConstruct
//    public void init() {
//        providers = providerService.findAll();
//    }

    @RequestMapping("provider/update")
    public ResponseEntity providerUpdated() {
        Address address = new Address();
        address.setState("Lvivska");
        address.setCity("Lviv");
        address.setStreet("Shevchenka");
        address.setBuildingNumber("1");
        addressService.save(address);

        Provider provider = new Provider();
        provider.setId(1L);
        provider.setLegalAddress(address);
        provider.setName("Provider");
        provider.setActive(true);
        providerService.save(provider);

        providers = providerService.findAll();

        return ResponseEntity.ok(null);
    }
}