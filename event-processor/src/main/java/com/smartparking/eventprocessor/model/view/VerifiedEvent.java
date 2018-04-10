package com.smartparking.eventprocessor.model.view;

import com.smartparking.eventprocessor.model.request.UnverifiedEventRequest;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class VerifiedEvent extends Event {

    public VerifiedEvent(UnverifiedEventRequest request) {
        super(request);
    }

    public VerifiedEvent(UnverifiedEvent event) {
        super(event.getSpotId(), event.getEventType(), event.getTimestamp());
    }
}
