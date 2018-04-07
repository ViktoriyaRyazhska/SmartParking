package com.smartparking.controller;

import com.smartparking.controller.exception.BadRequestException;
import com.smartparking.model.request.ProcessedEventRequest;
import com.smartparking.service.EventService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    AmqpTemplate amqpTemplate;

    @PostMapping("/events/save")
    void saveEvents(@RequestBody List<ProcessedEventRequest> requests) {
        try {
            eventService.saveAllRequests(requests);
        } catch (NullPointerException ex) {
            throw new BadRequestException(ex);
        }
    }

    @GetMapping("testt")
    public void testt() {
        amqpTemplate.convertAndSend("queue", "MESSAGE");
    }
}
