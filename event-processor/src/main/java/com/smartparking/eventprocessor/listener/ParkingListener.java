package com.smartparking.eventprocessor.listener;

import com.smartparking.eventprocessor.config.constants.RabbitConstants;
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
public class ParkingListener {

    @Autowired
    private EntityViewService entityViewService;

    @RabbitListener(queues = RabbitConstants.PARKING_DELETE_QUEUE)
    public void onParkingDelete(ParkingDeleteEvent event) {
        log.info("onParkingDelete: " + event);
    }

    @RabbitListener(queues = RabbitConstants.PARKING_ADD_QUEUE)
    public void onParkingAdd(ParkingAddEvent event) {
        log.info("onParkingDelete: " + event);
    }

    @RabbitListener(queues = RabbitConstants.PARKING_TOKEN_CHANGE_QUEUE)
    public void onParkingTokenChange(ParkingTokenChangeEvent event) {
        log.info("onParkingDelete: " + event);
    }
}
