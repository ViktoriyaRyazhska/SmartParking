package com.smartparking.service.impl;

import com.smartparking.entity.Parking;
import com.smartparking.entity.Spot;
import com.smartparking.model.request.SpotSearchCriterias;
import com.smartparking.model.response.SpotStatisticResponse;
import com.smartparking.model.response.SpotStatusResponse;
import com.smartparking.repository.SpotRepository;
import com.smartparking.repository.SpotStatisticRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public List<Parking> findBestParkingsByLocationPriceAndProperties(Double latitude, Double longitude, Double radius,
                                                                      Instant date, BigDecimal minPrice, BigDecimal maxPrice,
                                                                      Boolean hasCharger, Boolean hasInvalid, Boolean isCovered) {
        return repository.findBestParkingsByLocationPriceAndFunctional(
                latitude, longitude, radius,
                date, minPrice, maxPrice,
                hasCharger, hasInvalid, isCovered);
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
    public Spot findFirstBySpotNumberAndParking(Long spotNumber, Parking parking) {
        return repository.findFirstBySpotNumberAndParking(spotNumber, parking);
    }

    @Override
    public List<SpotStatisticResponse> getSpotStatistic(long id, long startDate, long endDate) {
        return spotStatisticRepository.getSpotStatistic(id, startDate, endDate);
    }

    @Override
    public List<SpotStatusResponse> findSpotsByCriterias(Long parkingId, SpotSearchCriterias criterias) {
        List<SpotStatusResponse> filteredSpots = findAllSpotsByParkingIdResponse(parkingId).stream().filter(spotStatusResponse -> {
            if (spotStatusResponse.getSpotNumber().toString().contains(criterias.getSearch())) {
                if (criterias.getAll()) {
                    return true;
                }
                if (spotStatusResponse.getHasCharger() == criterias.getHasCharger() && criterias.getHasCharger()) {
                    return true;
                }
                if (spotStatusResponse.getIsInvalid() == criterias.getIsInvalid() && criterias.getIsInvalid()) {
                    return true;
                }
                if (spotStatusResponse.getIsBlocked() == criterias.getIsBlocked() && criterias.getIsBlocked()) {
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
        return filteredSpots;
    }
}
