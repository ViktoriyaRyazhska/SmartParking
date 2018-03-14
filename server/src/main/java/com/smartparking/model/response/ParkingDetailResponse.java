package com.smartparking.model.response;

import com.smartparking.dto.SpotDto;
import com.smartparking.entity.Parking;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingDetailResponse {

    private Long id;

    private String address;

    private BigDecimal price;

    private String providerName;

    private String providerAddress;

    private List<SpotDto> spotsDto;

    private Integer numberSpots;

    private Integer numberAvailableSpots;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<SpotDto> getSpotsDto() {
        return spotsDto;
    }

    public void setSpotsDto(List<SpotDto> spotsDto) {
        this.spotsDto = spotsDto;
        this.numberSpots = Integer.valueOf(spotsDto.size());
    }

    public Integer getNumberSpots() {
        return numberSpots;
    }

    public Integer getNumberAvailableSpots() {
        return numberAvailableSpots;
    }

    public void setNumberAvailableSpots(Integer numberAvailableSpots) {
        this.numberAvailableSpots = numberAvailableSpots;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderAddress() {
        return providerAddress;
    }

    public void setProviderAddress(String providerAddress) {
        this.providerAddress = providerAddress;
    }

    public void setNumberSpots(Integer numberSpots) {
        this.numberSpots = numberSpots;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static ParkingDetailResponse of(Parking parking){
        ParkingDetailResponse parkingDetailResponse = new ParkingDetailResponse();

        parkingDetailResponse.setId(parking.getId());

        parkingDetailResponse.setAddress(
                parking.getAddress().getState() +
                "/" + parking.getAddress().getCity() +
                "/" + parking.getAddress().getStreet() +
                "/" + parking.getAddress().getBuildingNumber());

        parkingDetailResponse.setPrice(parking.getPrice());

        parkingDetailResponse.setProviderName(parking.getProvider().getName());

        parkingDetailResponse.setProviderAddress(
                parking.getProvider().getLegalAddress().getState() +
                        "/" + parking.getProvider().getLegalAddress().getCity() +
                        "/" + parking.getProvider().getLegalAddress().getStreet() +
                        "/" + parking.getProvider().getLegalAddress().getBuildingNumber());

        parkingDetailResponse.setSpotsDto(parking.getSpots()
                .stream().map(SpotDto::of).collect(Collectors.toList()));

        return parkingDetailResponse;
    }
}

