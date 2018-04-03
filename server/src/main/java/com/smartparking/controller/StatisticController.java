package com.smartparking.controller;

import com.smartparking.entity.Parking;
import com.smartparking.model.response.ParkingItemResponse;
import com.smartparking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    ParkingService parkingService;

    @GetMapping("/allparkings")
    List<ParkingItemResponse> getAllParkings() {
        System.out.println("QQQQQQQQQQQQQQQQQQQQ");
        System.out.println("QQQQQQQQQQQQQQQQQQQQ");
        System.out.println("QQQQQQQQQQQQQQQQQQQQ");
        List<Parking> parkings = parkingService.findAll();
        List<ParkingItemResponse> parkingItemResponses = new ArrayList<>();
        parkings.forEach(parking -> parkingItemResponses.add(ParkingItemResponse.of(parking)));
        return parkingItemResponses;
    }

}
