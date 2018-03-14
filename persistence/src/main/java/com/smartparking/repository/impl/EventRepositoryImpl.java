package com.smartparking.repository.impl;

import com.smartparking.entity.Event;
import com.smartparking.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class EventRepositoryImpl extends AbstractRepository<Event, Long> {

    public EventRepositoryImpl(@Autowired EntityManager entityManager) {
        super(Event.class, entityManager);
    }
}
