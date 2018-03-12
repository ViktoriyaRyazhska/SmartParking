package com.smartparking.service.impl;

import com.smartparking.entity.Event;
import com.smartparking.repository.EventRepository;
import com.smartparking.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    @Transactional
    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }

    @Override
    @Transactional
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }
}
