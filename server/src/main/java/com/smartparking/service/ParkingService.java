package com.smartparking.service;

import com.smartparking.dto.ParkingDto;
import com.smartparking.entity.Parking;

import java.util.List;

public interface ParkingService {
    Parking updateParking(Parking parking);
    void deleteParking(Parking parking);
    void saveParking(Parking parking);
    Parking findParkingById(Long parkingId);
    List<ParkingDto> findAllParkings();
}
