package com.smartparking.eventprocessor.listener;

import com.smartparking.eventprocessor.config.constants.RabbitConstants;
import com.smartparking.eventprocessor.model.event.SpotAddEvent;
import com.smartparking.eventprocessor.model.event.SpotDeleteEvent;
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

    @RabbitListener(queues = RabbitConstants.SPOT_ADD_QUEUE)
    public void consumeAdd(SpotAddEvent event) {
        try {
            entityViewService.addSpot(event.getSpotId(), event.getParkingId());
            log.info("Spot added from EntityViewService: spotId={}", event.getSpotId());
        } catch (IllegalStateException ex) {
            log.error("Spot does not added from EntityViewService: spotId={}, exception={}", event.getSpotId(), ex);
        }
    }

    @RabbitListener(queues = RabbitConstants.SPOT_DELETE_QUEUE)
    public void consumeDelete(SpotDeleteEvent event) {
        try {
            entityViewService.deleteSpot(event.getSpotId());
            log.info("Spot deleted from EntityViewService: spotId={}", event.getSpotId());
        } catch (IllegalStateException ex) {
            log.error("Spot does not deleted from EntityViewService: spotId={}, exception={}", event.getSpotId(), ex);
        }
    }
}
