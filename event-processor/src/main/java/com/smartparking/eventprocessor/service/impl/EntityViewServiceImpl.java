package com.smartparking.eventprocessor.service.impl;

import com.smartparking.eventprocessor.model.view.Spot;
import com.smartparking.eventprocessor.service.EntityViewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntityViewServiceImpl implements EntityViewService {

    private List<Spot> spots;

    @Override
    public void initialize() {
        //TODO синхронізувати все, щоб красиво було.
    }

    @Override
    public Optional<Spot> getSpot(Long spotId) {
        return Optional.empty();
    }

    @Override
    public void addSpot(Long spotId, Long parkingId) {

    }

    @Override
    public void deleteSpot(Long spotId, Long parkingId) {

    }

    @Override
    public void deleteParking(Long parkingId) {

    }

    @Override
    public void addParking(Long parkingId, String token) {

    }

    @Override
    public void updateParkingToken(Long parkingId, String parkingToken) {

    }
}
