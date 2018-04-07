package com.smartparking.service.impl;

import com.smartparking.entity.Event;
import com.smartparking.model.request.EventRequest;
import com.smartparking.repository.EventRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl extends AbstractService<Event, Long, EventRepository> implements EventService {

    protected EventServiceImpl(@Autowired EventRepository repository) {
        super(repository);
    }

    @Override
    public Event findBySpotId(Long spotId) {
        return repository.findLastBySpotId(spotId);
    }

    @Override
    @Transactional
    public void saveAllRequests(Collection<EventRequest> eventRequests) {
        repository.saveAll(eventRequests.stream()
                .map(EventRequest::toEvent)
                .collect(Collectors.toList()));
    }
}
