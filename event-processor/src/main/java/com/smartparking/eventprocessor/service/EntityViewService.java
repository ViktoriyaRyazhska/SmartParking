package com.smartparking.eventprocessor.service;

import com.smartparking.eventprocessor.model.view.Spot;

import java.io.IOException;

public interface EntityViewService {

    void update() throws IOException;

    Spot getSpot(Long spotId);

    Spot getSpotByNumberAndParkingToken(Long spotNumber, String parkingToken);

    boolean containsSpot(Long spotId);

    boolean containsParking(Long parkingId);

    void addSpot(Long spotId, Long parkingId, Long spotNumber);

    void deleteSpot(Long spotId);

    void updateSpotNumber(Long spotId, Long spotNumber);

    void deleteParking(Long parkingId);

    void addParking(Long parkingId, String token);

    void updateParkingToken(Long parkingId, String parkingToken);

    boolean isInitialized();
}
