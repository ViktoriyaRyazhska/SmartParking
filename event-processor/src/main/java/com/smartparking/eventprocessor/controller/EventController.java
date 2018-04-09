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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
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
    public void init() {
        /*try {
            spots = serverService.getSpots();
        } catch (IOException e) {
            log.error("Cannot connect to Server.", e);
        }*/
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

    @PostMapping(value = "spot/update")
    public void processingInRequests(@RequestBody EventRequest eventRequest) {
        Spot spot = spots.get(eventRequest.getSpotId());
        if (spot == null) {
            throw new BadRequestException("Spot with id '" + eventRequest.getSpotId() + "' does not exists.");
        }
        if (!spot.getParking().getToken().equals(eventRequest.getParkingToken())) {
            throw new BadRequestException("Parking token does not valid.");
        }
        Instant timestamp = Instant.ofEpochMilli(eventRequest.getTimestamp());
        EventType eventType = eventRequest.getEventType();
        eventBatchService.push(new Event(spot, eventType, timestamp));
    }
}