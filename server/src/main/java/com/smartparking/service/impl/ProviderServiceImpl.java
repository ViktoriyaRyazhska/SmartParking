package com.smartparking.service.impl;

import com.smartparking.entity.Provider;
import com.smartparking.repository.ProviderRepository;
import com.smartparking.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class ProviderServiceImpl implements ProviderService {

    @Autowired
    private ProviderRepository providerRepository;

    @Override
    @Transactional
    public Provider updateProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    @Override
    @Transactional
    public void deleteProvider(Provider provider) {
        providerRepository.delete(provider);
    }

    @Override
    @Transactional
    public void saveProvider(Provider provider) {
        providerRepository.save(provider);
    }
}
