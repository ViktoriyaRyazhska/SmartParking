package com.smartparking.eventprocessor.service;

import com.smartparking.eventprocessor.model.view.Spot;

import java.util.Optional;

public interface EntityViewService {

    void initialize();

    Optional<Spot> getSpot(Long spotId);

    boolean containsSpot(Long spotId);

    boolean containsParking(Long parkingId);

    void addSpot(Long spotId, Long parkingId);

    void deleteSpot(Long spotId, Long parkingId);

    void deleteParking(Long parkingId);

    void addParking(Long parkingId, String token);

    void updateParkingToken(Long parkingId, String parkingToken);
}
