package com.smartparking.realtimeserver.controller;

import com.smartparking.entity.Event;
import com.smartparking.entity.Spot;
import com.smartparking.service.EventService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MainController {

    //    private Map<Long, Parking> parkings;
    private Map<Long, Spot> spots;

//    @Autowired
//    private ParkingService parkingService;

    @Autowired
    private SpotService spotService;

    @Autowired
    private EventService eventService;

    @PostConstruct
    public void init() {
//        parkings = parkingService.findAll().stream()
//                .collect(Collectors.toMap(Parking::getId, p -> p));
        spots = spotService.findAll().stream()
                .collect(Collectors.toMap(Spot::getId, s -> s));
    }

//    @RequestMapping("provider/update")
//    public ResponseEntity providerUpdated(@RequestParam String id) {
//        final Long parkingId;
//        try {
//            parkingId = Long.valueOf(id);
//        } catch (NumberFormatException ex) {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//
//        Parking parking = parkings.get(parkingId);
//        if (parking == null) {
//            parking = parkingService.findById(parkingId);
//            if (parking == null) {
//                return new ResponseEntity(HttpStatus.BAD_REQUEST);
//            } else {
//                parkings.put(parkingId, parking);
//                return new ResponseEntity(HttpStatus.OK);
//            }
//        } else {
//            parkingService.refresh(parking);
//            return new ResponseEntity(HttpStatus.OK);
//        }
//    }

    @RequestMapping(value = "spot/update", method = RequestMethod.POST)
    public ResponseEntity spotUpdate(@RequestParam Long spotId,
                                     @RequestParam String parkingToken) {

        Spot spot = spots.get(spotId);
        if (spot == null) {
            spot = spotService.findById(spotId);
            if (spot == null) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            } else {
                spots.put(spotId, spot);
            }
        }

        if (spot.getParking().getToken().equals(parkingToken)) {
            Event event = eventService.findBySpotId(spotId);
            if (event == null) {
                event = new Event();
                event.setSpot(spot);
                event.setCurrentArrivalTime();
                eventService.save(event);
            } else {
                event.setCurrentDepartureTime();
            }
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}