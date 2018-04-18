package com.smartparking.publisher;

import com.smartparking.config.properties.RabbitProperties;
import com.smartparking.entity.Spot;
import com.smartparking.model.event.SpotAddEvent;
import com.smartparking.model.event.SpotDeleteEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpotEventPublisher {

    @Autowired
    private RabbitProperties rabbitProperties;

    @Autowired
    private RabbitTemplate amqpTemplate;

    public void publishSave(Spot spot) {
        if (spot.getId() == null) {
            publishAdd(spot);
        }
    }

    public void publishAdd(Spot spot) {
        SpotAddEvent event = new SpotAddEvent(spot.getId(), spot.getParking().getId());
        amqpTemplate.convertAndSend(rabbitProperties.getSpotAddQueueName(), event);
    }

    public void publishDelete(Spot spot) {
        SpotDeleteEvent event = new SpotDeleteEvent(spot.getId());
        amqpTemplate.convertAndSend(rabbitProperties.getSpotDeleteQueueName(), event);
    }
}
