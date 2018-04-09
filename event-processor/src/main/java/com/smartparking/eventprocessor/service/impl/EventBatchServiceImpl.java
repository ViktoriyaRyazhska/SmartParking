package com.smartparking.eventprocessor.service.impl;

import com.smartparking.eventprocessor.controller.exception.FailureException;
import com.smartparking.eventprocessor.model.view.Event;
import com.smartparking.eventprocessor.service.EventBatchService;
import com.smartparking.eventprocessor.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

@Slf4j
@Service
public class EventBatchServiceImpl implements EventBatchService {

    @Autowired
    private ServerService serverService;

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
        try {
            serverService.sendEvents(events);
            //TODO
        } catch (IOException | FailureException e) {
            log.error("Events can`t be sent to Server.", e);
        }
    }
}
