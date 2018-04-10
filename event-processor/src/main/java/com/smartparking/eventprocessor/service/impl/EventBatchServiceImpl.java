package com.smartparking.eventprocessor.service.impl;

import com.smartparking.eventprocessor.model.view.Spot;
import com.smartparking.eventprocessor.model.view.UnverifiedEvent;
import com.smartparking.eventprocessor.model.view.VerifiedEvent;
import com.smartparking.eventprocessor.service.EntityViewService;
import com.smartparking.eventprocessor.service.EventBatchService;
import com.smartparking.eventprocessor.service.ServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EventBatchServiceImpl implements EventBatchService {

    @Autowired
    private EntityViewService entityViewService;

    @Autowired
    private ServerService serverService;

    private Queue<VerifiedEvent> verifiedEvents = new ConcurrentLinkedQueue<>();

    private Queue<UnverifiedEvent> unverifiedEvents = new ConcurrentLinkedQueue<>();

    @Override
    public void pushUnverified(UnverifiedEvent event) {
        unverifiedEvents.add(event);
    }

    @Override
    public void pushVerified(VerifiedEvent event) {
        verifiedEvents.add(event);
    }

    @Override
    public VerifiedEvent pollVerified() {
        return verifiedEvents.poll();
    }

    @Override
    public UnverifiedEvent pollUnverified() {
        return unverifiedEvents.poll();
    }

    @Override
    public synchronized void sendVerified(Collection<? extends VerifiedEvent> events) {
        try {
            serverService.sendVerifiedEvents(events);
            log.info("Verified events sent: count=" + events.size() + ".");
        } catch (IOException ex) {
            verifiedEvents.addAll(events);
            log.error("Verified events does not sent: " + ex.toString());
        }
    }

    @Override
    public synchronized void sendUnverified(Collection<? extends UnverifiedEvent> events) {
        try {
            entityViewService.update();
            log.info("EntityViewService updated.");
        } catch (IOException ex) {
            unverifiedEvents.addAll(events);
            log.error("EntityViewService updating failed: " + ex.toString());
            return;
        }

        List<VerifiedEvent> verifiedEvents = events.stream()
                .filter(this::verify)
                .map(VerifiedEvent::new)
                .collect(Collectors.toList());
        log.info("Unverified events prepared: passed=%d, ignored=%d.",
                verifiedEvents.size(), events.size() - verifiedEvents.size());
        sendVerified(verifiedEvents);
    }

    private boolean verify(UnverifiedEvent event) {
        if (entityViewService.isInitialized() && serverService.isServerAvailable()) {
            Spot spot = entityViewService.getSpot(event.getSpotId());
            return spot != null && spot.getParking().getToken().equals(event.getParkingToken());
        } else {
            return false;
        }
    }
}
