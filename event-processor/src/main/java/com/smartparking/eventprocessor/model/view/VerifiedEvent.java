package com.smartparking.eventprocessor.model.view;

import com.smartparking.eventprocessor.model.request.EventRequest;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class VerifiedEvent extends Event {

    public VerifiedEvent(EventRequest request) {
        super(request);
    }

    public VerifiedEvent(UnverifiedEvent event) {
        super(event.getSpotId(), event.getEventType(), event.getTimestamp());
    }
}
