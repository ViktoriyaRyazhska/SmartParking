package com.smartparking.controller;

import com.smartparking.dto.AddressDto;
import com.smartparking.dto.ParkingDto;
import com.smartparking.entity.Parking;
import com.smartparking.entity.Provider;
import com.smartparking.model.response.ProviderDetailResponse;
import com.smartparking.model.response.ProviderItemResponse;
import com.smartparking.service.ParkingService;
import com.smartparking.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @Autowired
    private ParkingService parkingService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("providers")
    List<ProviderItemResponse> findAll() {
        List<Provider> providers = providerService.findAll();
        List<ProviderItemResponse> providerResponses = new ArrayList<>();
        for (Provider provider : providers) {
            providerResponses.add(ProviderItemResponse.of(provider));
        }
        return providerResponses;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("providers/{id}")
    ProviderDetailResponse find(@PathVariable Long id){
        Provider provider = providerService.findById(id);
        return ProviderDetailResponse.of(provider);
    }
}


