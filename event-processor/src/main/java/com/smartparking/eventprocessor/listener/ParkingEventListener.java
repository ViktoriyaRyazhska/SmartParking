package com.smartparking.eventprocessor.listener;

import com.smartparking.eventprocessor.model.event.ParkingAddEvent;
import com.smartparking.eventprocessor.model.event.ParkingDeleteEvent;
import com.smartparking.eventprocessor.model.event.ParkingTokenChangeEvent;
import com.smartparking.eventprocessor.service.EntityViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ParkingEventListener {

    @Autowired
    private EntityViewService entityViewService;

    @RabbitListener(queues = "${eventprocessor.rabbit.parking-delete-queue-name}")
    public void consumeDelete(ParkingDeleteEvent event) {
        try {
            entityViewService.deleteParking(event.getParkingId());
            log.info("Parking deleted from EntityViewService: parkingId={}", event.getParkingId());
        } catch (IllegalArgumentException ex) {
            log.error("Parking does not deleted from EntityViewService: parkingId={}, exception={}",
                    event.getParkingId(), ex);
        }
    }

    @RabbitListener(queues = "${eventprocessor.rabbit.parking-add-queue-name}")
    public void consumeAdd(ParkingAddEvent event) {
        try {
            entityViewService.addParking(event.getParkingId(), event.getParkingToken());
            log.info("Parking added into EntityViewService: parkingId={}", event.getParkingId());
        } catch (IllegalStateException ex) {
            log.error("Parking does not added into EntityViewService: parkingId={}, exception={}",
                    event.getParkingId(), ex);
        }
    }

    @RabbitListener(queues = "${eventprocessor.rabbit.parking-token-change-queue-name}")
    public void consumeTokenChange(ParkingTokenChangeEvent event) {
        log.info("Parking token changed: " + event.getParkingId());
        try {
            entityViewService.updateParkingToken(event.getParkingId(), event.getParkingToken());
            log.info("Parking token changed into EntityViewService: parkingId={}", event.getParkingId());
        } catch (IllegalStateException ex) {
            log.error("Parking token does not changed into EntityViewService: parkingId={}, exception={}",
                    event.getParkingId(), ex);
        }
    }
}
