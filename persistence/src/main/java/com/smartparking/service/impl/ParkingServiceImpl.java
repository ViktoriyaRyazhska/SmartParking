package com.smartparking.service.impl;

import com.smartparking.entity.Favorite;
import com.smartparking.repository.FavoriteRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingServiceImpl extends AbstractService<Favorite, Long, FavoriteRepository> implements FavoriteService {

    protected ParkingServiceImpl(@Autowired FavoriteRepository repository) {
        super(repository);
    }
}
