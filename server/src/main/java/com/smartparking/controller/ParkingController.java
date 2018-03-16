package com.smartparking.controller;


import com.smartparking.dto.AddressDto;
import com.smartparking.dto.ParkingDto;
import com.smartparking.dto.ProviderDto;
import com.smartparking.entity.Parking;
import com.smartparking.model.response.ParkingDetailResponse;
import com.smartparking.service.ParkingService;
import com.smartparking.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    @Autowired
    SpotService spotService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping("p")
    List<ParkingDto> findAllParkings() {
        List<Parking> parkings = parkingService.findAll();
        List<ParkingDto> parkingsDto = new ArrayList<>();
        ParkingDto parkingDto;
        AddressDto addressDto;
        ProviderDto providerDto;
        for (Parking parking : parkings) {
            addressDto = AddressDto.builder().setId(parking.getAddress().getId()).
                    setState(parking.getAddress().getState()).
                    setCity(parking.getAddress().getCity()).
                    setStreet(parking.getAddress().getStreet()).
                    setBuildingNumber(parking.getAddress().getBuildingNumber());
            providerDto = ProviderDto.builder().setId(parking.getProvider().getId()).
                    setName(parking.getProvider().getName());
            parkingDto = ParkingDto.builder().setId(parking.getId()).
                    setLatitude(parking.getLatitude()).
                    setLongitude(parking.getLongitude()).
                    setPrice(parking.getPrice()).
                    setAddressDto(addressDto).
                    setProviderDto(providerDto);
            parkingsDto.add(parkingDto);
        }
        return parkingsDto;
    }

    @CrossOrigin(origins = "http://localhost:4200")
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

}
