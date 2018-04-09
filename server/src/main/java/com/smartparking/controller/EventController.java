package com.smartparking.controller;

import com.smartparking.config.constants.RabbitConstants;
import com.smartparking.controller.exception.BadRequestException;
import com.smartparking.model.event.ParkingAddEvent;
import com.smartparking.model.event.ParkingDeleteEvent;
import com.smartparking.model.event.ParkingTokenChangeEvent;
import com.smartparking.model.request.ProcessedEventRequest;
import com.smartparking.model.response.InfoResponse;
import com.smartparking.service.EventService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    RabbitProperties rabbitProperties;

    @PostMapping("/events/save")
    void saveEvents(@RequestBody List<ProcessedEventRequest> requests) {
        try {
            eventService.saveAllRequests(requests);
        } catch (NullPointerException ex) {
            throw new BadRequestException(ex);
        }
    }

    @ResponseBody
    @GetMapping("testt")
    public InfoResponse testt() {
        amqpTemplate.convertAndSend(RabbitConstants.PARKING_DELETE_QUEUE, new ParkingDeleteEvent(0L));
        amqpTemplate.convertAndSend(RabbitConstants.PARKING_ADD_QUEUE, new ParkingAddEvent(1L, "ADD"));
        amqpTemplate.convertAndSend(RabbitConstants.PARKING_TOKEN_CHANGE_QUEUE, new ParkingTokenChangeEvent(2L, "TOKEN"));
        return new InfoResponse("OK");
    }
}
