package com.smartparking.model.request;

import com.smartparking.entity.Event;
import com.smartparking.entity.EventType;
import com.smartparking.entity.Spot;
import lombok.Data;
import lombok.val;

import java.time.Instant;
import java.util.Objects;

@Data
public class EventRequest {

    private Long spotId;
    private Integer eventType;
    private Long timestamp;

    public Event toEvent() {
        val spot = new Spot(this.spotId);
        val eventType = EventType.valueOf(this.eventType);
        val timestamp = Instant.ofEpochMilli(Objects.requireNonNull(this.timestamp, "timestamp"));
        return new Event(spot, eventType, timestamp);
    }
}
