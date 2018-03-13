package com.smartparking.service.impl;

import com.smartparking.entity.Address;
import com.smartparking.repository.AddressRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends AbstractService<Address, Long, AddressRepository> implements AddressService {

    protected AddressServiceImpl(@Autowired AddressRepository repository) {
        super(repository);
    }
}
