package com.smartparking.service.impl;

import com.smartparking.entity.Parking;
import com.smartparking.entity.Spot;
import com.smartparking.model.response.SpotStatisticResponse;
import com.smartparking.model.response.SpotStatusResponse;
import com.smartparking.repository.SpotRepository;
import com.smartparking.repository.SpotStatisticRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpotServiceImpl extends AbstractService<Spot, Long, SpotRepository> implements SpotService {

    @Autowired
    private SpotStatisticRepository spotStatisticRepository;

    protected SpotServiceImpl(@Autowired SpotRepository spotRepository) {
        super(spotRepository);
    }

    @Override
    public List<Spot> findAllAvailableSpotsByParkingId(Long id) {
        System.out.println(repository.findAllAvailableSpotsByParkingId(id));
        return repository.findAllAvailableSpotsByParkingId(id);
    }

    @Override
    public Long countAvailableSpotsByParkingId(Long id) {
        return repository.countAvailableSpotsByParkingId(id);
    }

    @Override
    public Long countAllSpotsByParkingId(Long id) {
        return repository.countAllSpotsByParkingId(id);
    }

    @Override
    public List<Spot> findAllSpotsByParkingId(Long id) {
        return repository.findAllByParkingId(id);
    }

    @Override
    public List<Parking> findBestParkings(String city, String street, Instant date) {
        return repository.findBestParkings(city, street, date);
    }

    @Override
    public List<Parking> findBestParkingsInTheCity(String city, Instant date) {
        return repository.findBestParkingsInTheCity(city, date);
    }

    @Override
    public List<Parking> findBestParkingsByLocation(Double latitude, Double longitude, Double radius, Instant date) {
        return repository.findBestParkingsByLocation(latitude, longitude, radius, date);
    }

    @Override
    public List<SpotStatusResponse> findAllSpotsByParkingIdResponse(Long parkingId) {
        List<SpotStatusResponse> spots = findAllSpotsByParkingId(parkingId).stream().
                map(spot -> new SpotStatusResponse(spot.getId(), false,
                        spot.getSpotNumber(), spot.getParking().getId(),
                        spot.getHasCharger(), spot.getIsInvalid(), spot.getIsBlocked())).collect(Collectors.toList());
        spots.sort((Comparator.comparing(SpotStatusResponse::getSpotNumber)));
        return spots;
    }

    @Override
    public List<SpotStatisticResponse> getSpotStatistic(long id, long startDate, long endDate) {
        return spotStatisticRepository.getSpotStatistic(id, startDate, endDate);
    }
}
