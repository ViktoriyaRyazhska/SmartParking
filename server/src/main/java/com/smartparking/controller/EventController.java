package com.smartparking.controller;

import com.smartparking.controller.exception.BadRequestException;
import com.smartparking.model.request.EventRequest;
import com.smartparking.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/events/save")
    void saveEvents(@RequestBody List<EventRequest> requests) {
        try {
            eventService.saveAllRequests(requests);
        } catch (NullPointerException ex) {
            throw new BadRequestException(ex);
        }
    }
}
