package com.smartparking.service.impl;

import com.smartparking.entity.Spot;
import com.smartparking.repository.SpotRepository;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

public class SpotServiceImpl implements SpotService {

    @Autowired
    private SpotRepository spotRepository;

    @Override
    @Transactional
    public Spot updateSpot(Spot spot) {
        return spotRepository.save(spot);
    }

    @Override
    @Transactional
    public void deleteSpot(Spot spot) {
        spotRepository.delete(spot);
    }

    @Override
    @Transactional
    public void saveSpot(Spot spot) {
        spotRepository.save(spot);
    }
}
