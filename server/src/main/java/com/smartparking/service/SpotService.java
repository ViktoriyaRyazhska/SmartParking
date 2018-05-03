package com.smartparking.service;

import com.smartparking.entity.Parking;
import com.smartparking.entity.Spot;
import com.smartparking.model.filter.ParkingStatisticsFilter;
import com.smartparking.model.filter.ParkingsSimpleStatisticFilter;
import com.smartparking.model.request.SpotSearchCriterias;
import com.smartparking.model.response.SpotStatisticResponse;
import com.smartparking.model.response.SpotStatusResponse;
import com.smartparking.repository.SpotRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface SpotService extends Service<Spot, Long, SpotRepository> {

    List<Spot> findAllAvailableSpotsByParkingId(Long id);

    Long countAvailableSpotsByParkingId(Long id);

    Long countAllSpotsByParkingId(Long id);

    List<Spot> findAllSpotsByParkingId(Long id);

    public List<SpotStatisticResponse> getSpotStatistic(long id, long startDate, long endDate);

    List<Parking> findBestParkings(String city, String street, Instant date);

    List<Parking> findBestParkingsInTheCity(String city, Instant date);

    List<Parking> findBestParkingsByLocation(ParkingsSimpleStatisticFilter parkingStatisticsFilter);

    List<Parking> findBestParkingsByLocationPriceAndProperties(ParkingStatisticsFilter parkingStatisticsFilter);

    List<SpotStatusResponse> findAllSpotsByParkingIdResponse(Long parkingId);

    Spot findFirstBySpotNumberAndParking(Long spotNumber, Parking parking);

    List<SpotStatusResponse> findSpotsByCriterias(Long parkingId, SpotSearchCriterias criterias);
}
