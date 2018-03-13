package com.smartparking.service.impl;

import com.smartparking.entity.Spot;
import com.smartparking.repository.SpotRepository;
import com.smartparking.service.AbstractService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpotServiceImpl extends AbstractService<Spot, Long, SpotRepository> implements SpotService {

    protected SpotServiceImpl(@Autowired SpotRepository repository) {
        super(repository);
    }
}
