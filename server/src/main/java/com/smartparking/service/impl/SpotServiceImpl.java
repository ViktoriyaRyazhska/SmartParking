package com.smartparking.service.impl;

import com.smartparking.entity.Parking;
import com.smartparking.entity.Spot;
import com.smartparking.repository.SpotRepository;
import com.smartparking.repository.SpotStatisticRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public List<Parking> findBestParkingsByCityAndStreet(String city, String street) {
        return repository.findBestParkingsByCityAndStreet(city, street);
    }

    @Override
    public Map<Long, Double> getSpotStatistic(long id, long startDate, long endDate) {
        return spotStatisticRepository.getSpotStatistic(id,startDate, endDate);
    }
}
