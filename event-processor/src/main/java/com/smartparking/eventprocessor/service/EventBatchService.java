package com.smartparking.eventprocessor.service;

import com.smartparking.eventprocessor.model.view.Event;

import java.util.Collection;

public interface EventBatchService {

    Event poll();

    void push(Event event);

    void send(Collection<? extends Event> events);
}
