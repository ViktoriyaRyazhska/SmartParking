package com.smartparking.eventprocessor.listener;

import com.smartparking.eventprocessor.model.event.SpotAddEvent;
import com.smartparking.eventprocessor.model.event.SpotDeleteEvent;
import com.smartparking.eventprocessor.model.event.SpotNumberChangeEvent;
import com.smartparking.eventprocessor.service.EntityViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpotEventListener {

    @Autowired
    private EntityViewService entityViewService;

    @RabbitListener(queues = "${eventprocessor.rabbit.spot-add-queue-name}")
    public void consumeAdd(SpotAddEvent event) {
        try {
            entityViewService.addSpot(event.getSpotId(), event.getParkingId(), event.getSpotNumber());
            log.info("Spot added from EntityViewService: spotId={}", event.getSpotId());
        } catch (IllegalStateException ex) {
            log.error("Spot does not added from EntityViewService: spotId={}, exception={}", event.getSpotId(), ex);
        }
    }

    @RabbitListener(queues = "${eventprocessor.rabbit.spot-delete-queue-name}")
    public void consumeDelete(SpotDeleteEvent event) {
        try {
            entityViewService.deleteSpot(event.getSpotId());
            log.info("Spot deleted from EntityViewService: spotId={}", event.getSpotId());
        } catch (IllegalArgumentException ex) {
            log.error("Spot does not deleted from EntityViewService: spotId={}, exception={}", event.getSpotId(), ex);
        }
    }

    @RabbitListener(queues = "${eventprocessor.rabbit.spot-number-change-queue-name}")
    public void consumeSpotNumberChange(SpotNumberChangeEvent event) {
        log.info("Spot number changed: " + event.getSpotId());
        try {
            entityViewService.updateSpotNumber(event.getSpotId(), event.getSpotNumber());
            log.info("Spot number changed into EntityViewService: spotId={}", event.getSpotId());
        } catch (IllegalStateException ex) {
            log.error("Spot number does not changed into EntityViewService: spotId={}, exception={}",
                    event.getSpotId(), ex);
        }
    }
}
