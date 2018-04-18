package com.smartparking.eventprocessor.model.view;

import com.smartparking.eventprocessor.model.request.UnverifiedEventRequest;
import lombok.*;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UnverifiedEvent extends Event {

    private String parkingToken;
    private Long spotNumber;

    public UnverifiedEvent(UnverifiedEventRequest request) {
        super(request);
        this.parkingToken = request.getParkingToken();
        this.spotNumber = request.getSpotNumber();
    }
}
