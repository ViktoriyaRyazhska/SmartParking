package com.smartparking.service;

import com.smartparking.entity.Provider;
import com.smartparking.repository.ProviderRepository;
import com.smartparking.model.request.ProviderRequest;

public interface ProviderService extends Service<Provider, Long, ProviderRepository> {
    void saveFromRequest(ProviderRequest providerRequest);
}
