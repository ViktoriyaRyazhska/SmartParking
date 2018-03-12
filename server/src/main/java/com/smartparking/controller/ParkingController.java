package com.smartparking.controller;


import com.smartparking.dto.ParkingDto;
import com.smartparking.entity.Parking;
import com.smartparking.service.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("p")
    List<ParkingDto> findAllParkings(){
        return parkingService.findAllParkings();
    }

}
