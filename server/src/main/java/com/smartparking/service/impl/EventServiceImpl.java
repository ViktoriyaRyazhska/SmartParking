package com.smartparking.service.impl;

import com.smartparking.entity.Event;
import com.smartparking.service.EventService;

import javax.transaction.Transactional;

public class EventServiceImpl implements EventService {
    @Override
    @Transactional
    public Event updateEvent(Event event) {
        return null;
    }

    @Override
    @Transactional
    public void deleteEvent(Event event) {

    }

    @Override
    @Transactional
    public void saveEvent(Event event) {

    }
}
