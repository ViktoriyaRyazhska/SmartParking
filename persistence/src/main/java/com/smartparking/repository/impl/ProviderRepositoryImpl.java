package com.smartparking.repository.impl;

import com.smartparking.entity.Provider;
import com.smartparking.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ProviderRepositoryImpl extends AbstractRepository<Provider, Long> {

    public ProviderRepositoryImpl(@Autowired EntityManager entityManager) {
        super(Provider.class, entityManager);
    }
}
