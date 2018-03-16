package com.smartparking.controller;


import com.smartparking.entity.Parking;
import com.smartparking.model.response.ParkingDetailResponse;
import com.smartparking.model.response.ParkingItemResponse;
import com.smartparking.service.ParkingService;
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

    @Autowired
    ParkingService addressService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("parkings")
    List<ParkingItemResponse> parkings() {
        return ParkingItemResponse.listOf(parkingService.findAll());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("parkingdetail/{id}")
    ParkingDetailResponse findParkingDetailResponseById(@PathVariable Long id) {
        Parking parking = parkingService.findById(id);
        return ParkingDetailResponse.of(parking);
    }

}
