package com.smartparking.service.impl;

import com.smartparking.entity.Provider;
import com.smartparking.service.ProviderService;

import javax.transaction.Transactional;

public class ProviderServiceImpl implements ProviderService {
    @Override
    @Transactional
    public Provider updateProvider(Provider provider) {
        return null;
    }

    @Override
    @Transactional
    public void deleteProvider(Provider provider) {

    }

    @Override
    @Transactional
    public void saveProvider(Provider provider) {

    }
}
