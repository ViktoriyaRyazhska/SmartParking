package com.smartparking.service.impl;

import com.smartparking.entity.Parking;
import com.smartparking.model.response.ParkingResponse;
import com.smartparking.repository.ParkingRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.ParkingService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ParkingServiceImpl extends AbstractService<Parking, Long, ParkingRepository> implements ParkingService {

    @Autowired
    private SpotService spotService;

    protected ParkingServiceImpl(@Autowired ParkingRepository repository) {
        super(repository);
    }

    @Override
    public List<Parking> findAllByProviderId(Long id) {
        return getRepository().findAllByProviderId(id);
    }

    @Override
    public List<ParkingResponse> findAllNearby(Double latitude, Double longitude, Double radius) {
        Objects.requireNonNull(latitude, "latitude");
        Objects.requireNonNull(longitude, "longitude");
        Objects.requireNonNull(radius, "radius");
        if (radius < 0) {
            throw new IllegalArgumentException("Radius can`t be less then zero.");
        }
        return getRepository().findAllNearby(latitude, longitude, radius).stream().map(tuple -> {
            ParkingResponse response = new ParkingResponse();
            response.setId(tuple.get("id", BigInteger.class).longValue());
            response.setPrice(tuple.get("price", BigDecimal.class));
            response.setCity(tuple.get("city", String.class));
            response.setStreet(tuple.get("street", String.class));
            response.setBuilding(tuple.get("building", String.class));
            response.setLatitude(tuple.get("latitude", Double.class));
            response.setLongitude(tuple.get("longitude", Double.class));
            response.setDistance(tuple.get("distance", Double.class));
            response.setSpotsNumber(spotService.countAllSpotsByParkingId(response.getId()));
            response.setAvailableSpotsNumber(spotService.countAvailableSpotsByParkingId(response.getId()));
            return response;
        }).collect(Collectors.toList());
    }
}
