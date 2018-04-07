package com.smartparking.controller;

import com.smartparking.entity.Parking;
import com.smartparking.model.response.ParkingItemResponse;
import com.smartparking.service.ParkingService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    ParkingService parkingService;

    @Autowired
    SpotService spotService;

    @GetMapping("/allparkings")
    ResponseEntity<List<ParkingItemResponse>> getAllParkings() {
        List<Parking> parkings = parkingService.findAll();
        List<ParkingItemResponse> parkingItemResponses = new ArrayList<>();
        parkings.forEach(parking -> parkingItemResponses.add(ParkingItemResponse.of(parking)));
        return new ResponseEntity<>(parkingItemResponses, HttpStatus.OK);
    }

    @GetMapping("/findparkings/{input}")
    ResponseEntity<List<ParkingItemResponse>> getParkinigsByCity(@PathVariable String input) {
        List<Parking> parkings = parkingService.findParkingsByCity(input);
        List<ParkingItemResponse> parkingItemResponses = new ArrayList<>();
        parkings.forEach(parking -> parkingItemResponses.add(ParkingItemResponse.of(parking)));
        return new ResponseEntity<>(parkingItemResponses, HttpStatus.OK);
    }

    @GetMapping("/findbestparkingsbystreet/{input}")
    ResponseEntity<List<ParkingItemResponse>> findMostPopularParkingsByStreet(@PathVariable String input) {
        List<Parking> parkings = spotService.findMostPopularParkingsByStreet(input);
        List<ParkingItemResponse> parkingItemResponses = new ArrayList<>();
        parkings.forEach(parking -> parkingItemResponses.add(ParkingItemResponse.of(parking)));
        return new ResponseEntity<>(parkingItemResponses, HttpStatus.OK);
    }

    @GetMapping("/findparkingstreets/{input}")
    ResponseEntity<List<String>> findParkingsStreet(@PathVariable String input) {
        return new ResponseEntity<>(parkingService.findParkingStreetByAnyMatch(input), HttpStatus.OK);
    }

    @GetMapping("/findparkingscities/{input}")
    ResponseEntity<List<String>> findParkingsCities(@PathVariable String input) {
        return new ResponseEntity<>(parkingService.findParkingCitiesByAnyMatch(input), HttpStatus.OK);
    }

}
