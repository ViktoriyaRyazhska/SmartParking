package com.smartparking.eventprocessor.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("eventprocessor.rabbit")
public class RabbitProperties {
    private String spotAddQueueName;
    private String spotDeleteQueueName;
    private String spotNumberChangeQueueName;
    private String parkingAddQueueName;
    private String parkingDeleteQueueName;
    private String parkingTokenChangeQueueName;
}
