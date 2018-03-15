package com.smartparking.controller;

import com.smartparking.entity.Provider;
import com.smartparking.model.request.ProviderRequest;
import com.smartparking.model.response.ProviderDetailResponse;
import com.smartparking.model.response.ProviderItemResponse;
import com.smartparking.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProviderController {

    @Autowired
    private ProviderService providerService;

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
    ProviderDetailResponse find(@PathVariable Long id) {
        Provider provider = providerService.findById(id);
        return ProviderDetailResponse.of(provider);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/ssss")
    ResponseEntity save(@RequestBody ProviderRequest providerRequest) {
        new RuntimeException();
        providerService.saveFromRequest(providerRequest);
        return new ResponseEntity(HttpStatus.OK);
    }
}
