package com.smartparking.service;

import com.smartparking.entity.Event;
import com.smartparking.model.request.ProcessedEventRequest;
import com.smartparking.repository.EventRepository;

import java.util.Collection;

public interface EventService extends Service<Event, Long, EventRepository> {
    Event findBySpotId(Long spotId);

    void saveAllRequests(Collection<ProcessedEventRequest> processedEventRequests);
}
