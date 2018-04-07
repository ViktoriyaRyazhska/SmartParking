package com.smartparking.eventprocessor.controller;

import com.smartparking.eventprocessor.controller.exception.BadRequestException;
import com.smartparking.eventprocessor.model.request.EventRequest;
import com.smartparking.eventprocessor.model.view.Event;
import com.smartparking.eventprocessor.model.view.EventType;
import com.smartparking.eventprocessor.model.view.Spot;
import com.smartparking.eventprocessor.service.EventBatchService;
import com.smartparking.eventprocessor.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@Slf4j
@RestController
public class EventController {

    private Map<Long, String> parkingTokens;

    @Autowired
    private ServerService serverService;

    @Autowired
    private EventBatchService eventBatchService;

    private Map<Long, Spot> spots;

    @PostConstruct
    public void init() throws IOException {
        spots = serverService.getSpots();
    }

    /*@RequestMapping("parking/update")
    public ResponseEntity parkingUpdated(@RequestParam Long id) {
        String token;
        token = parkingService.findById(id).getToken();
        if (token == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            parkingTokens.put(id, token);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping("spot/add")
    public ResponseEntity spotUpdate(@RequestParam Long spotId) {
        Spot spot = spotService.findById(spotId);
        if (spot == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            spots.put(spotId, spot);
            return new ResponseEntity(HttpStatus.OK);
        }
    }*/

    @RequestMapping(value = "spot/update", method = RequestMethod.POST)
    public void processingInRequests(@RequestBody EventRequest eventRequest) {
        EventType eventType;
        try {
            eventType = EventType.valueOf(eventRequest.getEventType());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid eventType.", ex);
        }
        Instant timestamp;
        try {
            timestamp = Instant.ofEpochMilli(Long.parseLong(eventRequest.getTimestamp()));
        } catch (NumberFormatException ex) {
            throw new BadRequestException("Invalid timestamp.", ex);
        }
        Spot spot = spots.get(eventRequest.getSpotId());
        if (spot == null) {
            throw new BadRequestException("Spot with id '" + eventRequest.getSpotId() + "' does not exists.");
        }
        if (!spot.getParking().getToken().equals(eventRequest.getParkingToken())) {
            throw new BadRequestException("Parking token does not valid.");
        }
        eventBatchService.push(new Event(spot, eventType, timestamp));
    }
}