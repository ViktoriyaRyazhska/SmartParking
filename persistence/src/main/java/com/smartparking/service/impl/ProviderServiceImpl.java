package com.smartparking.service.impl;

import com.smartparking.entity.Address;
import com.smartparking.entity.Provider;
import com.smartparking.model.request.ProviderRequest;
import com.smartparking.repository.AddressRepository;
import com.smartparking.repository.ProviderRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProviderServiceImpl extends AbstractService<Provider, Long, ProviderRepository> implements ProviderService {

    @Autowired
    private AddressRepository addressRepository;

    protected ProviderServiceImpl(@Autowired ProviderRepository repository) {
        super(repository);
    }

    @Override
    @Transactional
    public void saveFromRequest(ProviderRequest providerRequest) {
        Provider provider = new Provider();
        Address address = new Address();
        address.setState(providerRequest.getState());
        address.setCity(providerRequest.getCity());
        address.setStreet(providerRequest.getStreet());
        address.setBuildingNumber(providerRequest.getBuildingNumber());
        addressRepository.save(address);

        provider.setName(providerRequest.getName());
        provider.setActive(true);
        provider.setLegalAddress(address);
        getRepository().save(provider);

    }
}
