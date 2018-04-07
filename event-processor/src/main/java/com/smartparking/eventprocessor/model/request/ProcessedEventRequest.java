package com.smartparking.eventprocessor.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedEventRequest {
    private Long spotId;
    private String eventType;
    private Long timestamp;
}
