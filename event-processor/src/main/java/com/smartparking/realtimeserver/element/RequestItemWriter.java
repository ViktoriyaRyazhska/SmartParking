package com.smartparking.realtimeserver.element;

import com.smartparking.entity.Event;
import com.smartparking.service.EventService;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RequestItemWriter implements ItemWriter<List<Event>> {

    @Autowired
    EventService eventService;

    @Override
    public void write(List<? extends List<Event>> list) throws Exception {
        eventService.insertListEvents(list.get(0));
    }
}
