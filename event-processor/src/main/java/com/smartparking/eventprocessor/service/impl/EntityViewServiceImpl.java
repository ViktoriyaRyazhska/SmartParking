package com.smartparking.eventprocessor.service.impl;

import com.smartparking.eventprocessor.model.response.ParkingWithSpotsResponse;
import com.smartparking.eventprocessor.model.response.SpotResponse;
import com.smartparking.eventprocessor.model.view.Parking;
import com.smartparking.eventprocessor.model.view.Spot;
import com.smartparking.eventprocessor.service.EntityViewService;
import com.smartparking.eventprocessor.service.ServerService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EntityViewServiceImpl implements EntityViewService {

    @Getter
    private volatile boolean initialized = false;

    @Autowired
    private ServerService serverService;

    private Map<Long, Parking> parkings = new ConcurrentHashMap<>();

    private Map<Long, Spot> spots = new ConcurrentHashMap<>();

    @Override
    public synchronized void update() throws IOException {
        List<ParkingWithSpotsResponse> parkingsWithSpots = serverService.getParkingsWithSpots();
        parkings.clear();
        spots.clear();
        for (ParkingWithSpotsResponse p : parkingsWithSpots) {
            Parking parking = new Parking(p.getId(), p.getToken());
            parkings.put(p.getId(), parking);
            for (SpotResponse s : p.getSpots()) {
                spots.put(s.getId(), new Spot(s.getId(), parking, s.getSpotNumber()));
            }
        }

        initialized = true;
    }

    @Override
    public Spot getSpot(Long spotId) {
        return spots.get(spotId);
    }

    @Override
    public Spot getSpotByNumberAndParkingToken(Long spotNumber, String parkingToken) {
        Spot spot = spots.values()
                .stream()
                .filter(spotPredicate -> spotPredicate.getParking().getToken().equals(parkingToken))
                .filter(spotPredicate -> spotPredicate.getSpotNumber().equals(spotNumber))
                .findFirst().orElse(null);
        return spot;
    }


    @Override
    public boolean containsSpot(Long spotId) {
        return spots.containsKey(spotId);
    }

    @Override
    public boolean containsParking(Long parkingId) {
        return parkings.containsKey(parkingId);
    }

    @Override
    public synchronized void addSpot(Long spotId, Long parkingId, Long spotNumber) {
        if (spots.containsKey(spotId)) {
            throw new IllegalStateException("Spot with id=" + spotId + " does not exists.");
        }
        Parking parking = parkings.get(parkingId);
        if (parking == null) {
            throw new IllegalStateException("Parking with id=" + parkingId + " does not exists.");
        }
        spots.put(spotId, new Spot(spotId, parking, spotNumber));
    }

    @Override
    public synchronized void deleteSpot(Long spotId) {
        Spot spot = spots.remove(spotId);
        if (spot == null) {
            throw new IllegalArgumentException("Parking with id=" + spotId + " does not exists.");
        }
    }

    @Override
    public synchronized void deleteParking(Long parkingId) {
        Parking parking = parkings.remove(parkingId);
        if (parking == null) {
            throw new IllegalArgumentException("Parking with id=" + parkingId + " does not exists.");
        }
        spots.values().removeIf(s -> s.getParking().getId().equals(parking.getId()));
    }

    @Override
    public synchronized void updateSpotNumber(Long spotId, Long spotNumber) {
        Spot spot = spots.get(spotId);
        if (spot == null) {
            throw new IllegalStateException("Spot with id=" + spotId + " does not exists.");
        }
        spot.setSpotNumber(spotNumber);
    }

    @Override
    public synchronized void addParking(Long parkingId, String token) {
        if (parkings.containsKey(parkingId)) {
            throw new IllegalStateException("Parking with id=" + parkingId + " does not exists.");
        }
        parkings.put(parkingId, new Parking(parkingId, token));
    }

    @Override
    public synchronized void updateParkingToken(Long parkingId, String parkingToken) {
        Parking parking = parkings.get(parkingId);
        if (parking == null) {
            throw new IllegalStateException("Parking with id=" + parkingId + " does not exists.");
        }
        parking.setToken(parkingToken);
    }
}
