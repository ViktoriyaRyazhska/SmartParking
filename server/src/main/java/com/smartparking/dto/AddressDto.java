package com.smartparking.dto;

import com.smartparking.entity.Parking;

import java.util.List;

public class AddressDto {
    private Long id;
    private String state;
    private String city;
    private String street;
    private String buildingNumber;
    private List<ParkingDto> parkingsDto;

    public static AddressDto builder(){
        AddressDto addressDto = new AddressDto();
        return addressDto;
    }

    public AddressDto setId(Long id) {
        this.id = id;
        return this;
    }

    public AddressDto setState(String state) {
        this.state = state;
        return this;
    }

    public AddressDto setCity(String city) {
        this.city = city;
        return this;
    }

    public AddressDto setStreet(String street) {
        this.street = street;
        return this;
    }

    public AddressDto setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
        return this;
    }

    public AddressDto setParkingsDto(List<ParkingDto> parkingsDto) {
        this.parkingsDto = parkingsDto;
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public List<ParkingDto> getParkingsDto() {
        return parkingsDto;
    }
}
