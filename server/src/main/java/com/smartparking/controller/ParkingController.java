package com.smartparking.controller;


import com.smartparking.entity.Parking;
import com.smartparking.model.request.ParkingRequest;
import com.smartparking.model.response.ManagerParkingResponse;
import com.smartparking.model.response.ParkingDetailResponse;
import com.smartparking.service.ParkingService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    @Autowired
    SpotService spotService;

    @Autowired
    ParkingService addressService;

    @RequestMapping("parkings-nearby")
    public ResponseEntity<?> parkingsNearby(@RequestParam("latitude") Double latitude,
                                            @RequestParam("longitude") Double longitude,
                                            @RequestParam("radius") Double radius) {
        if (radius < 0) {
            return new ResponseEntity<>("Radius must be positive or zero.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(parkingService.findAllNearby(latitude, longitude, radius), HttpStatus.OK);
    }

    @RequestMapping("parkingdetail/{id}")
    ParkingDetailResponse findParkingDetailResponseById(@PathVariable Long id) {
        Parking parking = parkingService.findById(id);
        ParkingDetailResponse parkingDetailResponse = ParkingDetailResponse.of(parking);
        parkingDetailResponse.setNumberSpots(
                spotService.countAllSpotsByParkingId(id)
        );
        parkingDetailResponse.setNumberAvailableSpots(
                spotService.countAvailableSpotsByParkingId(id)
        );
        return parkingDetailResponse;
    }

    // TODO Change url to manager-configuration/parking/{id}

    @GetMapping("manager-configuration/parking/{id}")
    ResponseEntity<ManagerParkingResponse> configure(@PathVariable Long id) {
        Parking parking = parkingService.findById(id);
        if (parking != null) {
            return new ResponseEntity<>(ManagerParkingResponse.of(parking), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("manager-configuration/parkings")
    ResponseEntity<List<ManagerParkingResponse>> parkings() {
        List<Parking> parkings = parkingService.findAll();
        if (parkings != null && !parkings.isEmpty()) {
            List<ManagerParkingResponse> parkingResponses = new ArrayList<>();
            for (Parking parking : parkings) {
                parkingResponses.add(ManagerParkingResponse.of(parking));
            }
            return new ResponseEntity<>(parkingResponses, HttpStatus.OK);
        } else if (parkings != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // TODO Check this predicates
    }

    @PostMapping("/manager-configuration/parking/save")
    ResponseEntity<?> save(@RequestBody ParkingRequest parkingRequest) {
        parkingService.save(parkingRequest.toParking());
        //TODO Handle different HttpStatuses.
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/manager-configuration/parking/delete")
    ResponseEntity<?> delete(@RequestBody ParkingRequest parkingRequest) {
        parkingService.delete(parkingRequest.toParking());
        //TODO Handle different HttpStatuses.
        return new ResponseEntity<>(HttpStatus.OK);
    }
}