package com.smartparking.v2.repository.impl;

import com.smartparking.entity.Address;
import com.smartparking.v2.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AddressRepositoryImpl extends AbstractRepository<Address, Long> {

    public AddressRepositoryImpl(@Autowired EntityManager entityManager) {
        super(Address.class, entityManager);
    }
}
