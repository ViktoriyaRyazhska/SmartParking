package com.smartparking.repository.impl;

import com.smartparking.entity.Spot;
import com.smartparking.repository.AbstractRepository;
import com.smartparking.repository.SpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class SpotRepositoryImpl extends AbstractRepository<Spot, Long>{

    public SpotRepositoryImpl(@Autowired EntityManager entityManager) {
        super(Spot.class, entityManager);
    }


}
