package com.smartparking.controller;

import com.smartparking.dto.AddressDto;
import com.smartparking.dto.ProviderDto;
import com.smartparking.entity.Provider;
import com.smartparking.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("providers")
    List<ProviderDto> findAll() {
        List<Provider> providers = providerService.findAll();
        List<ProviderDto> dtoProviders = new ArrayList<>();
        AddressDto addressDto;
        ProviderDto providerDto;
        for (Provider provider : providers) {
            addressDto = AddressDto.builder()
                    .setCity(provider.getLegalAddress().getCity())
                    .setStreet(provider.getLegalAddress().getStreet())
                    .setBuildingNumber(provider.getLegalAddress().getBuildingNumber());
            providerDto = ProviderDto.builder()
                    .setName(provider.getName())
                    .setId(provider.getId())
                    .setLegalAddressDto(addressDto);
            dtoProviders.add(providerDto);
        }
        return dtoProviders;
    }
}


