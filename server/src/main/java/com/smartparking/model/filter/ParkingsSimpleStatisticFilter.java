package com.smartparking.model.filter;

import lombok.Data;

@Data
public class ParkingsSimpleStatisticFilter {
    private String latitude;
    private String longitude;
    private String radius;
    private String days;

    public ParkingsSimpleStatisticFilter(String latitude, String longitude, String radius, String days) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.days = days;
    }
}
