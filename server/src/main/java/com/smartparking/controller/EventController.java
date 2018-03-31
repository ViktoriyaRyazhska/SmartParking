package com.smartparking.controller;

import com.smartparking.controller.exception.BadRequestException;
import com.smartparking.model.request.EventRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class EventController {

    @PostMapping("/events/save")
    void saveEvents(@RequestBody(required = false) List<EventRequest> requests) {
        if (true) {
            throw new BadRequestException("AAAAAAAAAA");
        }
    }
}
