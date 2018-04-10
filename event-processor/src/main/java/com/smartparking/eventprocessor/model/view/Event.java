package com.smartparking.eventprocessor.model.view;

import com.smartparking.eventprocessor.model.request.UnverifiedEventRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Event {
    private Long spotId;
    private EventType eventType;
    private Instant timestamp;

    public Event(UnverifiedEventRequest request) {
        this(request.getSpotId(), request.getEventType(), Instant.ofEpochMilli(request.getTimestamp()));
    }
}
