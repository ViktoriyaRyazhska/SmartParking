package com.smartparking.repository;

import com.smartparking.entity.Parking;

import java.util.List;

public interface ParkingRepository extends Repository<Parking, Long> {

    List<Parking> findAllByProviderId(Long id);
}
