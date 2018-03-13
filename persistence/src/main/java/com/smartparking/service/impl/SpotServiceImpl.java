package com.smartparking.service.impl;

import com.smartparking.entity.Provider;
import com.smartparking.repository.ProviderRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotServiceImpl extends AbstractService<Provider, Long, ProviderRepository> implements ProviderService {

    protected SpotServiceImpl(@Autowired ProviderRepository repository) {
        super(repository);
    }
}
