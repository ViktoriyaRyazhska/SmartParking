package com.smartparking.controller;

import com.smartparking.entity.Parking;
import com.smartparking.model.response.ParkingItemResponse;
import com.smartparking.service.ParkingService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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


    @GetMapping("/findparkingstreets")
    ResponseEntity<List<String>> findParkingsStreet(@RequestParam("city") String city,
                                                    @RequestParam("street") String street) {
        return new ResponseEntity<>(parkingService.findParkingStreetByAnyMatch(city, street), HttpStatus.OK);
    }

    @GetMapping("/findparkingscities/{input}")
    ResponseEntity<List<String>> findParkingsCitiesByAnyMatching(@PathVariable String input) {
        return new ResponseEntity<>(parkingService.findParkingCitiesByAnyMatch(input), HttpStatus.OK);
    }

    @GetMapping("/findAllparkingscities")
    ResponseEntity<List<String>> findAllParkingsCities() {
        return new ResponseEntity<>(parkingService.findAllParkingCities(), HttpStatus.OK);
    }

    @RequestMapping("/findbestparkingsbystreetandcity")
    public ResponseEntity<List<ParkingItemResponse>> bestParkingsByCityAndStreet(@RequestParam("city") String city,
                                                                                 @RequestParam("street") String street,
                                                                                 @RequestParam("date") String date) {

        LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(date)),
                        TimeZone.getDefault().toZoneId());

        Instant instant = localDateTime.toInstant(ZoneOffset.UTC);

        List<Parking> parkings = spotService.findBestParkingsByCityAndStreet(city, street, instant);
        List<ParkingItemResponse> parkingItemResponses = new ArrayList<>();
        parkings.forEach(parking -> parkingItemResponses.add(ParkingItemResponse.of(parking)));
        return new ResponseEntity<>(parkingItemResponses, HttpStatus.OK);
    }

}
