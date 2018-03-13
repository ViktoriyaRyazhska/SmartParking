package com.smartparking.service;

import com.smartparking.entity.Provider;

import java.util.List;

public interface ProviderService {
    Provider updateProvider(Provider provider);
    void deleteProvider(Provider provider);
    void saveProvider(Provider provider);
    List<Provider> findAllProviders();
}
