package com.smartparking.model.response;

import com.smartparking.entity.Parking;

import java.math.BigDecimal;

public class ParkingDetailResponse {

    private Long id;

    private Double latitude;

    private Double longitude;

    private String address;

    private BigDecimal price;

    private String providerName;

    private String providerAddress;

    private Long spotsCount;

    private Long availableSpotsCount;

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

    public Long getSpotsCount() {
        return spotsCount;
    }

    public void setSpotsCount(Long spotsCount) {
        this.spotsCount = spotsCount;
    }

    public Long getAvailableSpotsCount() {
        return availableSpotsCount;
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

    public void setAvailableSpotsCount(Long availableSpotsCount) {
        this.availableSpotsCount = availableSpotsCount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /*
         *This method don`t set numberSpots and numberAvailableSpots values please set its after using this method
         */
    public static ParkingDetailResponse of(Parking parking) {
        ParkingDetailResponse parkingDetailResponse = new ParkingDetailResponse();

        parkingDetailResponse.setId(parking.getId());

        parkingDetailResponse.setAddress(
                parking.getCity() +
                        "/" + parking.getStreet() +
                        "/" + parking.getBuilding());

        parkingDetailResponse.setPrice(parking.getPrice());

        parkingDetailResponse.setLongitude(parking.getLongitude());

        parkingDetailResponse.setLatitude(parking.getLatitude());

        parkingDetailResponse.setProviderName(parking.getProvider().getName());

        parkingDetailResponse.setProviderAddress(
                parking.getProvider().getCity() +
                        "/" + parking.getStreet() +
                        "/" + parking.getBuilding());
        return parkingDetailResponse;
    }
}

