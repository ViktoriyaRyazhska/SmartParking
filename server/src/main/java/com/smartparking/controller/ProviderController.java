package com.smartparking.controller;

import com.smartparking.entity.Provider;
import com.smartparking.model.filter.ProviderFilter;
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

    @GetMapping("providers")
    List<ProviderItemResponse> findAll(@RequestParam String active,
                                       @RequestParam String companyName) {
        ProviderFilter providerFilter = new ProviderFilter();
        providerFilter.setActive(active);
        providerFilter.setCompanyName(companyName);
        List<Provider> providers = providerService.findAllByFilter(providerFilter);
        List<ProviderItemResponse> providerResponses = new ArrayList<>();
        for (Provider provider : providers) {
            providerResponses.add(ProviderItemResponse.of(provider));
        }
        return providerResponses;
    }

    @GetMapping("providers/{id}")
    ProviderDetailResponse find(@PathVariable Long id) {
        Provider provider = providerService.findById(id);
        return ProviderDetailResponse.of(provider);
    }

    @GetMapping("providers/changeState/{id}")
    ProviderDetailResponse changeState(@PathVariable Long id) {
        return ProviderDetailResponse.of(providerService.changeState(id));
    }

    @PostMapping("/providers/add")
    ResponseEntity save(@RequestBody ProviderRequest providerRequest) {
        if (providerRequest.getName() != "" && providerRequest.getState() != "" &&
                providerRequest.getCity() != "" && providerRequest.getStreet() != "" &&
                providerRequest.getBuildingNumber() != "") {
            providerService.saveFromRequest(providerRequest);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT.valueOf("Bad data input."));
        }
    }
}
