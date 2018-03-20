package com.smartparking.repository;

import com.smartparking.entity.Address;
import com.smartparking.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

}

