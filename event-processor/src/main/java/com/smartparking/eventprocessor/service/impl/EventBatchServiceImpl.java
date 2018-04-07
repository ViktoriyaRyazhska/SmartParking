package com.smartparking.eventprocessor.service.impl;

import com.smartparking.eventprocessor.model.view.Event;
import com.smartparking.eventprocessor.service.EventBatchService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

@Service
public class EventBatchServiceImpl implements EventBatchService {

    private Queue<Event> events = new LinkedList<>();

    @Override
    public void push(Event event) {
        events.add(event);
    }

    @Override
    public Event poll() {
        return events.poll();
    }

    @Override
    public void send(Collection<? extends Event> events) {
        //TODO WRITE THIS
    }
}
