package com.smartparking.service;

import com.smartparking.entity.Address;

public interface AddressService {
    Address updateAddress(Address address);
    void deleteAddress(Address address);
    void saveAddress(Address address);
}
