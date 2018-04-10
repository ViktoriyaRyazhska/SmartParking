package com.smartparking.eventprocessor.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("eventprocessor.batch")
public class EventBatchProperties {
    private int chunkSize = 1000;
}
