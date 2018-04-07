package com.smartparking.eventprocessor.model.view;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Event {
    private Integer spotId;
    private EventType eventType;
    private Instant timestamp;
}
