package com.smartparking.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("server.rabbit")
public class RabbitProperties {
    private String spotAddQueueName = "spot-add-queue";
    private String spotDeleteQueueName = "spot-delete-queue";
    private String parkingAddQueueName = "parking-add-queue";
    private String parkingDeleteQueueName = "parking-delete-queue";
    private String parkingTokenChangeQueueName = "parking-token-change-queue";
}
