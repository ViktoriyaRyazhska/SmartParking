package com.smartparking.model.filter;

import lombok.Data;

@Data
public class ParkingStatisticsFilter {
    private String latitude;
    private String longitude;
    private String radius;
    private String days;
    private String minPrice;
    private String maxPrice;
    private String hasCharger;
    private String hasInvalid;
    private String isCovered;

    public ParkingStatisticsFilter(String latitude, String longitude, String radius, String days, String minPrice, String maxPrice, String hasCharger, String hasInvalid, String isCovered) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.days = days;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.hasCharger = hasCharger;
        this.hasInvalid = hasInvalid;
        this.isCovered = isCovered;
    }

}
