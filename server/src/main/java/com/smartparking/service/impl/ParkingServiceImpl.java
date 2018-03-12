package com.smartparking.service.impl;

import com.smartparking.dto.AddressDto;
import com.smartparking.dto.ParkingDto;
import com.smartparking.dto.ProviderDto;
import com.smartparking.entity.Parking;
import com.smartparking.repository.ParkingRepository;
import com.smartparking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService{
    @Autowired
    ParkingRepository parkingRepository;


    @Override
    @Transactional
    public Parking updateParking(Parking parking) {
        return parkingRepository.save(parking);
    }

    @Override
    @Transactional
    public void deleteParking(Parking parking) {
        parkingRepository.delete(parking);
    }

    @Override
    @Transactional
    public void saveParking(Parking parking) {
        parkingRepository.save(parking);
    }

    @Override
    public Parking findParkingById(Long parkingId) {
        return parkingRepository.getOne(parkingId);
    }

    @Override
    public List<ParkingDto> findAllParkings() {
        List<Parking> parkings = parkingRepository.findAll();
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
}
