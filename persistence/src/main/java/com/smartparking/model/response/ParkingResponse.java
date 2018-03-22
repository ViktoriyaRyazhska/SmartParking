package com.smartparking.model.response;

import java.math.BigDecimal;

public class ParkingResponse {

    private Long id;
    private String city;
    private String street;
    private String building;
    private Double latitude;
    private Double longitude;
    private BigDecimal price;
    private Double distance;
    private Long spotsNumber;
    private Long availableSpotsNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getSpotsNumber() {
        return spotsNumber;
    }

    public void setSpotsNumber(Long spotsNumber) {
        this.spotsNumber = spotsNumber;
    }

    public Long getAvailableSpotsNumber() {
        return availableSpotsNumber;
    }

    public void setAvailableSpotsNumber(Long availableSpotsNumber) {
        this.availableSpotsNumber = availableSpotsNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
