package com.smartparking.eventprocessor.controller;

import com.smartparking.eventprocessor.controller.exception.BadRequestException;
import com.smartparking.eventprocessor.model.request.UnverifiedEventRequest;
import com.smartparking.eventprocessor.model.view.Spot;
import com.smartparking.eventprocessor.model.view.UnverifiedEvent;
import com.smartparking.eventprocessor.model.view.VerifiedEvent;
import com.smartparking.eventprocessor.service.EntityViewService;
import com.smartparking.eventprocessor.service.EventBatchService;
import com.smartparking.eventprocessor.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public void spotUpdate(@RequestBody UnverifiedEventRequest request) {
        if (entityViewService.isInitialized() && serverService.isServerAvailable()) {
            Spot spot = entityViewService.getSpot(request.getSpotId());
            if (spot == null) {
                throw new BadRequestException("Spot with id=" + request.getSpotId() + " does not exists.");
            }
            if (!spot.getParking().getToken().equals(request.getParkingToken())) {
                throw new BadRequestException("Parking token does not valid.");
            }
            eventBatchService.push(new VerifiedEvent(request));
        } else {
            eventBatchService.push(new UnverifiedEvent(request));
        }
    }
}