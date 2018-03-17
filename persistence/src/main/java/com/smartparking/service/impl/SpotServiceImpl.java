package com.smartparking.service.impl;

import com.smartparking.entity.Spot;
import com.smartparking.repository.SpotRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpotServiceImpl extends AbstractService<Spot, Long, SpotRepository> implements SpotService {

    protected SpotServiceImpl(@Autowired SpotRepository spotRepository) {
        super(spotRepository);
    }

    @Override
    public List<Spot> findAllAvailableSpotsByParkingId(Long id) {
//        return repository.findAllAvailableSpotsByParkingId(id);
        return null;
    }

    @Override
    public Long countAvailableSpotsByParkingId(Long id) {
        //return repository.countAvailableSpotsByParkingId(id);
        return null;
    }

    @Override
    public Long countAllSpotsByParkingId(Long id) {
        //return repository.countAllSpotsByParkingId(id);
        return null;
    }

    @Override
    public List<Spot> findAllSpotsByParkingId(Long id) {
        return repository.findAllByParkingId(id);
    }
}
