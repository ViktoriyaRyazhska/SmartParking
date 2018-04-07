package com.smartparking.eventprocessor.controller;

import com.smartparking.eventprocessor.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
public class EventController {

    private Map<Long, String> parkingTokens;

    @Autowired
    private ServerService serverService;

    @PostConstruct
    public void init() throws IOException {
        System.out.println(serverService.getParkingTokens());
    }

    /*@PostConstruct
    public void init() {
        parkingTokens = parkingService.findAll().stream()
                .collect(Collectors.toMap(Parking::getId, Parking::getToken));
        spots = spotService.findAll().stream()
                .collect(Collectors.toMap(Spot::getId, s -> s));
    }

    @RequestMapping("parking/update")
    public ResponseEntity parkingUpdated(@RequestParam Long parkingId) {
        String token;
        token = parkingService.findById(parkingId).getToken();
        if (token == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            parkingTokens.put(parkingId, token);
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
    }

    @RequestMapping(value = "spot/update", method = RequestMethod.POST)
    public ResponseEntity processingInRequests(@RequestBody EventRequest eventRequest) {
        Event event = eventRequest.toEvent();
        if (event != null && tokenIsValid(eventRequest, event)) {
            events.add(event);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    private boolean tokenIsValid(EventRequest eventRequest, Event event) {
        Spot spot = spots.get(eventRequest.getSpotId());
        if (spot != null && parkingTokens.containsValue(eventRequest.getParkingToken())) {
            event.setSpot(spot);
            return true;
        } else {
            return false;
        }

    }*/
}