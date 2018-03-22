package com.smartparking.service.impl;

import com.smartparking.entity.Parking;
import com.smartparking.repository.ParkingRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ParkingServiceImpl extends AbstractService<Parking, Long, ParkingRepository> implements ParkingService {

    protected ParkingServiceImpl(@Autowired ParkingRepository repository) {
        super(repository);
    }

    @Override
    public List<Parking> findAllByProviderId(Long id) {
        return getRepository().findAllByProviderId(id);
    }

    @Override
    public List<Parking> findAllNearby(Double latitude, Double longitude, Double radius) {
        Objects.requireNonNull(latitude, "latitude");
        Objects.requireNonNull(longitude, "longitude");
        Objects.requireNonNull(radius, "radius");
        if (radius < 0) {
            throw new IllegalArgumentException("Radius can`t be less then zero.");
        }
        return getRepository().findAllNearby(latitude, longitude, radius);
    }
}
