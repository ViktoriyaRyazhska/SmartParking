package com.smartparking.v2.repository.impl;

import com.smartparking.entity.Event;
import com.smartparking.v2.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class EventRepositoryImpl extends AbstractRepository<Event, Long> {

    public EventRepositoryImpl(@Autowired EntityManager entityManager) {
        super(Event.class, entityManager);
    }
}
