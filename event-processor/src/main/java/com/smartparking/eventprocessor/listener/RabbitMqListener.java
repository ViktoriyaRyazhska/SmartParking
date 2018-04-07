package com.smartparking.eventprocessor.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMqListener {

    @RabbitListener(queues = "queue")
    public void processQueue(String message) {
        log.info("Received from queue 1: " + message);
    }
}
