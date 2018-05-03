package com.smartparking.controller;

import com.smartparking.entity.Parking;
import com.smartparking.model.filter.ParkingStatisticsFilter;
import com.smartparking.model.filter.ParkingsSimpleStatisticFilter;
import com.smartparking.model.response.ParkingItemResponse;
import com.smartparking.service.ParkingService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    ParkingService parkingService;

    @Autowired
    SpotService spotService;

    @GetMapping("/parkings-streets")
    ResponseEntity<List<String>> getParkingsStreetsByAnyMatching(@RequestParam("city") String city,
                                                                 @RequestParam("street") String input) {
        return new ResponseEntity<>(parkingService.findParkingStreetByAnyMatch(city, input), HttpStatus.OK);
    }

    @GetMapping("/parkings-cities/{input}")
    ResponseEntity<List<String>> getParkingsCitiesByAnyMatching(@PathVariable String input) {
        return new ResponseEntity<>(parkingService.findParkingCitiesByAnyMatch(input), HttpStatus.OK);
    }

    @GetMapping("/all-parkings-cities")
    ResponseEntity<List<String>> getAllParkingsCities() {
        return new ResponseEntity<>(parkingService.findAllParkingCities(), HttpStatus.OK);
    }

    @RequestMapping("/best-parkings")
    public ResponseEntity<List<ParkingItemResponse>> getBestParkings(@RequestParam("city") String city,
                                                                     @RequestParam("street") String street,
                                                                     @RequestParam("days") String days) {

        Instant calculatedTime = getCalculatedTime(days);
        List<Parking> parkings = spotService.findBestParkings(city, street, calculatedTime);
        List<ParkingItemResponse> parkingItemResponses = new ArrayList<>();

        parkings.forEach(parking -> parkingItemResponses
                .add(ParkingItemResponse.of(parking, spotService.countAvailableSpotsByParkingId(parking.getId()))));

        return new ResponseEntity<>(parkingItemResponses, HttpStatus.OK);
    }

    @RequestMapping("/best-parkings-in-city")
    public ResponseEntity<List<ParkingItemResponse>> getBestParkingsInTheCity(@RequestParam("city") String city,
                                                                              @RequestParam("days") String days) {

        Instant calculatedTime = getCalculatedTime(days);
        List<Parking> parkings = spotService.findBestParkingsInTheCity(city, calculatedTime);
        List<ParkingItemResponse> parkingItemResponses = new ArrayList<>();

        parkings.forEach(parking -> parkingItemResponses
                .add(ParkingItemResponse.of(parking, spotService.countAvailableSpotsByParkingId(parking.getId()))));

        return new ResponseEntity<>(parkingItemResponses, HttpStatus.OK);
    }

    @RequestMapping("/best-parkings-by-location")
    public ResponseEntity<List<ParkingItemResponse>> getBestParkingsByLocation(
            @ModelAttribute ParkingsSimpleStatisticFilter parkingsSimpleStatisticFilter) {

        List<Parking> parkings = spotService.
                findBestParkingsByLocation(parkingsSimpleStatisticFilter);
        List<ParkingItemResponse> parkingItemResponses = new ArrayList<>();

        parkings.forEach(parking -> parkingItemResponses.
                add(ParkingItemResponse.of(parking, spotService.countAvailableSpotsByParkingId(parking.getId()))));

        return new ResponseEntity<>(parkingItemResponses, HttpStatus.OK);
    }

    @RequestMapping("/best-parkings-by-location-and-properties")
    public ResponseEntity<List<ParkingItemResponse>> getBestParkingsByLocationPriceAndProperties(
            @ModelAttribute ParkingStatisticsFilter parkingStatisticsFilter) {

        List<Parking> parkings = spotService.
                findBestParkingsByLocationPriceAndProperties(parkingStatisticsFilter);
        List<ParkingItemResponse> parkingItemResponses = new ArrayList<>();

        parkings.forEach(parking -> parkingItemResponses.
                add(ParkingItemResponse.of(parking, spotService.countAvailableSpotsByParkingId(parking.getId()))));

        return new ResponseEntity<>(parkingItemResponses, HttpStatus.OK);
    }

    private Instant getCalculatedTime(String days) {
        return LocalDateTime.now().minusDays(Integer.parseInt(days)).toInstant(ZoneOffset.UTC);
    }

}
