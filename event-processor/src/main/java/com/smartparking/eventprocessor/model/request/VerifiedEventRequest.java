package com.smartparking.eventprocessor.model.request;

import com.smartparking.eventprocessor.model.view.Event;
import com.smartparking.eventprocessor.model.view.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifiedEventRequest {

    private Long spotId;
    private EventType eventType;
    private Long timestamp;

    public VerifiedEventRequest(Event event) {
        this(event.getSpotId(), event.getEventType(), event.getTimestamp().toEpochMilli());
    }
}
