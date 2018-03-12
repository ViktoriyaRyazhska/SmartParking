package com.smartparking.service.impl;

import com.smartparking.entity.Address;
import com.smartparking.service.AddressService;

import javax.transaction.Transactional;

public class AddressServiceImpl implements AddressService {

    @Override
    @Transactional
    public Address updateAddress(Address address) {
        return null;
    }

    @Override
    @Transactional
    public void deleteAddress(Address address) {

    }

    @Override
    @Transactional
    public void saveAddress(Address address) {

    }
}
