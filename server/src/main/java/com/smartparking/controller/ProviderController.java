package com.smartparking.controller;

import com.smartparking.entity.Provider;
import com.smartparking.model.filter.ProviderFilter;
import com.smartparking.model.request.ProviderRequest;
import com.smartparking.model.response.ProviderItemResponse;
import com.smartparking.service.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ProviderController {

    private final ProviderService providerService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderController.class);

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("providers")
    ResponseEntity<List<ProviderItemResponse>> findAll(@ModelAttribute ProviderFilter providerFilter) {
        List<Provider> providers = providerService.findAllByFilter(providerFilter);
        List<ProviderItemResponse> providerResponses = new ArrayList<>();
        providers.forEach(provider -> providerResponses.add(ProviderItemResponse.of(provider)));
        return new ResponseEntity<>(providerResponses, HttpStatus.OK);
    }

    @GetMapping("providers/{id}")
    ResponseEntity<?> find(@PathVariable Long id) {
        return providerService.findByIdResponse(id)
                .map(provider -> new ResponseEntity<Object>(provider, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Such provider wasn't found!", HttpStatus.BAD_REQUEST));
    }

    @PostMapping("/providers/save")
    ResponseEntity<?> save(@RequestBody ProviderRequest providerRequest) {
        providerService.save(providerRequest.toProvider());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
