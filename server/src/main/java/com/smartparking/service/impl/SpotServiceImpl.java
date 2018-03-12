package com.smartparking.service.impl;

import com.smartparking.entity.Spot;
import com.smartparking.service.SpotService;

import javax.transaction.Transactional;

public class SpotServiceImpl implements SpotService {
    @Override
    @Transactional
    public Spot updateSpot(Spot spot) {
        return null;
    }

    @Override
    @Transactional
    public void deleteSpot(Spot spot) {

    }

    @Override
    @Transactional
    public void saveSpot(Spot spot) {

    }
}
