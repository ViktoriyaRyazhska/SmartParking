package com.smartparking.model.request;

import lombok.Data;

@Data
public class ParkingSearchCriterias {
    private String search;
    private Boolean all;
    private Boolean hasCharger;
    private Boolean isCovered;
}
