package com.smartparking.service;

import com.smartparking.entity.Spot;

public interface SpotService {
    Spot updateSpot(Spot spot);
    void deleteSpot(Spot spot);
    void saveSpot(Spot spot);
}
