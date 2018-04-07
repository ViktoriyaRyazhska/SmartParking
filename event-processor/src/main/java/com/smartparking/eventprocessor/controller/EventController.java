package com.smartparking.eventprocessor.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    /*private Logger LOGGER = LoggerFactory.getLogger(EventController.class);

    private Map<Long, String> tokens;

    @PostConstruct
    public void init() {
        tokens = parkingService.findAll().stream()
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
            tokens.put(parkingId, token);
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
        if (spot != null && tokens.containsValue(eventRequest.getParkingToken())) {
            event.setSpot(spot);
            return true;
        } else {
            return false;
        }

    }

    public List<Event> getEvents() {
        return events;
    }

    public Map<Long, Spot> getSpots() {
        return spots;
    }*/

}