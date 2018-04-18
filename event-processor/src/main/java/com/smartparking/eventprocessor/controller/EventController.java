package com.smartparking.eventprocessor.controller;

import com.smartparking.eventprocessor.model.request.UnverifiedEventRequest;
import com.smartparking.eventprocessor.model.view.Spot;
import com.smartparking.eventprocessor.model.view.UnverifiedEvent;
import com.smartparking.eventprocessor.model.view.VerifiedEvent;
import com.smartparking.eventprocessor.service.EntityViewService;
import com.smartparking.eventprocessor.service.EventBatchService;
import com.smartparking.eventprocessor.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

@Slf4j
@RestController
public class EventController {

    private Map<Long, String> parkingTokens;

    @Autowired
    private ServerService serverService;

    @Autowired
    private EventBatchService eventBatchService;

    @Autowired
    private EntityViewService entityViewService;

    @PostMapping(value = "/spot/update")
    public ResponseEntity spotUpdate(@RequestBody UnverifiedEventRequest request) {
        if (request != null) {
            request.setTimestamp(LocalDateTime.now().toInstant(ZoneOffset.UTC));
            if (entityViewService.isInitialized() && serverService.isServerAvailable()) {
//                Spot spot = entityViewService.getSpot(request.getSpotId());
                Spot spot = entityViewService.getSpotByNumberAndParkingToken(request.getSpotNumber(), request.getParkingToken());
                if (spot == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Spot with id=" + request.getSpotId() + " does not exists.");
//                throw new BadRequestException("Spot with id=" + request.getSpotId() + " does not exists.");
                }
                if (!spot.getParking().getToken().equals(request.getParkingToken())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Parking token does not valid.");
//              throw new BadRequestException("Parking token does not valid.");
                }
                request.setSpotId(spot.getId());
                eventBatchService.push(new VerifiedEvent(request));
            } else {
                eventBatchService.push(new UnverifiedEvent(request));
            }
            return ResponseEntity.status(HttpStatus.OK).body("Ok");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad");
        }
    }
}