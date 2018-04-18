package com.smartparking.eventprocessor;

import com.smartparking.eventprocessor.service.EntityViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@EnableConfigurationProperties
@SpringBootApplication
public class EventProcessorApplication {

    @Autowired
    private EntityViewService entityViewService;

    @PostConstruct
    private void init() {
        Thread thread = new Thread(() -> {
            try {
                entityViewService.update();
                log.error("EntityViewService updated successfully.");
            } catch (IOException e) {
                log.error("EntityViewService updating failed: " + e.toString());
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public static void main(String[] args) {
        SpringApplication.run(EventProcessorApplication.class, args);
        log.info("Eventprocessor started");
    }
}
