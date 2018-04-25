package com.smartparking.model.request;

import com.smartparking.entity.Parking;
import com.smartparking.entity.Spot;
import lombok.Data;

@Data
public class SpotRequest {
    private Long id;
    private Long parkingId;
    private Long spotNumber;
    private Boolean hasCharger;
    private Boolean isInvalid;
    private Boolean isBlocked;

    public Spot toSpot() {
        Spot spot = new Spot();
        spot.setId(id);
        spot.setSpotNumber(spotNumber);
        Parking parking = new Parking();
        parking.setId(parkingId);
        spot.setParking(parking);
        spot.setHasCharger(hasCharger);
        spot.setIsInvalid(isInvalid);
        spot.setIsBlocked(isBlocked);
        return spot;
    }
}
