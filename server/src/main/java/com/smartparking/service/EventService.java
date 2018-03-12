package com.smartparking.service;

import com.smartparking.entity.Event;

public interface EventService {
    Event updateEvent(Event event);
    void deleteEvent(Event event);
    void saveEvent(Event event);
}
