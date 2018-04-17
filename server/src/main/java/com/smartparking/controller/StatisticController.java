package com.smartparking.controller;

import com.smartparking.entity.Parking;
import com.smartparking.model.response.ParkingItemResponse;
import com.smartparking.service.ParkingService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    ParkingService parkingService;

    @Autowired
    SpotService spotService;

    @GetMapping("/findparkingstreets")
    ResponseEntity<List<String>> findParkingsStreetsByAnyMatching(@RequestParam("city") String city,
                                                                  @RequestParam("street") String input) {
        return new ResponseEntity<>(parkingService.findParkingStreetByAnyMatch(city, input), HttpStatus.OK);
    }

    @GetMapping("/findparkingscities/{input}")
    ResponseEntity<List<String>> findParkingsCitiesByAnyMatching(@PathVariable String input) {
        return new ResponseEntity<>(parkingService.findParkingCitiesByAnyMatch(input), HttpStatus.OK);
    }

    @GetMapping("/findallparkingscities")
    ResponseEntity<List<String>> findAllParkingsCities() {
        return new ResponseEntity<>(parkingService.findAllParkingCities(), HttpStatus.OK);
    }

    @RequestMapping("/findbestparkings")
    public ResponseEntity<List<ParkingItemResponse>> findBestParkings(@RequestParam("city") String city,
                                                                      @RequestParam("street") String street,
                                                                      @RequestParam("days") String date) {

        Instant instant = LocalDateTime.now().minusDays(Integer.parseInt(date)).toInstant(ZoneOffset.UTC);

        List<Parking> parkings = spotService.findBestParkings(city, street, instant);
        List<ParkingItemResponse> parkingItemResponses = new ArrayList<>();
        parkings.forEach(parking -> parkingItemResponses.add(ParkingItemResponse.of(parking)));
        return new ResponseEntity<>(parkingItemResponses, HttpStatus.OK);
    }

    @RequestMapping("/findbestparkingsincity")
    public ResponseEntity<List<ParkingItemResponse>> findBestParkingsInTheCity(@RequestParam("city") String city,
                                                                               @RequestParam("days") String date) {

        Instant instant = LocalDateTime.now().minusDays(Integer.parseInt(date)).toInstant(ZoneOffset.UTC);

        List<Parking> parkings = spotService.findBestParkingsInTheCity(city, instant);
        List<ParkingItemResponse> parkingItemResponses = new ArrayList<>();
        parkings.forEach(parking -> parkingItemResponses.add(ParkingItemResponse.of(parking)));
        return new ResponseEntity<>(parkingItemResponses, HttpStatus.OK);
    }

    @RequestMapping("/findbestparkingsbylocation")
    public ResponseEntity<List<ParkingItemResponse>> findBestParkingsByLocation(@RequestParam("latitude") String latitude,
                                                                                @RequestParam("longitude") String longitude,
                                                                                @RequestParam("radius") String radius,
                                                                                @RequestParam("days") String date) {

        Instant instant = LocalDateTime.now().minusDays(Integer.parseInt(date)).toInstant(ZoneOffset.UTC);

        List<Parking> parkings = spotService.findBestParkingsByLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), Double.parseDouble(radius), instant);
        List<ParkingItemResponse> parkingItemResponses = new ArrayList<>();
        parkings.forEach(parking -> parkingItemResponses.add(ParkingItemResponse.of(parking)));
        return new ResponseEntity<>(parkingItemResponses, HttpStatus.OK);
    }

}
