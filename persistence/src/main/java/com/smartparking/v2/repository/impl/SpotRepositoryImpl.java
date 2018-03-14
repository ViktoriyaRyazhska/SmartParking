package com.smartparking.v2.repository.impl;

import com.smartparking.entity.Spot;
import com.smartparking.v2.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class SpotRepositoryImpl extends AbstractRepository<Spot, Long> {

    public SpotRepositoryImpl(@Autowired EntityManager entityManager) {
        super(Spot.class, entityManager);
    }
}
