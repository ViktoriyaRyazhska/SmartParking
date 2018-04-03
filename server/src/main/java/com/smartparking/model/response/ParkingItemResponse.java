package com.smartparking.model.response;

import com.smartparking.entity.Parking;
import lombok.Data;

@Data
public class ParkingItemResponse {

    private Long id;
    private String city;
    private String street;

    public static ParkingItemResponse of(Parking parking){
        ParkingItemResponse parkingItemResponse = new ParkingItemResponse();
        parkingItemResponse.setId(parking.getId());
        parkingItemResponse.setCity(parking.getCity());
        parkingItemResponse.setStreet(parking.getStreet());
        return parkingItemResponse;
    }
}
