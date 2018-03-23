package com.smartparking.controller;

import com.smartparking.entity.Provider;
import com.smartparking.model.filter.ProviderFilter;
import com.smartparking.model.request.ProviderRequest;
import com.smartparking.model.request.ProviderStatisticRequest;
import com.smartparking.model.response.ProviderDetailResponse;
import com.smartparking.model.response.ProviderItemResponse;
import com.smartparking.service.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderController.class);

    @GetMapping("providers")
    List<ProviderItemResponse> findAll(@RequestParam String active,
                                       @RequestParam String companyName) {
        ProviderFilter providerFilter = new ProviderFilter();
        providerFilter.setActive(active);
        providerFilter.setCompanyName(companyName);
        LOGGER.info("Filtering by " + providerFilter.getActive() + " state and " + providerFilter.getCompanyName() +
                " company name.");
        List<Provider> providers = providerService.findAllByFilter(providerFilter);
        List<ProviderItemResponse> providerResponses = new ArrayList<>();
        for (Provider provider : providers) {
            providerResponses.add(ProviderItemResponse.of(provider));
        }
        LOGGER.info("Filtered providers response - " + providerResponses);
        return providerResponses;
    }

    @GetMapping("providers/{id}")
    ResponseEntity<ProviderDetailResponse> find(@PathVariable Long id) {
        LOGGER.info("Searching the provider with id " + id);
        Provider provider = providerService.findById(id);
        if (provider != null) {
            ProviderDetailResponse response = ProviderDetailResponse.of(provider);
            LOGGER.info("Provider was found.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            LOGGER.info("Provider wasn't found.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("providers/changeState/{id}")
    ResponseEntity<ProviderDetailResponse> changeState(@PathVariable Long id) {
        LOGGER.info("Finding provider by ID - " + id + " to change state.");
        if (providerService.findById(id) != null) {
            LOGGER.info("Provider was found and state was changed!");
            return new ResponseEntity<ProviderDetailResponse>(ProviderDetailResponse.of(providerService.changeState(id)), HttpStatus.OK);
        } else {
            LOGGER.info("Provider wasn't found!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/providers/add")
    ResponseEntity<?> save(@RequestBody ProviderRequest providerRequest) {
        if (!(providerRequest.getName().equals("") && providerRequest.getCity().equals("")
                && providerRequest.getStreet().equals("")
                && providerRequest.getBuilding().equals(""))) {
            providerService.saveFromRequest(providerRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Bad data input.", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/providers/update/{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProviderRequest providerRequest) {
        Provider provider = providerService.findById(id);
        provider.setName(providerRequest.getName());
        provider.setCity(providerRequest.getCity());
        provider.setStreet(providerRequest.getStreet());
        provider.setBuilding(providerRequest.getBuilding());
        providerService.save(provider);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("providers/statistic")
    ProviderStatisticRequest statistic() {
        return providerService.getStatistic();
    }

}
