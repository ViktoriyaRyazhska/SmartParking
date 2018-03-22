package com.smartparking.service;

import com.smartparking.entity.Parking;
import com.smartparking.model.response.ParkingResponse;
import com.smartparking.repository.ParkingRepository;

import java.util.List;

public interface ParkingService extends Service<Parking, Long, ParkingRepository> {
    List<Parking> findAllByProviderId(Long id);

    List<ParkingResponse> findAllNearby(Double latitude, Double longitude, Double radius);
}
