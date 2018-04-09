package com.smartparking.service;

import com.smartparking.entity.Parking;
import com.smartparking.entity.Spot;
import com.smartparking.repository.SpotRepository;

import java.util.List;
import java.util.Map;

public interface SpotService extends Service<Spot, Long, SpotRepository> {

    List<Spot> findAllAvailableSpotsByParkingId(Long id);

    Long countAvailableSpotsByParkingId(Long id);

    Long countAllSpotsByParkingId(Long id);

    List<Spot> findAllSpotsByParkingId(Long id);

    Map<Long, Double> getSpotStatistic(long id);

    List<Parking> findBestParkingsByCityAndStreet(String city, String street);

}
