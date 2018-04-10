package com.smartparking.eventprocessor.model.view;

import com.smartparking.eventprocessor.model.request.UnverifiedEventRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UnverifiedEvent extends Event {

    private String parkingToken;

    public UnverifiedEvent(UnverifiedEventRequest request) {
        super(request);
        this.parkingToken = request.getParkingToken();
    }
}
