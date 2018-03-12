package com.smartparking.service;

import com.smartparking.entity.Provider;

public interface ProviderService {
    Provider updateProvider(Provider provider);
    void deleteProvider(Provider provider);
    void saveProvider(Provider provider);
}
