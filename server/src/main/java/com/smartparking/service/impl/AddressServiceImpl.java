package com.smartparking.service.impl;

import com.smartparking.entity.Address;
import com.smartparking.repository.AddressRepository;
import com.smartparking.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    @Transactional
    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public void deleteAddress(Address address) {
        addressRepository.delete(address);
    }

    @Override
    @Transactional
    public void saveAddress(Address address) {
        addressRepository.save(address);
    }
}
